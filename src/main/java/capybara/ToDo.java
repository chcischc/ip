package capybara;

/**
 * Represents a basic to-do task in the Capybara application.
 *
 * A ToDo stores only a description and a completion status.
 * It extends {@code Task} without adding new fields, but
 * customizes the string representation for display and file storage.
 */
public class ToDo extends Task {

    /**
     * Creates a ToDo task with the given description.
     *
     * @param name Description of the to-do task.
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Returns a string representation of the to-do suitable for saving
     * to the storage file. The format includes the to-do marker "T",
     * completion status, and description.
     *
     * @return Encoded string for storage.
     */
    @Override
    public String toFileString() {
        return "T" + super.toFileString();
    }

    /**
     * Returns a string representation of the to-do for display to the user.
     * The format includes the to-do marker "[T]" and the description.
     *
     * @return Human-readable string of the to-do.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}