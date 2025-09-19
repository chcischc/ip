package capybara;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a generic task in the Capybara application.
 *
 * A Task stores a textual description and a completion status
 * (done or not done). It also provides utilities for formatting
 * date/time values for display to the user and for saving to the
 * persistent storage file.
 *
 * Subclasses such as {@code ToDo}, {@code Deadline}, and
 * {@code Event} extend this class to include additional fields
 * such as due dates or event time ranges.
 *
 * This class defines the base behavior for marking tasks as done,
 * marking them as not done, and serializing them into a string
 * representation for both display and file storage.
 */
public class Task {
    protected static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("MMM d yyyy");
    protected static final DateTimeFormatter DATE_TIME_FMT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    protected static final java.time.format.DateTimeFormatter SAVE_DATE =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected static final java.time.format.DateTimeFormatter SAVE_DATE_TIME =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String description;
    private boolean isDone = false;

    /**
     * Creates a new task with the given description.
     *
     * @param description Description of the task.
     */
    Task(String description) {
        this.description = description;
    }

    /**
     * Returns whether this task has been marked as done.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the given date/time for display.
     * Dates at midnight are shown as a date only; otherwise both date and
     * time are shown.
     *
     * @param dt The date/time to format.
     * @return A formatted string, or an empty string if {@code dt} is null.
     */
    protected static String formatForPrint(LocalDateTime dt) {
        if (dt == null) return ""; // defensive
        return dt.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(DATE_FMT)
                : dt.format(DATE_TIME_FMT);
    }

    /**
     * Returns a string representation of the given date/time for saving.
     * Dates at midnight are saved as date only; otherwise both date and
     * time are saved.
     *
     * @param dt The date/time to format.
     * @return A formatted string, or an empty string if {@code dt} is null.
     */
    protected static String formatForSave(java.time.LocalDateTime dt) {
        if (dt == null) return "";
        return dt.toLocalTime().equals(java.time.LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(SAVE_DATE)       // only date
                : dt.format(SAVE_DATE_TIME);               // date + time
    }

    /**
     * Returns a string representation of this task suitable for saving
     * to the storage file. The format includes the completion status
     * and description.
     *
     * @return Encoded string for storage.
     */
    public String toFileString() {
        String isDoneMark = (isDone ? "1" : "0");
        return " | " + isDoneMark + " | " + description;
    }

    /**
     * Returns a string representation of this task for display to the user.
     * The format includes a checkmark if done, or a blank box if not done,
     * followed by the description.
     *
     * @return Human-readable string of the task.
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " + description : "[ ] " + description);
    }
}