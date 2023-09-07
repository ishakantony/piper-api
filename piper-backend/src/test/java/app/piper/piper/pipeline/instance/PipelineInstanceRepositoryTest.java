package app.piper.piper.pipeline.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest(properties = { "spring.liquibase.contexts=test" })
@Import(ObjectMapper.class)
class PipelineInstanceRepositoryTest {

    @Autowired
    private PipelineInstanceRepository underTest;

    @Test
    void fetchExistingPipelineInstances() {

        long numberOfPipelineInstances = underTest.count();

        assertEquals(12, numberOfPipelineInstances);
    }

}