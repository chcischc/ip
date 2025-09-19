package capybara;

/**
 * Captures all output produced by the {@code Ui} so that the GUI can display it.
 *
 * Instead of printing to the console, this class buffers messages into
 * an internal {@code StringBuilder}. The GUI can then retrieve the buffered
 * output and render it in the application interface.
 *
 * All {@code println} calls are redirected to the buffer, while input
 * reading is unsupported since the GUI does not rely on stdin.
 */
public class BufferingUi extends Ui {
    private final StringBuilder buf = new StringBuilder();

    /**
     * Appends the given message and a line separator to the internal buffer.
     *
     * @param msg The message to append.
     */
    @Override
    public void println(String msg) {
        buf.append(msg).append(System.lineSeparator());
    }

    /**
     * Throws an {@code UnsupportedOperationException} because the GUI
     * does not read commands from standard input.
     *
     * @return nothing, as this method always throws.
     * @throws UnsupportedOperationException always thrown when called.
     */
    @Override
    public String readCommand() {
        // GUI never reads from stdin
        throw new UnsupportedOperationException("BufferingUi does not support readCommand().");
    }

    /**
     * Performs no operation. Closing is unnecessary in the GUI context.
     */
    @Override
    public void close() {
        // no-op in GUI
    }

    /**
     * Returns the buffered output accumulated during one turn of execution.
     *
     * @return All messages appended to the buffer as a single string.
     */
    public String flush() {
        return buf.toString();
    }
}
