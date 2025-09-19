package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;

/**
 * Represents a command that marks a task as done or not done.
 *
 * The command operates on a one-based task index provided by the user
 * (converted to zero-based internally). When executed, it updates the
 * completion status of the specified task, provides feedback to the user,
 * and persists the updated task list to storage.
 */
public class MarkCommand extends Command {
    private final int index0;
    private final boolean mark;

    /**
     * Constructs a MarkCommand with the specified target index and status.
     *
     * @param index0 Zero-based index of the task to mark.
     * @param mark   {@code true} to mark the task as done,
     *               {@code false} to mark it as not done.
     */
    public MarkCommand(int index0, boolean mark) {
        this.index0 = index0;
        this.mark = mark;
    }

    /**
     * Executes the mark/unmark operation.
     *
     * Attempts to mark the specified task as done or not done, prints a
     * confirmation message via the UI, and saves the updated task list.
     * If the given index does not correspond to a valid task, an error is
     * raised.
     *
     * @param tasks   The task list to modify.
     * @param ui      The UI used to provide feedback to the user.
     * @param storage The storage used to save the updated task list.
     * @throws CapyException    If the task index is invalid.
     * @throws java.io.IOException If saving to storage fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        try {
            Task t = tasks.get(index0);
            if (mark) {
                t.markAsDone();
                ui.println("Nice! I've marked this task as done:");
            } else {
                t.markAsNotDone();
                ui.println("OK, I've marked this task as not done yet:");
            }
            ui.println("  " + t);
            storage.save(tasks.asArrayList());
        } catch (IndexOutOfBoundsException oob) {
            int oneBased = index0 + 1;
            throw new CapyException("Capybara can’t find task number " + oneBased + " in the list.");
        }
    }
}
