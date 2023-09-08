package app.piper.piper.loadgenerator;

import app.piper.piper.common.PaginationResponse;
import app.piper.piper.operation.action.PipelineInstanceActionRequest;
import app.piper.piper.pipeline.PipelineResponse;
import app.piper.piper.pipeline.instance.PipelineInstanceResponse;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class PipelineInstanceGenerator {

    private final List<UUID> PIPELINE_IDS = new ArrayList<>();

    private final RestTemplate restTemplate;

    private final Random random;

    private final URI baseUrl;

    public PipelineInstanceGenerator(RestTemplateBuilder restTemplateBuilder, Environment environment) {
        this.restTemplate = restTemplateBuilder.build();
        this.random = new Random();

        try {
            this.baseUrl = new URI(environment.getRequiredProperty(Constants.PIPER_BACKEND_BASE_URL_ENV_KEY));
        }
        catch (Exception e) {
            throw new BeanCreationException(e.getMessage());
        }
    }

    @Scheduled(fixedRate = 1000)
    public void autoGeneratePipelines() {
        try {
            log.info("================== PIPELINE INSTANCE GENERATOR START ==================");
            UUID pipelineId = this.getPipelineId();

            PipelineInstanceResponse createdPipelineInstance = this.generateInstance(pipelineId);
            log.info("New Pipeline generated. INSTANCE: {}", createdPipelineInstance.getName());

            this.startInstance(createdPipelineInstance.getId());
            log.info("Instance started");

            // Make it 80% success
            if (random.nextInt(10) > 2) {
                this.markInstanceAsSuccess(createdPipelineInstance.getId());
                log.info("Marking instance as success");
            }
            else {
                this.markInstanceAsFailed(createdPipelineInstance.getId());
                log.info("Marking instance as failed");
            }
        }
        catch (Exception e) {
            log.error("Failed to generate pipeline. REASON: {}", e.getMessage());
        }
        finally {
            log.info("================== PIPELINE INSTANCE GENERATOR END ==================");
        }
    }

    private void markInstanceAsFailed(UUID instanceId) {
        OffsetDateTime now = OffsetDateTime.now().plusMinutes(30);
        OffsetDateTime maxStartAt = now.plusHours(3);

        OffsetDateTime endAt = this.generateRandomTime(now, maxStartAt);

        restTemplate
                .postForObject(
                        UriComponentsBuilder.fromUri(this.baseUrl)
                                .path(String.format("/api/_action/pipeline-instances/%s/mark-as-failed",
                                        instanceId.toString()))
                                .build().toUri(),
                        PipelineInstanceActionRequest.builder().time(endAt).build(), PipelineInstanceResponse.class);
    }

    private void markInstanceAsSuccess(UUID instanceId) {
        OffsetDateTime now = OffsetDateTime.now().plusMinutes(15);
        OffsetDateTime maxStartAt = now.plusMinutes(30);

        OffsetDateTime endAt = this.generateRandomTime(now, maxStartAt);

        restTemplate.postForObject(
                UriComponentsBuilder.fromUri(this.baseUrl)
                        .path(String.format("/api/_action/pipeline-instances/%s/mark-as-success",
                                instanceId.toString()))
                        .build().toUri(),
                PipelineInstanceActionRequest.builder().time(endAt).build(), PipelineInstanceResponse.class);
    }

    private void startInstance(UUID instanceId) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime maxStartAt = now.plusMinutes(15);

        OffsetDateTime startAt = this.generateRandomTime(now, maxStartAt);

        restTemplate.postForObject(UriComponentsBuilder.fromUri(this.baseUrl)
                .path(String.format("/api/_action/pipeline-instances/%s/start", instanceId.toString())).build().toUri(),
                PipelineInstanceActionRequest.builder().time(startAt).build(), PipelineInstanceResponse.class);
    }

    private PipelineInstanceResponse generateInstance(UUID pipelineId) {
        return restTemplate.postForObject(
                UriComponentsBuilder.fromUri(this.baseUrl)
                        .path(String.format("/api/pipelines/%s/instances", pipelineId.toString())).build().toUri(),
                null, PipelineInstanceResponse.class);
    }

    private UUID getPipelineId() {
        if (!PIPELINE_IDS.isEmpty()) {
            return this.getPipelineIdFromStore();
        }

        log.info("No pipelines in store, fetching from piper backend...");

        try {
            ResponseEntity<PaginationResponse<PipelineResponse>> responseEntity = restTemplate
                    .exchange(
                            UriComponentsBuilder.fromUri(this.baseUrl).path("/api/pipelines")
                                    .queryParam("pageSize", 100).build().toUri(),
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });

            Objects.requireNonNull(responseEntity.getBody()).getContent().stream().map(PipelineResponse::getId)
                    .forEach(PIPELINE_IDS::add);

            return this.getPipelineIdFromStore();
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Failed to get pipelines. REASON: %s", e.getMessage()));
        }
    }

    private UUID getPipelineIdFromStore() {
        return PIPELINE_IDS.get(random.nextInt(PIPELINE_IDS.size()));
    }

    private OffsetDateTime generateRandomTime(OffsetDateTime start, OffsetDateTime end) {
        long startSeconds = start.toEpochSecond();
        long endSeconds = end.toEpochSecond();

        Random random = new Random();
        long randomSeconds = startSeconds + random.nextInt((int) (endSeconds - startSeconds + 1));

        Instant instant = Instant.ofEpochSecond(randomSeconds);
        return OffsetDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
    }

}
