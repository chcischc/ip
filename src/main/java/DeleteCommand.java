/*
package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.TaskList;
import capybara.Ui;
*/

public class DeleteCommand extends Command {
    private final int index0;

    public DeleteCommand(int index0) {
        this.index0 = index0;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        try {
            Task removed = tasks.remove(index0);
            ui.showRemoved(removed, tasks.size());
            storage.save(tasks.asArrayList());
        } catch (IndexOutOfBoundsException oob) {
            int oneBased = index0 + 1;
            throw new CapyException("Capybara can’t find task number " + oneBased + " in the list.");
        }
    }
}
