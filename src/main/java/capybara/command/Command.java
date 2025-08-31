package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;


public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException;

    public boolean isExit() {
        return false;
    }
}
