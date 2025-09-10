package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;

/**
 * Represents a command that deletes a task from the task list by index.
 * <p>
 * The command removes the specified task, displays a confirmation message
 * to the user, and updates the persistent storage. If the index is invalid,
 * a {@link CapyException} is thrown.
 */
public class DeleteCommand extends Command {
    private final int index0;

    /**
     * Constructs a {@code DeleteCommand} targeting the task at the specified index.
     *
     * @param index0 the zero-based index of the task to delete
     */
    public DeleteCommand(int index0) {
        this.index0 = index0;
    }

    /**
     * Executes the delete command.
     * <p>
     * Removes the task at the given index from the task list, displays the
     * removed task and the updated list size through the UI, and saves the
     * updated task list to storage. If the index is invalid, a
     * {@code CapyException} is thrown.
     *
     * @param tasks    the current task list to operate on
     * @param ui       the UI handler used to show feedback to the user
     * @param storage  the storage handler used to persist the updated list
     * @throws CapyException        if the given index does not correspond to a valid task
     * @throws java.io.IOException  if saving the updated task list fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        try {
            Task removed = tasks.remove(index0);
            ui.showRemoved(removed, tasks.size());
            storage.save(tasks.asArrayList());
        } catch (IndexOutOfBoundsException oob) {
            int oneBased = index0 + 1;
            throw new CapyException("Capybara can’t find task number " + oneBased + " in the list.");
        }
    }
}
