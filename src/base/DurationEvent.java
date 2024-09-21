package base;

import java.time.LocalDateTime;

public interface DurationEvent {
    LocalDateTime getEndDateTime();
    void setEndDateTime(LocalDateTime endDateTime);
}
