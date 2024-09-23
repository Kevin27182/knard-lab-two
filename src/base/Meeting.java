
// Title: Meeting.java
// Author: Kevin Nard
// Describes a completeable event with a start time, end time, and location

package base;

import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable, DurationEvent, LocationEvent {

    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    // Sets Meeting state based on constructor parameters
    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        this.setName(name);
        this.setDateTime(start);
        this.setEndDateTime(end);
        this.setLocation(location);
    }

    // Get the end datetime of the meeting
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    // Set the end datetime of the meeting
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    // Get the location of the meeting
    public String getLocation() {
        return location;
    }

    // Set the location of the meeting
    public void setLocation(String location) {
        this.location = location;
    }

    // Set the completion status to true
    public void complete() {
        this.complete = true;
    }

    // Get the completion status
    public boolean isComplete() {
        return this.complete;
    }

    // Get the duration of the event
    public Duration getDuration() {
        return Duration.between(this.getDateTime(), this.getEndDateTime());
    }
}
