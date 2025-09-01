package capybara;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StorageRoundTripTest {

    @TempDir
    Path tmpDir;

    @Test
    public void saveThenLoad_preservesTasks() throws IOException {
        Path file = tmpDir.resolve("tasks.txt");
        Storage storage = new Storage(file.toString());

        ArrayList<Task> original = new ArrayList<Task>();
        original.add(new ToDo("read book"));
        original.add(new Deadline("return book",
                LocalDate.of(2025, 6, 4).atStartOfDay()));               // date-only
        original.add(new Deadline("finish report",
                LocalDateTime.of(2025, 6, 4, 22, 0)));                   // with time
        original.add(new Event("meeting",
                LocalDateTime.of(2025, 6, 5, 14, 0),
                LocalDateTime.of(2025, 6, 5, 16, 0)));

        storage.save(original);

        ArrayList<Task> loaded = storage.load();

        Assertions.assertEquals(original.size(), loaded.size(), "Loaded size should match saved size");

        // spot-check types and descriptions
        Assertions.assertTrue(loaded.get(0) instanceof ToDo);
        Assertions.assertEquals("read book", loaded.get(0).getDescription());

        Assertions.assertTrue(loaded.get(1) instanceof Deadline);
        Assertions.assertEquals("return book", loaded.get(1).getDescription());

        Assertions.assertTrue(loaded.get(2) instanceof Deadline);
        Assertions.assertEquals("finish report", loaded.get(2).getDescription());

        Assertions.assertTrue(loaded.get(3) instanceof Event);
        Assertions.assertEquals("meeting", loaded.get(3).getDescription());
    }
}
