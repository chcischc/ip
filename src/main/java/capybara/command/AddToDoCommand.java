package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.ToDo;
import capybara.Ui;

public class AddToDoCommand extends Command {
    private final String desc;

    public AddToDoCommand(String desc) {
        this.desc = desc;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        Task t = new ToDo(desc);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}