package capybara;

/**
 * Signals that a command requiring a description was given without one.
 *
 * Used for commands such as {@code todo}, {@code deadline}, or
 * {@code event} when the user omits the description text.
 */
public class EmptyDescriptionException extends CapyException {

    /**
     * Creates an exception indicating that a description
     * is required but was not provided.
     *
     * @param kind The command type (e.g., "todo", "deadline").
     */
    public EmptyDescriptionException(String kind) {
        super(String.format("Peep! %s needs a description. Try: %s sleep ...", kind, kind.toLowerCase()));
    }
}