package capybara;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline in the Capybara application.
 *
 * A Deadline stores a description and a specific due date/time.
 * It extends {@code Task} by adding the {@code by} field and
 * customizes both the string representation for display and
 * the serialized format for saving to storage.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a Deadline task with the given description and due date/time.
     *
     * @param name Description of the deadline task.
     * @param by   Date and time by which the task is due.
     */
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