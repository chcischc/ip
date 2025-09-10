package capybara;

public class EmptyTimeException extends CapyException {
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