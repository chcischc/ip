package capybara.command;

import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.formatAll());
    }
}