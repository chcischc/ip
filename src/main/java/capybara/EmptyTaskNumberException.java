package capybara;

public class EmptyTaskNumberException extends CapyException {
    public EmptyTaskNumberException(String kind) {
        super("OOPS!!! Capybara requires a task number to " + kind + " :-(");
    }
}