package capybara;

public class EmptyDescriptionException extends CapyException {
    public EmptyDescriptionException(String kind) {
        super(String.format("Peep! %s needs a description. Try: %s sleep ...", kind, kind.toLowerCase()));
    }
}