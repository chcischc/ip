package capybara.command;

import capybara.CapyException;
import capybara.Deadline;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;
import java.time.LocalDateTime;

/**
 * Represents a command that adds a {@code Deadline} task to the task list.
 *
 * A Deadline requires both a textual description and a due date/time.
 * When executed, this command creates the task, stores it in the task list,
 * saves the updated list to storage, and notifies the user through the UI.
 */
public class AddDeadlineCommand extends Command {
    private final String desc;
    private final LocalDateTime by;

    /**
     * Creates an {@code AddDeadlineCommand} with the given description
     * and due date/time.
     *
     * @param desc Description of the deadline task.
     * @param by   Date and time by which the task must be completed.
     */
    public AddDeadlineCommand(String desc, LocalDateTime by) {
        this.desc = desc;
        this.by = by;
    }

    /**
     * Executes the command to add a {@code Deadline} task.
     *
     * Creates a new {@code Deadline} with the given description and due date,
     * appends it to the task list, saves the updated list to persistent
     * storage, and displays feedback to the user.
     *
     * @param tasks   The task list to which the deadline is added.
     * @param ui      The UI used to show messages to the user.
     * @param storage The storage system for saving tasks.
     * @throws CapyException   If there is an error in creating or handling the task.
     * @throws java.io.IOException If there is an error saving the updated task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        Task t = new Deadline(desc, by);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}