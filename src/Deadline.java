import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean complete;

    Deadline(String nameIn, LocalDateTime dateTimeIn) {
        this.setName(nameIn);
        this.setDateTime(dateTimeIn);
    }

    public void complete() {
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
