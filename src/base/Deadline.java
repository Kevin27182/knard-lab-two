
// Title: Deadline.java
// Author: Kevin Nard
// Describes a completeable event with a single deadline

package base;

import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {

    private boolean complete;

    // Set Deadline state based on constructor parameters
    public Deadline(String name, LocalDateTime deadline) {
        this.setName(name);
        this.setDateTime(deadline);
    }

    // Set completion status to true
    public void complete() {
        this.complete = true;
    }

    // Return the completion status
    public boolean isComplete() {
        return complete;
    }
}
