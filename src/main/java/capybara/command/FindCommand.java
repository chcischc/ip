package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;

/**
 * Represents a command that searches for tasks containing a given keyword.
 * <p>
 * The command loads the task list from storage, filters it by the keyword,
 * and displays the matching tasks to the user. If no matching tasks are found,
 * a {@link CapyException} is thrown.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a {@code FindCommand} with the specified search keyword.
     *
     * @param keyword the keyword to search for within task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command.
     * <p>
     * Loads the task list from storage, filters it by the keyword, and displays
     * the formatted results to the user interface. If no matching tasks are found,
     * an exception is thrown.
     *
     * @param tasks    the current task list (not used directly, reloaded from storage)
     * @param ui       the UI handler used to display results
     * @param storage  the storage used to load tasks
     * @throws CapyException if no tasks match the given keyword or a loading error occurs
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CapyException {
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (Exception e) {
            loaded = new TaskList();
        }
        TaskList filtered = loaded.getFilteredTaskList(keyword);
        if (filtered.isEmpty()) {
            throw new CapyException("Capybara can't find any matching tasks for " + keyword);
        }
        String formatted = filtered.formatAll();
        ui.showList(formatted);
    }
}