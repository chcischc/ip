package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;

/**
 * Represents an abstract user command in the Capybara task manager.
 * <p>
 * Concrete subclasses of {@code Command} implement specific user actions
 * such as adding, deleting, listing, or finding tasks. Each command
 * encapsulates its own execution logic and can optionally indicate
 * whether the application should exit after running.
 */
public abstract class Command {

    /**
     * Executes the command using the given task list, UI, and storage.
     *
     * @param tasks    the current task list to operate on
     * @param ui       the UI handler used to provide feedback to the user
     * @param storage  the storage handler used to persist changes
     * @throws CapyException        if the command fails due to invalid input or state
     * @throws java.io.IOException  if an error occurs when accessing storage
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException;

    /**
     * Determines whether this command signals the application to exit.
     * <p>
     * By default, commands do not exit the application and return {@code false}.
     * Subclasses can override this method to return {@code true} if execution
     * should terminate the program (e.g., {@code ByeCommand}).
     *
     * @return {@code true} if the application should exit after this command,
     *         {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
