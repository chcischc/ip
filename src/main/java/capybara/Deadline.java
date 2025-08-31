package capybara;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String name, LocalDateTime by) {
        super(name);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + formatForSave(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatForPrint(by) + ")";
    }

}