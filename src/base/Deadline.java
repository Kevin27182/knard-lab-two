package base;

import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean complete;

    public Deadline(String name, LocalDateTime deadline) {
        this.setName(name);
        this.setDateTime(deadline);
    }

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
