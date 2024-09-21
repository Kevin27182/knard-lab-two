import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean complete;

    Deadline(String name, LocalDateTime dateTime) {
        this.setName(name);
        this.setDateTime(dateTime);
    }

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
