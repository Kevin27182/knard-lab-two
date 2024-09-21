package base;

import java.time.LocalDateTime;

public abstract class Event implements Comparable<Event> {
    private String name;
    private LocalDateTime dateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int compareTo(Event o) {
        if (this.dateTime.isBefore(o.getDateTime()))
            return -1;

        if (this.dateTime.isAfter(o.getDateTime()))
            return 1;

        return 0;
    }
}
