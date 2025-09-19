package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.ToDo;
import capybara.Ui;

/**
 * Represents a command that adds a {@code ToDo} task to the task list.
 *
 * A ToDo requires only a textual description. When executed, this command
 * creates the task, adds it to the task list, saves the updated list to
 * storage, and notifies the user through the UI.
 */
public class AddToDoCommand extends Command {
    private final String desc;

    /**
     * Creates an {@code AddToDoCommand} with the given description.
     *
     * @param desc Description of the ToDo task.
     */
    public AddToDoCommand(String desc) {
        this.desc = desc;
    }

    /**
     * Executes the command to add a {@code ToDo} task.
     *
     * Creates a new {@code ToDo} with the given description, appends it to
     * the task list, saves the updated list to persistent storage, and
     * displays feedback to the user.
     *
     * @param tasks   The task list to which the ToDo is added.
     * @param ui      The UI used to show messages to the user.
     * @param storage The storage system for saving tasks.
     * @throws CapyException       If there is an error in creating or handling the task.
     * @throws java.io.IOException If there is an error saving the updated task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        Task t = new ToDo(desc);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}