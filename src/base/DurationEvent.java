
// Title: DurationEvent.java
// Author: Kevin Nard
// Interface that describes an event with an end date and time

package base;

import java.time.LocalDateTime;

public interface DurationEvent {

    // Return the end datetime
    LocalDateTime getEndDateTime();

    // Set the end datetime
    void setEndDateTime(LocalDateTime endDateTime);
}
