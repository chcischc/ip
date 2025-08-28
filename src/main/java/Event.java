import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() +
                " | " + formatForSave(from) + " | " + formatForSave(to);
    }


    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatForPrint(from) + " to: " + formatForPrint(to) + ")";
    }
}