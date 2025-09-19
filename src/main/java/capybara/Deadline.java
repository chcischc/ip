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

    /**
     * Returns a string representation of the deadline suitable for saving
     * to the storage file. The format includes the deadline marker "D",
     * completion status, description, and the due date/time.
     *
     * @return Encoded string for storage.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + formatForSave(by);
    }

    /**
     * Returns a string representation of the deadline for display to the user.
     * The format includes the deadline marker "[D]", description, and the
     * due date/time formatted for readability.
     *
     * @return Human-readable string of the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatForPrint(by) + ")";
    }

}