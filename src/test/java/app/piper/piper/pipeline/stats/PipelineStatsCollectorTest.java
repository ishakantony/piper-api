package app.piper.piper.pipeline.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import app.piper.piper.common.Health;
import app.piper.piper.pipeline.instance.PipelineInstance;
import app.piper.piper.pipeline.instance.PipelineInstanceStatus;
import app.piper.piper.setting.pipeline.statistics.PipelineStatisticsSetting;
import app.piper.piper.setting.store.SettingStore;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PipelineStatsCollectorTest {

    @Mock
    private SettingStore settingStore;

    private static final AtomicInteger pipelineCount = new AtomicInteger(1);

    @Test
    void testCollectStatsFromPipelineInstances_IsUnhealthy() {
        // Create a mock SettingStore
        when(settingStore.getPipelineStatisticsSettings()).thenReturn(new PipelineStatisticsSetting.Settings(100, 0.8));

        // Create instances for testing
        PipelineInstance successfulInstance1 = createPipelineInstance(PipelineInstanceStatus.SUCCESSFUL);
        PipelineInstance successfulInstance2 = createPipelineInstance(PipelineInstanceStatus.SUCCESSFUL);
        PipelineInstance successfulInstance3 = createPipelineInstance(PipelineInstanceStatus.SUCCESSFUL);
        PipelineInstance failedInstance = createPipelineInstance(PipelineInstanceStatus.FAILED);

        List<PipelineInstance> instances = new ArrayList<>();
        instances.add(successfulInstance1);
        instances.add(successfulInstance2);
        instances.add(successfulInstance3);
        instances.add(failedInstance);

        PipelineStatsCollector statsCollector = new PipelineStatsCollector(settingStore);
        PipelineStats stats = statsCollector.collectStatsFromPipelineInstances(instances);

        // Assertions
        assertEquals(successfulInstance3.getCreatedAt(), stats.getLastSuccessPipelineCompletedAt());
        assertEquals(failedInstance.getCreatedAt(), stats.getLastFailedPipelineCompletedAt());
        assertEquals(Integer.valueOf(4), stats.getTotalPipelinesCount());
        assertEquals(Health.UNHEALTHY, stats.getHealth());
        // TODO - Check duration related things
    }

    private PipelineInstance createPipelineInstance(PipelineInstanceStatus status) {
        PipelineInstance instance = new PipelineInstance();
        instance.setStatus(status);
        instance.setCreatedAt(OffsetDateTime.now());
        instance.setStartAt(OffsetDateTime.now());
        instance.setEndAt(OffsetDateTime.now().plusSeconds(5));
        instance.setInstanceNumber(pipelineCount.getAndIncrement());
        return instance;
    }

}