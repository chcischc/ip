package capybara;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds and manages the list of tasks.
 * Keeps helpers used by UI and capybara.Storage.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Start with an empty list. */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /** Start with an initial list (e.g., from capybara.Storage.load()). */
    public TaskList(List<Task> initial) {
        if (initial == null) {
            this.tasks = new ArrayList<Task>();
        } else {
            this.tasks = new ArrayList<Task>(initial);
        }
    }

    /** Add a task to the end of the list. */
    public void add(Task t) {
        tasks.add(t);
    }

    /** Get the task at zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Remove and return the task at zero-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Number of tasks. */
    public int size() {
        return tasks.size();
    }

    /** Whether the list is empty. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Expose the underlying ArrayList for capybara.Storage.save(...).
     * Prefer not to mutate the returned list outside of capybara.TaskList methods.
     */
    public ArrayList<Task> asArrayList() {
        return tasks;
    }

    /**
     * Returns the exact listing text your UI expects.
     * If empty, returns a friendly message instead of a blank list.
     */
    public String formatAll() {
        if (tasks.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            int displayIndex = i + 1;
            sb.append(displayIndex);
            sb.append(". ");
            sb.append(tasks.get(i));
            sb.append("\n");
        }

        return sb.toString().trim();
    }
}