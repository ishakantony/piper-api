package app.piper.piper.setting.pipeline.statistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class PipelineStatisticsSettingConverter
        implements AttributeConverter<PipelineStatisticsSetting.Settings, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(PipelineStatisticsSetting.Settings attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        }
        catch (Exception e) {
            // Handle the exception appropriately
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    @Override
    public PipelineStatisticsSetting.Settings convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, PipelineStatisticsSetting.Settings.class);
        }
        catch (IOException e) {
            // Handle the exception appropriately
            throw new RuntimeException("Error converting from JSON", e);
        }
    }

}
