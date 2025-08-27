import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(task.toFileString() + System.lineSeparator());
        }
        writer.close();
    }

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