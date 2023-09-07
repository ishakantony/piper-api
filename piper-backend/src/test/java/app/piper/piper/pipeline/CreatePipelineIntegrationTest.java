package app.piper.piper.pipeline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.ErrorResponseBody;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreatePipelineIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @BeforeEach
    void setUp() {
        this.url = "http://localhost:" + port + "/api/pipelines";
    }

    @Test
    @Transactional
    void testCreatePipeline() {
        String requestBody = """
                    {
                        "name": "Test Pipeline E2E",
                        "description": "This is a pipeline E2E test description"
                    }
                """;

        ResponseEntity<PipelineResponse> response = restTemplate.exchange(this.url, HttpMethod.POST,
                this.generateRequestEntity(requestBody), PipelineResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Pipeline E2E");
        assertThat(response.getBody().getSlug()).isNotNull();
        assertThat(response.getBody().getDescription()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo("This is a pipeline E2E test description");
        assertThat(response.getBody().getCreatedAt()).isNotNull();
        assertThat(response.getBody().getUpdatedAt()).isNotNull();
        assertThat(response.getBody().getCreatedAt()).isBefore(OffsetDateTime.now());
        assertThat(response.getBody().getUpdatedAt()).isBefore(OffsetDateTime.now());
    }

    @Test
    @Transactional
    void testCreatePipeline_WithoutDescription() {
        String requestBody = """
                    {
                        "name": "Test Pipeline Without Description E2E"
                    }
                """;

        ResponseEntity<PipelineResponse> response = restTemplate.exchange(this.url, HttpMethod.POST,
                this.generateRequestEntity(requestBody), PipelineResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Pipeline Without Description E2E");
        assertThat(response.getBody().getSlug()).isNotNull();
        assertThat(response.getBody().getDescription()).isNull();
        assertThat(response.getBody().getCreatedAt()).isNotNull();
        assertThat(response.getBody().getUpdatedAt()).isNotNull();
        assertThat(response.getBody().getCreatedAt()).isBefore(OffsetDateTime.now());
        assertThat(response.getBody().getUpdatedAt()).isBefore(OffsetDateTime.now());
    }

    @Test
    void testCreatePipeline_MissingName() {
        String requestBody = """
                    {}
                """;

        ResponseEntity<ErrorResponseBody> response = restTemplate.exchange(this.url, HttpMethod.POST,
                this.generateRequestEntity(requestBody), ErrorResponseBody.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(ErrorCode.INVALID_FIELDS);
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getTimestamp()).isBefore(OffsetDateTime.now());
        assertThat(response.getBody().getErrors()).isNotNull();
        assertThat(response.getBody().getErrors()).hasSize(1);
        assertThat(response.getBody().getErrors()).contains("Invalid field [name], Name is required");
    }

    @Test
    @Transactional
    void testCreatePipeline_NameIsDuplicated() {
        String requestBody = """
                    {
                        "name": "Test Duplicated Pipeline E2E"
                    }
                """;

        assertDoesNotThrow(() -> restTemplate.exchange(this.url, HttpMethod.POST,
                this.generateRequestEntity(requestBody), ErrorResponseBody.class));

        ResponseEntity<ErrorResponseBody> response = restTemplate.exchange(this.url, HttpMethod.POST,
                this.generateRequestEntity(requestBody), ErrorResponseBody.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(ErrorCode.CONSTRAINT_VIOLATION);
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getTimestamp()).isBefore(OffsetDateTime.now());
        assertThat(response.getBody().getErrors()).isNotNull();
        assertThat(response.getBody().getErrors()).hasSize(1);
        assertThat(response.getBody().getErrors()).contains("Pipeline name must be unique");
    }

    private HttpEntity<String> generateRequestEntity(String requestBody) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(requestBody, headers);
    }

}
