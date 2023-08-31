package app.piper.piper.setting.pipeline.statistics;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineStatisticsSettingRepository extends CrudRepository<PipelineStatisticsSetting, UUID> {

    @Transactional(readOnly = true)
    Optional<PipelineStatisticsSetting> findByName(String name);

}
