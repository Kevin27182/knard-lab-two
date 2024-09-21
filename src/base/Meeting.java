package base;

import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    Meeting(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.setName(name);
        this.setDateTime(startDateTime);
        this.setEndDateTime(endDateTime);
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
