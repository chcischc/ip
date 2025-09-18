package capybara;

/**
 * Signals that a command requiring a task number was given without one.
 *
 * Used for commands such as {@code delete}, {@code mark}, or
 * {@code unmark} when the user omits the task index.
 */
public class EmptyTaskNumberException extends CapyException {

    /**
     * Creates an exception indicating that a task number
     * is required but was not provided.
     *
     * @param kind The command type (e.g., "delete", "mark").
     */
    public EmptyTaskNumberException(String kind) {
        super("OOPS!!! Capybara requires a task number to " + kind + " :-(");
    }
}