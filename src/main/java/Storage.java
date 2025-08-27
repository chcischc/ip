import java.io.*;
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
     * @return an {@code ArrayList<Task>} containing the tasks read from file
     * @throws IOException if an I/O error occurs while creating or reading the file
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return tasks;
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            Boolean isDone = (Integer.parseInt(parts[1]) == 1) ? true : false;
            String description = parts[2];

            switch (type) {
                case "T":
                    ToDo t = new ToDo(description);
                    if (isDone) {
                        t.markAsDone();
                    }
                    tasks.add(t);
                    break;
                case "D":
                    Deadline d = new Deadline(description, parts[3]);
                    if (isDone) {
                        d.markAsDone();
                    }
                    tasks.add(d);
                    break;
                case "E":
                    Event e = new Event(description, parts[3], parts[4]);
                    if (isDone) {
                        e.markAsDone();
                    }
                    tasks.add(e);
                    break;
            }
        }
        br.close();
        return tasks;
    }
}