package capybara.command;

import capybara.CapyException;
import capybara.Event;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;
import java.time.LocalDateTime;

public class AddEventCommand extends Command {
    private final String desc;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public AddEventCommand(String desc, LocalDateTime from, LocalDateTime to) {
        this.desc = desc;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        Task t = new Event(desc, from, to);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}