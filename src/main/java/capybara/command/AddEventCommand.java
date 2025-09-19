package capybara.command;

import capybara.CapyException;
import capybara.Event;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.Ui;
import java.time.LocalDateTime;

/**
 * Represents a command that adds an {@code Event} task to the task list.
 *
 * An Event requires a description, a start date/time, and an end date/time.
 * When executed, this command creates the event, adds it to the task list,
 * saves the updated list to storage, and notifies the user through the UI.
 */
public class AddEventCommand extends Command {
    private final String desc;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an {@code AddEventCommand} with the given description,
     * start time, and end time.
     *
     * @param desc Description of the event.
     * @param from Date and time when the event starts.
     * @param to   Date and time when the event ends.
     */
    public AddEventCommand(String desc, LocalDateTime from, LocalDateTime to) {
        this.desc = desc;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the command to add an {@code Event} task.
     *
     * Creates a new {@code Event} with the given description, start, and end
     * times, appends it to the task list, saves the updated list to persistent
     * storage, and displays feedback to the user.
     *
     * @param tasks   The task list to which the event is added.
     * @param ui      The UI used to show messages to the user.
     * @param storage The storage system for saving tasks.
     * @throws CapyException       If there is an error in creating or handling the task.
     * @throws java.io.IOException If there is an error saving the updated task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws CapyException, java.io.IOException {
        Task t = new Event(desc, from, to);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}