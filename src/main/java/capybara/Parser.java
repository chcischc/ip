package capybara;

import capybara.command.AddDeadlineCommand;
import capybara.command.AddEventCommand;
import capybara.command.AddToDoCommand;
import capybara.command.ByeCommand;
import capybara.command.Command;
import capybara.command.DeleteCommand;
import capybara.command.ListCommand;
import capybara.command.MarkCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Command parse(String input) throws CapyException {
        if (input == null || input.isBlank()) {
            throw new UnknownCommandException("");
        }

        String trimmed = input.trim();
        String[] parts = trimmed.split("\\s+", 2);
        String head = parts[0].toLowerCase();
        String args;

        if (parts.length > 1) {
            args = parts[1].trim();
        } else {
            args = "";
        }

        if (head.equals("bye")) {
            return new ByeCommand();
        } else if (head.equals("list")) {
            return new ListCommand();
        } else if (head.equals("todo")) {
            return parseToDo(args);
        } else if (head.equals("deadline")) {
            return parseDeadline(args);
        } else if (head.equals("event")) {
            return parseEvent(args);
        } else if (head.equals("mark")) {
            return parseMark(args, true);
        } else if (head.equals("unmark")) {
            return parseMark(args, false);
        } else if (head.equals("delete")) {
            return parseDelete(args);
        } else {
            throw new UnknownCommandException(head);
        }
    }

    private static Command parseToDo(String args) throws CapyException {
        if (args.isBlank()) {
            throw new EmptyDescriptionException("todo");
        }
        return new AddToDoCommand(args);
    }

    private static Command parseDeadline(String args) throws CapyException {
        if (args.isBlank()) {
            throw new EmptyDescriptionException("deadline");
        }

        String[] split = args.split("\\s*/by\\s+", 2);
        boolean missingBy = split.length < 2;
        boolean emptyDesc = !missingBy && split[0].trim().isEmpty();
        boolean emptyTime = !missingBy && split[1].trim().isEmpty();

        if (missingBy || emptyTime) {
            throw new EmptyTimeException("deadline");
        } else if (emptyDesc) {
            throw new EmptyDescriptionException("deadline");
        }

        String desc = split[0].trim();
        String byRaw = split[1].trim();
        LocalDateTime by = parseDateOrDateTime(byRaw, "/by");
        return new AddDeadlineCommand(desc, by);
    }

    private static Command parseEvent(String args) throws CapyException {
        if (args.isBlank()) {
            throw new EmptyDescriptionException("event");
        }

        String[] splitByFrom = args.split("\\s*/from\\s+", 2);
        boolean missingFrom = splitByFrom.length < 2;

        if (missingFrom) {
            throw new EmptyTimeException("event");
        }

        String desc = splitByFrom[0].trim();

        if (desc.isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        String remainder = splitByFrom[1];
        String[] splitByTo = remainder.split("\\s*/to\\s+", 2);
        boolean missingTo = splitByTo.length < 2;

        if (missingTo) {
            throw new EmptyTimeException("event");
        }

        String fromRaw = splitByTo[0].trim();
        String toRaw = splitByTo[1].trim();

        if (fromRaw.isEmpty() || toRaw.isEmpty()) {
            throw new EmptyTimeException("event");
        }

        LocalDateTime from = parseDateOrDateTime(fromRaw, "/from");
        LocalDateTime to = parseDateOrDateTime(toRaw, "/to");
        return new AddEventCommand(desc, from, to);
    }

    private static Command parseMark(String args, boolean mark) throws CapyException {
        int idx0 = parseOneBasedIndex(args, mark ? "mark" : "unmark");
        return new MarkCommand(idx0, mark);
    }

    private static Command parseDelete(String args) throws CapyException {
        int idx0 = parseOneBasedIndex(args, "delete");
        return new DeleteCommand(idx0);
    }

    private static int parseOneBasedIndex(String raw, String kind) throws CapyException {
        if (raw == null || raw.isBlank()) {
            throw new EmptyTaskNumberException(kind);
        }

        try {
            int n = Integer.parseInt(raw.trim());
            if (n <= 0) {
                throw new NumberFormatException();
            }
            return n - 1;
        } catch (NumberFormatException e) {
            throw new CapyException("capybara.Capybara tilts head… '" + raw + "' is not a valid task number.");
        }
    }

    private static LocalDateTime parseDateOrDateTime(String s, String label) throws CapyException {
        String t = s.trim();

        try {
            if (t.length() == 10) {
                LocalDate d = LocalDate.parse(t, DATE);
                return d.atStartOfDay();
            } else {
                return LocalDateTime.parse(t, DATE_TIME);
            }
        } catch (Exception e) {
            if ("/from".equals(label)) {
                throw new CapyException("capybara.Capybara can’t read the /from time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
            } else if ("/to".equals(label)) {
                throw new CapyException("capybara.Capybara can’t read the /to time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
            } else {
                throw new CapyException("capybara.Capybara can’t read that date 🐹🍊. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
            }
        }
    }
}
