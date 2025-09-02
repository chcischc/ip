package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

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