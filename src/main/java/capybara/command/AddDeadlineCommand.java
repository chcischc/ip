package capybara.command;

import capybara.CapyException;
import capybara.Deadline;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;


import capybara.*;

import java.time.LocalDateTime;

public class AddDeadlineCommand extends Command {
    private final String desc;
    private final LocalDateTime by;

    public AddDeadlineCommand(String desc, LocalDateTime by) {
        this.desc = desc;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        Task t = new Deadline(desc, by);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}