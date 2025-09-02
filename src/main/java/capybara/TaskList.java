package capybara;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a dynamic list of tasks.
 * Provides operations to add, remove, retrieve, and format tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Creates a task list initialized with the given tasks.
     *
     * @param initial List of tasks to populate the task list with.
     */
    public TaskList(List<Task> initial) {
        if (initial == null) {
            this.tasks = new ArrayList<Task>();
        } else {
            this.tasks = new ArrayList<Task>(initial);
        }
    }

    public TaskList getFilteredTaskList(String keyword) {
        if (isEmpty()) {
            return new TaskList();
        }
        TaskList filtered = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                filtered.add(task);
            }
        }
        return filtered;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param t Task to be added.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Zero-based index of the task.
     * @return Task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Zero-based index of the task.
     * @return Removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if there are no tasks, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the underlying task list as a mutable {@link ArrayList}.
     * This method is provided to support {@code capybara.Storage.save(...)}.
     * Clients should avoid mutating the returned list directly, and instead
     * use {@link TaskList} methods to preserve invariants.
     *
     * @return Underlying {@code ArrayList<Task>} representing all tasks.
     */
    public ArrayList<Task> asArrayList() {
        return tasks;
    }

    /**
     * Formats all tasks into a numbered string suitable for display.
     *
     * @return Formatted string containing all tasks.
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