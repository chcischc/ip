package capybara;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the current task list to the storage file.
     * <p>
     * Each task will be written in a text-based format defined by its
     * {@code toFileString()} representation. If the parent directory does not
     * exist, it will be created automatically.
     *
     * @param tasks the list of tasks to save to disk
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(task.toFileString() + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Loads the task list from the storage file.
     * <p>
     * If the file or parent directory does not exist, they will be created and
     * an empty task list will be returned. Lines in the file that do not follow
     * the expected format will be skipped.
     *
     * @return an {@code ArrayList<capybara.Task>} containing the tasks read from file
     * @throws IOException if an I/O error occurs while creating or reading the file
     */
    public ArrayList<Task> load() throws IOException {
        /**
         * [AI-ASSISTED] Refactor note — Storage.load()
         *
         * Tool: ChatGPT (2025-09-19, Asia/Singapore).
         * Goal: shorten load() (<30 lines) and improve readability (SLAP) by
         * delegating work to helpers, while preserving observable behavior.
         *
         * Mechanical changes (behavior-preserving):
         * - Extracted file creation to initEmptyFile(...).
         * - Extracted per-line handling to processRecord(...).
         * - Continued using existing helpers buildToDo/parseDeadline/parseEvent.
         * - Kept try-with-resources for BufferedReader.
         *
         * Behavior preserved:
         * - Same user-facing messages for bad dates (/from, /to, generic).
         * - Same early-null return on malformed date/time (matches legacy).
         * - Empty lines skipped; unknown record types effectively ignored.
         * - Missing file -> create parent dirs + empty file, return empty list.
         * - Done flag parsing unchanged (1 => done).
         *
         * Review: human-verified output on sample data and diff-checked for
         * regressions in messages and return semantics.
         */

        java.time.format.DateTimeFormatter DATE_FMT =
                java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
        java.time.format.DateTimeFormatter DATETIME_FMT =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return initEmptyFile(file, tasks);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                if (!processRecord(trimmed, tasks, DATE_FMT, DATETIME_FMT)) {
                    return null; // keep original early-null behavior
                }
            }
        }
        return tasks;
    }


    // helpers
    private ArrayList<Task> initEmptyFile(File file, ArrayList<Task> tasks) throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();
        return tasks; // empty list
    }

    /** Returns false if parsing should trigger the original early-null behavior. */
    private boolean processRecord(
            String line,
            ArrayList<Task> tasks,
            java.time.format.DateTimeFormatter DATE_FMT,
            java.time.format.DateTimeFormatter DATETIME_FMT) {

        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = (Integer.parseInt(parts[1]) == 1);
        String description = parts[2];

        switch (type) {
        case "T": tasks.add(buildToDo(description, isDone)); return true;
        case "D": {
            Task d = parseDeadline(parts[3], description, isDone, DATE_FMT, DATETIME_FMT);
            if (d == null) return false; tasks.add(d); return true;
        }
        case "E": {
            Task e = parseEvent(parts[3], parts[4], description, isDone, DATE_FMT, DATETIME_FMT);
            if (e == null) return false; tasks.add(e); return true;
        }
        default: return true; // unknown type -> skip (same net behavior)
        }
    }

    private static Task buildToDo(String description, boolean isDone) {
        ToDo t = new ToDo(description);
        if (isDone) {
            t.markAsDone();
        }
        return t;
    }

    private static Task parseDeadline(
            String rawBy,
            String description,
            boolean isDone,
            java.time.format.DateTimeFormatter DATE_FMT,
            java.time.format.DateTimeFormatter DATETIME_FMT) {

        LocalDateTime by;
        try {
            by = LocalDateTime.parse(rawBy, DATETIME_FMT);
        } catch (java.time.format.DateTimeParseException e1) {
            try {
                by = LocalDate.parse(rawBy, DATE_FMT).atStartOfDay();
            } catch (java.time.format.DateTimeParseException e2) {
                System.out.println("capybara.Capybara can’t read that date 🐹🍊. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                return null; // same as original
            }
        }

        Deadline d = new Deadline(description, by);
        if (isDone) {
            d.markAsDone();
        }
        return d;
    }

    /**
     * Builds an Event from serialized fields.
     *
     * Behavior preserved:
     * - Uses a shared parser for /from and /to that prints the SAME messages
     *   as before on bad input and returns null to trigger the original
     *   early-null behavior in callers.
     * - Marks as done if isDone is true.
     *
     * Refactor intent: shorten callers (e.g., load()) by delegating
     * date parsing and message handling.
     * [AI-ASSISTED] Helper extraction and wording suggested by ChatGPT; reviewed.
     */
    private static Task parseEvent(
            String fromRaw,
            String toRaw,
            String description,
            boolean isDone,
            java.time.format.DateTimeFormatter DATE_FMT,
            java.time.format.DateTimeFormatter DATETIME_FMT) {

        LocalDateTime from = parseDateOrDateTimeStrict(fromRaw, "/from", DATE_FMT, DATETIME_FMT);
        if (from == null) return null;

        LocalDateTime to = parseDateOrDateTimeStrict(toRaw, "/to", DATE_FMT, DATETIME_FMT);
        if (to == null) return null;

        Event e = new Event(description, from, to);
        if (isDone) e.markAsDone();
        return e;
    }

    /**
     * Parses a date/time string using DATETIME_FMT first (yyyy-MM-dd HH:mm),
     * then falls back to DATE_FMT (yyyy-MM-dd) mapped to 00:00.
     *
     * On failure, prints the SAME user-facing message that legacy code emitted:
     * - "/from" -> "can’t read the /from time..."
     * - "/to"   -> "can’t read the /to time..."
     * - otherwise -> generic "can’t read that date ..."
     *
     * Returns:
     * - LocalDateTime when parsed, or
     * - null on error (to preserve legacy early-null semantics).
     *
     * Purpose: centralize boilerplate and keep callers short without changing
     * behavior or messages.
     */
    private static LocalDateTime parseDateOrDateTimeStrict(
            String raw,
            String label,
            java.time.format.DateTimeFormatter DATE_FMT,
            java.time.format.DateTimeFormatter DATETIME_FMT) {

        try {
            return LocalDateTime.parse(raw, DATETIME_FMT);
        } catch (java.time.format.DateTimeParseException e1) {
            try {
                return LocalDate.parse(raw, DATE_FMT).atStartOfDay();
            } catch (java.time.format.DateTimeParseException e2) {
                if ("/from".equals(label)) {
                    System.out.println("capybara.Capybara can’t read the /from time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                } else if ("/to".equals(label)) {
                    System.out.println("capybara.Capybara can’t read the /to time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                } else {
                    System.out.println("capybara.Capybara can’t read that date 🐹🍊. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                }
                return null;
            }
        }
    }

}
