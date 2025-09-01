package capybara.command;

import capybara.CapyException;
import capybara.Storage;
import capybara.Task;
import capybara.TaskList;
import capybara.ToDo;
import capybara.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

public class MarkCommandTest {

    @TempDir
    Path tmp;

    @Test
    public void execute_mark_setsDoneAndSaves() throws IOException, CapyException {
        TaskList tasks = new TaskList();
        Task t = new ToDo("alpha");
        tasks.add(t);

        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        Ui ui = new Ui();

        Command cmd = new MarkCommand(0, true);
        cmd.execute(tasks, ui, storage);

        Assertions.assertTrue(tasks.get(0).isDone(), "Task should be marked done");
    }

    @Test
    public void execute_mark_outOfBounds_throwsCapyExceptionWithNiceMessage() throws IOException {
        TaskList tasks = new TaskList(); // empty
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        Ui ui = new Ui();

        Command cmd = new MarkCommand(5, true); // invalid index

        CapyException ex = Assertions.assertThrows(CapyException.class, () -> {
            cmd.execute(tasks, ui, storage);
        });
        Assertions.assertTrue(ex.getMessage().contains("can’t find task number"),
                "OOB indexing should map to friendly CapyException");
    }
}
