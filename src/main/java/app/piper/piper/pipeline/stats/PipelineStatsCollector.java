package app.piper.piper.pipeline.stats;

import app.piper.piper.common.Health;
import app.piper.piper.pipeline.instance.PipelineInstance;
import app.piper.piper.pipeline.instance.PipelineInstanceStatus;
import app.piper.piper.setting.store.SettingStore;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import org.springframework.stereotype.Service;

@Service
public class PipelineStatsCollector {

    private final double healthyPipelineTolerance;

    public PipelineStatsCollector(SettingStore settingStore) {
        this.healthyPipelineTolerance = settingStore.getPipelineStatisticsSettings().getHealthyPipelineTolerance();
    }

    public PipelineStats collectStatsFromPipelineInstances(List<PipelineInstance> instances) {
        OffsetDateTime lastSuccessPipelineCompletedAt = instances.stream()
                .filter(instance -> instance.getStatus() == PipelineInstanceStatus.SUCCESSFUL)
                .max(Comparator.comparing(PipelineInstance::getCreatedAt)).map(PipelineInstance::getCreatedAt)
                .orElse(null);

        OffsetDateTime lastFailedPipelineCompletedAt = instances.stream()
                .filter(instance -> instance.getStatus() == PipelineInstanceStatus.FAILED)
                .max(Comparator.comparing(PipelineInstance::getCreatedAt)).map(PipelineInstance::getCreatedAt)
                .orElse(null);

        Integer totalPipelinesCount = instances.stream().max(Comparator.comparing(PipelineInstance::getCreatedAt))
                .map(PipelineInstance::getInstanceNumber).orElse(null);

        List<Duration> instancesDurationsWithCompletedExecution = instances.stream()
                .filter(instance -> instance.getStartAt() != null && instance.getEndAt() != null)
                .map(instance -> Duration.between(instance.getStartAt(), instance.getEndAt())).toList();

        Duration longestRunningDuration = instancesDurationsWithCompletedExecution.stream()
                .max(Comparator.naturalOrder()).orElse(null);

        Duration shortestRunningDuration = instancesDurationsWithCompletedExecution.stream()
                .min(Comparator.naturalOrder()).orElse(null);

        Duration averageRunningDuration = this.calculateAverageDuration(instancesDurationsWithCompletedExecution);

        long successfulInstancesCount = instances.stream()
                .filter(instance -> instance.getStatus() == PipelineInstanceStatus.SUCCESSFUL).count();

        double healthValue = (double) successfulInstancesCount / instances.size();

        Health health = healthValue >= healthyPipelineTolerance ? Health.HEALTHY : Health.UNHEALTHY;

        PipelineStats newStats = new PipelineStats();

        newStats.setLastSuccessPipelineCompletedAt(lastSuccessPipelineCompletedAt);
        newStats.setLastFailedPipelineCompletedAt(lastFailedPipelineCompletedAt);
        newStats.setTotalPipelinesCount(totalPipelinesCount);
        newStats.setLongestRunningDuration(longestRunningDuration);
        newStats.setShortestRunningDuration(shortestRunningDuration);
        newStats.setAverageRunningDuration(averageRunningDuration);
        newStats.setHealth(health);

        return newStats;
    }

    private Duration calculateAverageDuration(List<Duration> durations) {
        OptionalDouble averageRunningDuration = durations.stream().mapToLong(Duration::toMillis).average();

        if (averageRunningDuration.isPresent()) {
            // Convert average milliseconds to Duration
            return Duration.ofMillis((long) averageRunningDuration.getAsDouble());
        }
        else {
            return null;
        }
    }

}
