package app.piper.piper.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(properties = { "spring.liquibase.contexts=test" })
@Import(ObjectMapper.class)
class PipelineRepositoryTest {

    @Autowired
    private PipelineRepository underTest;

    @Test
    void fetchExistingPipelines() {

        long numberOfPipelines = underTest.count();

        assertTrue(numberOfPipelines > 0);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void testUniqueName() {

        // Create and save a pipeline with a unique name
        Pipeline pipeline = new Pipeline();
        pipeline.setName("Unique Pipeline");
        pipeline.setSlug("pipeline-slug");
        underTest.save(pipeline);

        // Attempt to save another pipeline with the same name
        Pipeline duplicatePipeline = new Pipeline();
        duplicatePipeline.setName("Unique Pipeline");
        duplicatePipeline.setSlug("duplicated-slug");

        // Verify that saving the duplicate pipeline throws ConstraintViolationException
        DataIntegrityViolationException expectedException = assertThrows(DataIntegrityViolationException.class,
                () -> underTest.save(duplicatePipeline));

        assertTrue(expectedException.getMostSpecificCause().getMessage()
                .contains(DuplicatePipelineNameException.DB_CONSTRAINT_NAME));

    }

}