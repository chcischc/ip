package capybara;

public class EmptyDescriptionException extends CapyException {
    EmptyDescriptionException(String kind) {
        super(String.format("Peep! %s needs a description. Try: %s sleep ...", kind, kind.toLowerCase()));
    }
}