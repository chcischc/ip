package capybara;

/**
 * Captures all output that Ui would print, so GUI can display it.
 * Works because all Ui methods route through println(String).
 */
public class BufferingUi extends Ui {
    private final StringBuilder buf = new StringBuilder();

    @Override
    public void println(String msg) {
        buf.append(msg).append(System.lineSeparator());
    }

    @Override
    public String readCommand() {
        // GUI never reads from stdin
        throw new UnsupportedOperationException("BufferingUi does not support readCommand().");
    }

    @Override
    public void close() {
        // no-op in GUI
    }

    /** Returns everything printed during this one turn. */
    public String flush() {
        return buf.toString();
    }
}
