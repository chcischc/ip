package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;

public class MarkCommand extends Command {
    private final int index0;
    private final boolean mark;

    public MarkCommand(int index0, boolean mark) {
        this.index0 = index0;
        this.mark = mark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        try {
            Task t = tasks.get(index0);
            if (mark) {
                t.markAsDone();
                ui.println("Nice! I've marked this task as done:");
            } else {
                t.markAsNotDone();
                ui.println("OK, I've marked this task as not done yet:");
            }
            ui.println("  " + t);
            storage.save(tasks.asArrayList());
        } catch (IndexOutOfBoundsException oob) {
            int oneBased = index0 + 1;
            throw new CapyException("capybara.Capybara can’t find task number " + oneBased + " in the list.");
        }
    }
}
