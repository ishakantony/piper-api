package app.piper.piper.pipeline.template;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class PipelineTemplateConfigConverter implements AttributeConverter<PipelineTemplateConfig, String> {

    private static final String FIELD_NAME_TO_IGNORE = "DEFAULT";

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(PipelineTemplateConfig pipelineTemplateConfig) {
        if (pipelineTemplateConfig == null) {
            return null;
        }

        Class<?> clazz = pipelineTemplateConfig.getClass();
        Field[] fields = clazz.getDeclaredFields();

        return Arrays.stream(fields).filter(field -> !FIELD_NAME_TO_IGNORE.equals(field.getName())).map(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(pipelineTemplateConfig);
                return field.getName() + "=" + value;
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field", e);
            }
        }).collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public PipelineTemplateConfig convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        PipelineTemplateConfig pipelineTemplateConfig = new PipelineTemplateConfig();
        String[] keyValuePairs = s.split(SEPARATOR);

        for (String keyValuePair : keyValuePairs) {
            String[] parts = keyValuePair.split("=");
            if (parts.length == 2) {
                String fieldName = parts[0];
                boolean fieldValue = Boolean.parseBoolean(parts[1]);

                try {
                    Field field = pipelineTemplateConfig.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(pipelineTemplateConfig, fieldValue);
                }
                catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Error setting field value", e);
                }
            }
        }

        return pipelineTemplateConfig;
    }

}
