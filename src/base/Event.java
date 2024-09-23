
// Title: Event.java
// Author: Kevin Nard
// Abstract class for defining a comparable event

package base;

import java.time.LocalDateTime;

public abstract class Event implements Comparable<Event> {

    private String name;
    private LocalDateTime dateTime;

    // Get the name of the event
    public String getName() {
        return name;
    }

    // Set the event name
    public void setName(String name) {
        this.name = name;
    }

    // Get the start datetime of the event
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Set the start datetime of the event
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Compare this event to another event by datetime
    public int compareTo(Event o) {
        if (this.dateTime.isBefore(o.getDateTime()))
            return -1;

        if (this.dateTime.isAfter(o.getDateTime()))
            return 1;

        return 0;
    }
}
