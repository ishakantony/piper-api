package app.piper.piper.setting.store;

import app.piper.piper.setting.pipeline.statistics.PipelineStatisticsSetting;
import app.piper.piper.setting.pipeline.statistics.PipelineStatisticsSettingRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class SettingStore implements ApplicationRunner {

    private final PipelineStatisticsSettingRepository pipelineStatisticsSettingRepository;

    private PipelineStatisticsSetting.Settings pipelineStatisticsSetting;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.pipelineStatisticsSetting = pipelineStatisticsSettingRepository
                .findByName(PipelineStatisticsSetting.PIPELINE_STATISTICS_SETTING_NAME)
                .orElseThrow(SettingMissingException::new).getData();
    }

}
