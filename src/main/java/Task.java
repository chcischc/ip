import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Task {
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

    Task(String description) {
        this.description = description;
    }

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

    protected static String formatForPrint(LocalDateTime dt) {
        if (dt == null) return ""; // defensive
        return dt.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(DATE_FMT)
                : dt.format(DATE_TIME_FMT);
    }

    protected static String formatForSave(java.time.LocalDateTime dt) {
        if (dt == null) return "";
        return dt.toLocalTime().equals(java.time.LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(SAVE_DATE)       // only date
                : dt.format(SAVE_DATE_TIME);               // date + time
    }

    public String toFileString() {
        String isDoneMark = (isDone ? "1" : "0");
        return " | " + isDoneMark + " | " + description;
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " + description : "[ ] " + description);
    }
}