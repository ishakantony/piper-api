package app.piper.piper.common;

import jakarta.persistence.PrePersist;
import java.time.OffsetDateTime;
import java.util.Objects;

public class TimestampEntityListener {

    @PrePersist
    public void setTimestamps(BaseEntity entity) {
        OffsetDateTime now = OffsetDateTime.now();

        if (Objects.isNull(entity.getCreatedAt())) {
            entity.setCreatedAt(now);
        }

        if (Objects.isNull(entity.getUpdatedAt())) {
            entity.setUpdatedAt(now);
        }
    }

}
