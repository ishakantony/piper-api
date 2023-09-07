package app.piper.piper.pipeline;

import static org.assertj.core.api.Assertions.assertThat;

import app.piper.piper.common.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.liquibase.contexts=test" })
public class GetPipelinesIntegrationTest {

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
    void testGetPipelines() {
        ResponseEntity<PaginationResponse<PipelineResponse>> response = restTemplate.exchange(this.url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(5);
        assertThat(response.getBody().getPageSize()).isEqualTo(5);
        assertThat(response.getBody().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getIsFirstPage()).isTrue();
        assertThat(response.getBody().getIsLastPage()).isFalse();
    }

    @Test
    void testGetPipelines_GetSecondPage() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url).queryParam("pageNumber", 1);

        ResponseEntity<PaginationResponse<PipelineResponse>> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(5);
        assertThat(response.getBody().getPageSize()).isEqualTo(5);
        assertThat(response.getBody().getPageNumber()).isEqualTo(1);
        assertThat(response.getBody().getIsFirstPage()).isFalse();
    }

    @Test
    void testGetPipelines_CustomPageSize() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url).queryParam("pageSize", 10);

        ResponseEntity<PaginationResponse<PipelineResponse>> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(10);
        assertThat(response.getBody().getPageSize()).isEqualTo(10);
        assertThat(response.getBody().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getIsFirstPage()).isTrue();
    }

    @Test
    void testGetPipelines_NewestPipelineFirst() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url).queryParam("sortBy", "createdAt")
                .queryParam("sortDirection", "DESC");

        ResponseEntity<PaginationResponse<PipelineResponse>> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(5);
        assertThat(response.getBody().getPageSize()).isEqualTo(5);
        assertThat(response.getBody().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getIsFirstPage()).isTrue();
        assertThat(response.getBody().getIsLastPage()).isFalse();

        PipelineResponse firstPipelineInTheResponse = response.getBody().getContent().get(0);

        PipelineResponse secondPipelineInTheResponse = response.getBody().getContent().get(1);

        assertThat(firstPipelineInTheResponse.getCreatedAt())
                .isAfterOrEqualTo(secondPipelineInTheResponse.getCreatedAt());
    }

    @Test
    void testGetPipelines_OldestPipelineFirst() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url).queryParam("sortBy", "createdAt")
                .queryParam("sortDirection", "ASC");

        ResponseEntity<PaginationResponse<PipelineResponse>> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(5);
        assertThat(response.getBody().getPageSize()).isEqualTo(5);
        assertThat(response.getBody().getPageNumber()).isEqualTo(0);
        assertThat(response.getBody().getIsFirstPage()).isTrue();
        assertThat(response.getBody().getIsLastPage()).isFalse();
        assertThat(response.getBody().getContent().get(0)).isNotNull();
        assertThat(response.getBody().getContent().get(0).getName()).isEqualTo("Data Processing Pipeline");
    }

}
