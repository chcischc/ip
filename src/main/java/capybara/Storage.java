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
        java.time.format.DateTimeFormatter DATE_FMT =
                java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;                // yyyy-MM-dd
        java.time.format.DateTimeFormatter DATETIME_FMT =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // yyyy-MM-dd 18:00

        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split(" \\| ");
                String type = parts[0];
                boolean isDone = (Integer.parseInt(parts[1]) == 1);
                String description = parts[2];

                switch (type) {
                case "T": {
                    Task t = buildToDo(description, isDone);
                    tasks.add(t);
                    break;
                }
                case "D": {
                    Task d = parseDeadline(parts[3], description, isDone, DATE_FMT, DATETIME_FMT);
                    if (d == null) {
                        return null; // keep original early-null behavior on bad date
                    }
                    tasks.add(d);
                    break;
                }
                case "E": {
                    Task e = parseEvent(parts[3], parts[4], description, isDone, DATE_FMT, DATETIME_FMT);
                    if (e == null) {
                        return null; // keep original early-null behavior on bad from/to
                    }
                    tasks.add(e);
                    break;
                }
                default:
                    // Unknown record type: do nothing (effectively skip line), same net behavior
                    break;
                }
            }
        }

        return tasks;
    }


    // helpers

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

    private static Task parseEvent(
            String fromRaw,
            String toRaw,
            String description,
            boolean isDone,
            java.time.format.DateTimeFormatter DATE_FMT,
            java.time.format.DateTimeFormatter DATETIME_FMT) {

        LocalDateTime from, to;

        try {
            from = LocalDateTime.parse(fromRaw, DATETIME_FMT);
        } catch (java.time.format.DateTimeParseException e1) {
            try {
                from = LocalDate.parse(fromRaw, DATE_FMT).atStartOfDay();
            } catch (java.time.format.DateTimeParseException e2) {
                System.out.println("capybara.Capybara can’t read the /from time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                return null;
            }
        }

        try {
            to = LocalDateTime.parse(toRaw, DATETIME_FMT);
        } catch (java.time.format.DateTimeParseException e1) {
            try {
                to = LocalDate.parse(toRaw, DATE_FMT).atStartOfDay();
            } catch (java.time.format.DateTimeParseException e2) {
                System.out.println("capybara.Capybara can’t read the /to time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                return null;
            }
        }

        Event e = new Event(description, from, to);
        if (isDone) {
            e.markAsDone();
        }
        return e;
    }
}
