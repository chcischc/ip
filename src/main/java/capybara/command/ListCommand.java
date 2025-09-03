package capybara.command;

import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;

/**
 * Represents a command that lists all tasks currently in the task list.
 * <p>
 * The command retrieves the formatted representation of the task list
 * and displays it to the user through the {@link Ui}.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command.
     * <p>
     * Retrieves the full set of tasks from the given {@code TaskList},
     * formats them into a user-readable string, and displays them through
     * the UI. The storage is not modified by this command.
     *
     * @param tasks   the current task list to display
     * @param ui      the UI handler used to show the task list
     * @param storage the storage handler (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.formatAll());
    }
}