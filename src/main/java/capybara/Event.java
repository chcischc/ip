package capybara;

import java.time.LocalDateTime;

/**
 * Represents a task that takes place within a specific time range.
 *
 * An Event stores a description along with start and end times.
 * It extends {@code Task} by adding the {@code from} and {@code to}
 * fields, and customizes the string representation for both display
 * and file storage.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an Event task with the given description, start time,
     * and end time.
     *
     * @param name Description of the event task.
     * @param from Start date and time of the event.
     * @param to   End date and time of the event.
     */
    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event suitable for saving
     * to the storage file. The format includes the event marker "E",
     * completion status, description, and the start and end times.
     *
     * @return Encoded string for storage.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() +
                " | " + formatForSave(from) + " | " + formatForSave(to);
    }

    /**
     * Returns a string representation of the event for display to the user.
     * The format includes the event marker "[E]", description, and the
     * start and end times formatted for readability.
     *
     * @return Human-readable string of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatForPrint(from) + " to: " + formatForPrint(to) + ")";
    }
}