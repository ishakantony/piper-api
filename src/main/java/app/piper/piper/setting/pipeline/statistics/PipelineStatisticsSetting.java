package app.piper.piper.setting.pipeline.statistics;

import app.piper.piper.setting.BaseSetting;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "setting")
@Setter
@Getter
public class PipelineStatisticsSetting extends BaseSetting {

    @Transient
    public static final Settings DEFAULT = Settings.builder().build();

    @Transient
    public static final String PIPELINE_STATISTICS_SETTING_NAME = "pipeline.statistics";

    public PipelineStatisticsSetting() {
        this.setName(PIPELINE_STATISTICS_SETTING_NAME);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Settings implements Serializable {

        // How many pipeline instances we can store per pipeline
        @Builder.Default
        private int maxInstanceRecordsPerPipeline = 100;

        // Beyond this value, we consider the pipeline healthy
        @Builder.Default
        private double healthyPipelineTolerance = 0.7;

    }

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    @Convert(converter = PipelineStatisticsSettingConverter.class)
    private Settings data;

}
