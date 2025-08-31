package capybara;

public class EmptyTaskNumberException extends CapyException {
    EmptyTaskNumberException(String kind) {
        super("OOPS!!! capybara.Capybara requires a task number to " + kind + " :-(");
    }
}