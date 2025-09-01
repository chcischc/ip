package capybara;

import capybara.command.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserIndexTest {

    @Test
    public void parse_markMissingIndex_throwsEmptyTaskNumberException() {
        String input = "mark";
        Assertions.assertThrows(EmptyTaskNumberException.class, () -> {
            Parser.parse(input);
        });
    }

    @Test
    public void parse_unmarkNonNumeric_throwsCapyExceptionWithNiceMessage() {
        String input = "unmark three";
        CapyException ex = Assertions.assertThrows(CapyException.class, () -> {
            Parser.parse(input);
        });
        // Optional: assert message text specified in Parser
        // "'three' is not a valid task number."
        Assertions.assertTrue(ex.getMessage().contains("not a valid task number"),
                "Parser should complain about non-numeric task number");
    }

    @Test
    public void parse_deleteZero_throwsCapyException() {
        String input = "delete 0";
        CapyException ex = Assertions.assertThrows(CapyException.class, () -> {
            Parser.parse(input);
        });
        Assertions.assertTrue(ex.getMessage().contains("not a valid task number"));
    }

    @Test
    public void parse_deleteValidIndex_returnsCommand() throws CapyException {
        String input = "delete 3";
        Command c = Parser.parse(input);
        Assertions.assertNotNull(c);
    }
}
