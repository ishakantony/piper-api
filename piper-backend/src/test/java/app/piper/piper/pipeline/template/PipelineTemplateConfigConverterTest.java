package app.piper.piper.pipeline.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PipelineTemplateConfigConverterTest {

    @Test
    void ableToConvertToDatabaseColumn() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = new PipelineTemplateConfig();
        pipelineTemplateConfig.setAllowedMultiple(true);
        pipelineTemplateConfig.setAllowedOverwrite(true);

        String result = converter.convertToDatabaseColumn(pipelineTemplateConfig);
        assertEquals("isAllowedMultiple=true,isAllowedOverwrite=true", result);
    }

    @Test
    void ableToConvertToDatabaseColumnUsingDefaultConfig() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = PipelineTemplateConfig.DEFAULT;

        String result = converter.convertToDatabaseColumn(pipelineTemplateConfig);
        assertEquals("isAllowedMultiple=false,isAllowedOverwrite=false", result);
    }

    @Test
    void ableToConvertToDatabaseColumnWithOnlyOneOptionTrue() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = new PipelineTemplateConfig();
        pipelineTemplateConfig.setAllowedOverwrite(true);

        String result = converter.convertToDatabaseColumn(pipelineTemplateConfig);
        assertEquals("isAllowedMultiple=false,isAllowedOverwrite=true", result);
    }

    @Test
    void ableToConvertToEntityAttribute() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = converter
                .convertToEntityAttribute("isAllowedMultiple=true,isAllowedOverwrite=true");

        assertTrue(pipelineTemplateConfig.isAllowedMultiple());
        assertTrue(pipelineTemplateConfig.isAllowedOverwrite());
    }

    @Test
    void ableToConvertToEntityAttributeWithDefaultConfig() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = converter
                .convertToEntityAttribute("isAllowedMultiple=false,isAllowedOverwrite=false");

        assertFalse(pipelineTemplateConfig.isAllowedMultiple());
        assertFalse(pipelineTemplateConfig.isAllowedOverwrite());
    }

    @Test
    void ableToConvertToEntityAttributeWithOnlyOneTrueConfig() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = converter
                .convertToEntityAttribute("isAllowedMultiple=true,isAllowedOverwrite=false");

        assertTrue(pipelineTemplateConfig.isAllowedMultiple());
        assertFalse(pipelineTemplateConfig.isAllowedOverwrite());
    }

    @Test
    void ableToConvertToEntityAttributeWithOnlyOneConfigStoreInDb() {
        PipelineTemplateConfigConverter converter = new PipelineTemplateConfigConverter();

        PipelineTemplateConfig pipelineTemplateConfig = converter.convertToEntityAttribute("isAllowedOverwrite=false");

        assertFalse(pipelineTemplateConfig.isAllowedMultiple());
        assertFalse(pipelineTemplateConfig.isAllowedOverwrite());
    }

}