package capybara;

/**
 * Represents the base class for all custom exceptions in Capybara.
 *
 * All application-specific exceptions extend this class to provide
 * clearer error reporting and user-friendly messages.
 */
public class CapyException extends Exception {

    /**
     * Creates a new Capybara-specific exception with the given message.
     *
     * @param message Error message to be shown.
     */
    public CapyException(String message) {
        super(message);
    }
}








