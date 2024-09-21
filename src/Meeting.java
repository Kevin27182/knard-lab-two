import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDatetime;
    private String location;
    private boolean complete;

    Meeting(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.setName(name);
        this.setDateTime(startDateTime);
        this.setEndDatetime(endDateTime);
        this.setLocation(location);
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
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
        return Duration.between(this.getDateTime(), this.getEndDatetime());
    }
}
