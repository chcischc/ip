package capybara;

/**
 * Signals that the user entered a command that is not recognized.
 *
 * This exception is thrown when the parser encounters a command
 * word that does not match any known command type.
 */
public class UnknownCommandException extends CapyException {

    /**
     * Creates an exception indicating that the given command
     * is not recognized by the Capybara application.
     *
     * @param cmd The unknown command entered by the user.
     */
    UnknownCommandException(String cmd) {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}