public enum Command {
    BYE("bye"),
    LIST("list"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public static Command fromInput(String input) throws UnknownCommandException {
        String cmd = input.trim().split("\\s+", 2)[0].toLowerCase();
        for (Command c : values()) {
            if (c.keyword.equals(cmd)) {
                return c;
            }
        }
        throw new UnknownCommandException(cmd);
    }
}
