package base;

import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable, DurationEvent, LocationEvent {
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        this.setName(name);
        this.setDateTime(start);
        this.setEndDateTime(end);
        this.setLocation(location);
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public Duration getDuration() {
        return Duration.between(this.getDateTime(), this.getEndDateTime());
    }
}
