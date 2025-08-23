class CapyException extends Exception {
    CapyException(String message) {
        super(message);
    }
}

class EmptyDescriptionException extends CapyException {
    EmptyDescriptionException(String kind) {
        super(String.format("Peep! %s needs a description. Try: %s sleep ...", kind, kind.toLowerCase()));
    }
}

class EmptyTimeException extends CapyException {
    EmptyTimeException(String kind) {
        super(buildMessage(kind));
    }

    private static String buildMessage(String kind) {
        if ("deadline".equalsIgnoreCase(kind)) {
            return String.format(
                    "Sniff sniff… Your %s needs a time! Try: %s nap /by tonight zzz...",
                    kind, kind.toLowerCase()
            );
        } else if ("event".equalsIgnoreCase(kind)) {
            return "Splash! Your event needs both /from and /to… "
                    + "otherwise Capybara won’t know when to soak in the hot spring 🛁 "
                    + "(with an mandarin orange on its head 🍊).\n"
                    + "Try: event picnic /from Mon 2pm /to 4pm";
        } else {
            // safe fallback
            return "Peep! I need a time for that. Try using /by or /from … /to.";
        }
    }
}

class UnknownCommandException extends CapyException {
    UnknownCommandException(String cmd) {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}


