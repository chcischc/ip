package capybara;

/**
 * Signals that a command requiring a time component was given without one.
 *
 * This exception is thrown when the user enters a command such as
 * {@code deadline} or {@code event} but omits the required date or
 * time arguments. It generates a context-specific error message
 * guiding the user on the correct format to use.
 *
 * Examples:
 * - {@code deadline} without a {@code /by} date/time.
 * - {@code event} without both {@code /from} and {@code /to} times.
 *
 * For unsupported cases, a generic fallback message is provided.
 */
public class EmptyTimeException extends CapyException {

    /**
     * Creates an exception indicating a missing time component
     * for the specified command type.
     *
     * @param kind The command type (e.g., "deadline", "event").
     */
    public EmptyTimeException(String kind) {
        super(buildMessage(kind));
    }

    private static String buildMessage(String kind) {
        if ("deadline".equalsIgnoreCase(kind)) {
            return String.format(
                    "Sniff sniff… Your %s needs a time! Try: %s nap /by 2025-09-01",
                    kind, kind.toLowerCase()
            );
        } else if ("event".equalsIgnoreCase(kind)) {
            return "Splash! Your event needs both /from and /to… "
                    + "otherwise Capybara won’t know when to soak in the hot spring 🛁 "
                    + "(with an mandarin orange on its head 🍊).\n"
                    + "Try: event picnic /from 2025-09-01 16:00 /to 2025-09-01 18:00";
        } else {
            // safe fallback
            return "Peep! I need a time for that. Try using /by or /from … /to.";
        }
    }
}