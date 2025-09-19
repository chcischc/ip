package capybara.command;

import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;

/**
 * Represents a command that ends the Capybara application.
 *
 * When executed, this command shows a goodbye message through the UI
 * and signals the application to exit.
 */
public class ByeCommand extends Command {

    /**
     * Executes the command to terminate the program.
     *
     * Displays a goodbye message to the user. No changes are made to
     * the task list or storage.
     *
     * @param tasks   The task list (unused in this command).
     * @param ui      The UI used to show the goodbye message.
     * @param storage The storage system (unused in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns {@code true} to indicate that this command exits the program.
     *
     * @return Always {@code true}, as ByeCommand ends the application.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}