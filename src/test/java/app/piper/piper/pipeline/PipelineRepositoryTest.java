package app.piper.piper.pipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest(properties = { "spring.liquibase.contexts=test" })
@Import(ObjectMapper.class)
class PipelineRepositoryTest {

    @Autowired
    private PipelineRepository underTest;

    @Test
    void fetchExistingPipelines() {

        long numberOfPipelines = underTest.count();

        assertEquals(10, numberOfPipelines);
    }

}