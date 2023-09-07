package app.piper.piper.loadgenerator;

import app.piper.piper.common.PaginationResponse;
import app.piper.piper.pipeline.PipelineResponse;
import app.piper.piper.pipeline.instance.PipelineInstanceResponse;
import java.net.URI;
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
            UUID pipelineId = this.getPipelineId();

            PipelineInstanceResponse createdPipelineInstance = this.generate(pipelineId);
            log.info("New Pipeline generated. INSTANCE: {}", createdPipelineInstance.getName());
        }
        catch (Exception e) {
            log.error("Failed to create pipeline. REASON: {}", e.getMessage());
        }
    }

    private PipelineInstanceResponse generate(UUID pipelineId) {
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

}
