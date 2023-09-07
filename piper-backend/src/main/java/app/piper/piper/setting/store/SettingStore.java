package app.piper.piper.setting.store;

import app.piper.piper.setting.pipeline.statistics.PipelineStatisticsSetting;
import app.piper.piper.setting.pipeline.statistics.PipelineStatisticsSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingStore {

    private final PipelineStatisticsSettingRepository pipelineStatisticsSettingRepository;

    public PipelineStatisticsSetting.Settings getPipelineStatisticsSettings() {
        return pipelineStatisticsSettingRepository
                .findByName(PipelineStatisticsSetting.PIPELINE_STATISTICS_SETTING_NAME)
                .orElseThrow(SettingMissingException::new).getData();
    }

}
