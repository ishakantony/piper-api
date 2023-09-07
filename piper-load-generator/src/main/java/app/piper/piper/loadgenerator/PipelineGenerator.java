package app.piper.piper.loadgenerator;

import app.piper.piper.pipeline.PipelineRequest;
import app.piper.piper.pipeline.PipelineResponse;
import com.github.javafaker.Faker;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class PipelineGenerator {

    private final RestTemplate restTemplate;

    private final Faker faker;

    private final URI baseUrl;

    public PipelineGenerator(RestTemplateBuilder restTemplateBuilder, Faker faker, Environment environment) {
        this.restTemplate = restTemplateBuilder.build();
        this.faker = faker;

        try {
            this.baseUrl = new URI(environment.getRequiredProperty(Constants.PIPER_BACKEND_BASE_URL_ENV_KEY));
        }
        catch (Exception e) {
            throw new BeanCreationException(e.getMessage());
        }
    }

    @Scheduled(fixedRate = 3000)
    public void autoGeneratePipelines() {
        try {
            PipelineResponse createdPipeline = this.generate();
            log.info("New Pipeline generated. ID: {}", createdPipeline.getId());
        }
        catch (Exception e) {
            log.error("Failed to create pipeline. REASON: {}", e.getMessage());
        }
    }

    private PipelineResponse generate() {
        PipelineRequest newPipeline = new PipelineRequest();

        newPipeline.setName(faker.name().firstName() + " " + faker.commerce().productName());
        newPipeline.setDescription(String.join(" ", faker.lorem().words(40)));

        return restTemplate.postForObject(
                UriComponentsBuilder.fromUri(this.baseUrl).path("/api/pipelines").build().toUri(), newPipeline,
                PipelineResponse.class);
    }

}
