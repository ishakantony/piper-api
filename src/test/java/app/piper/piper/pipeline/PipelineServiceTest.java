package app.piper.piper.pipeline;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ComponentScan(basePackages = { "app.piper.piper.pipeline", "app.piper.piper.util" })
@Import({ ObjectMapper.class })
class PipelineServiceTest {

    @Autowired
    private PipelineService underTest;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Test
    void testCreatePipeline() {

        PipelineRequest validPipelineRequest = new PipelineRequest();
        validPipelineRequest.setName("Test Create Pipeline");
        validPipelineRequest.setDescription("Complex pipeline to process large set of data from external system");

        PipelineResponse result = underTest.createPipeline(validPipelineRequest);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getSlug());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        assertEquals(validPipelineRequest.getName(), result.getName());
        assertEquals(validPipelineRequest.getDescription(), result.getDescription());

    }

    @Test
    void testCreatePipeline_NameIsDuplicated() {

        PipelineRequest validPipelineRequest = new PipelineRequest();
        validPipelineRequest.setName("Custom Complex Pipeline");
        validPipelineRequest.setDescription("Complex pipeline to process large set of data from external system");

        assertDoesNotThrow(() -> underTest.createPipeline(validPipelineRequest));

        long numberOfPipelinesAfterFirstInsert = pipelineRepository.count();

        assertEquals(1, numberOfPipelinesAfterFirstInsert);

        DuplicatePipelineNameException expectedException = assertThrows(DuplicatePipelineNameException.class,
                () -> underTest.createPipeline(validPipelineRequest));

        assertEquals("Pipeline name must be unique", expectedException.getMessage());

    }

}