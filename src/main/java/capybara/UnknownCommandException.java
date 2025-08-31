package capybara;

public class UnknownCommandException extends CapyException {
    UnknownCommandException(String cmd) {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}