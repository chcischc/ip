package capybara;

import capybara.command.AddDeadlineCommand;
import capybara.command.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ParserTest {

    @Test
    public void parse_deadlineWithDateTime_returnsAddDeadlineCommand() throws CapyException {
        String input = "deadline finish report /by 2025-06-04 22:00";
        Command cmd = Parser.parse(input);

        Assertions.assertNotNull(cmd);
        Assertions.assertTrue(cmd instanceof AddDeadlineCommand,
                "Parser should produce AddDeadlineCommand for a valid deadline input");
    }

    @Test
    public void parse_deadlineMissingBy_throwsEmptyTimeException() {
        String input = "deadline finish report"; // no /by
        Assertions.assertThrows(EmptyTimeException.class, () -> {
            Parser.parse(input);
        });
    }

    @Test
    public void parse_eventMissingTo_throwsEmptyTimeException() {
        String input = "event picnic /from 2025-06-05 14:00"; // missing /to
        Assertions.assertThrows(EmptyTimeException.class, () -> {
            Parser.parse(input);
        });
    }
}
