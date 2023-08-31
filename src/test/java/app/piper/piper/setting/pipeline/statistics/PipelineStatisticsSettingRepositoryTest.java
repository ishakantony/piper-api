package app.piper.piper.setting.pipeline.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ObjectMapper.class)
class PipelineStatisticsSettingRepositoryTest {

    @Autowired
    private PipelineStatisticsSettingRepository underTest;

    @Test
    void saveSettings() {
        PipelineStatisticsSetting.Settings settingData = PipelineStatisticsSetting.DEFAULT;

        PipelineStatisticsSetting setting = new PipelineStatisticsSetting();

        setting.setData(settingData);

        PipelineStatisticsSetting savedSetting = underTest.save(setting);

        PipelineStatisticsSetting settingFromDb = underTest.findById(savedSetting.getId()).orElse(null);

        assertNotNull(settingFromDb);

        PipelineStatisticsSetting.Settings settingDataFromDb = settingFromDb.getData();

        assertNotNull(settingDataFromDb);
        assertEquals(100, settingDataFromDb.getMaxInstanceRecordsPerPipeline());

        System.out.println(underTest.findAll());
    }

}