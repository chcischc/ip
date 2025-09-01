package capybara;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskFormatTest {

    @Test
    public void deadline_dateOnly_toFileString_hasDateOnly() {
        LocalDateTime by = LocalDate.of(2025, 6, 4).atStartOfDay();
        Task d = new Deadline("return book", by);
        String file = d.toFileString();
        // expected shape: D | 0 | return book | 2025-06-04
        Assertions.assertTrue(file.matches("^D \\| [01] \\| return book \\| 2025-06-04$"),
                "Deadline at midnight should save as yyyy-MM-dd only");
    }

    @Test
    public void deadline_dateTime_toFileString_hasDateTime() {
        LocalDateTime by = LocalDateTime.of(2025, 6, 4, 22, 0);
        Task d = new Deadline("finish report", by);
        String file = d.toFileString();
        // expected shape: D | 0 | finish report | 2025-06-04 22:00
        Assertions.assertTrue(file.matches("^D \\| [01] \\| finish report \\| 2025-06-04 22:00$"),
                "Deadline with time should save as yyyy-MM-dd HH:mm");
    }

    @Test
    public void event_fromTo_dateTime_toFileString_hasTwoStamps() {
        LocalDateTime from = LocalDateTime.of(2025, 6, 5, 14, 0);
        LocalDateTime to   = LocalDateTime.of(2025, 6, 5, 16, 0);
        Task e = new Event("meeting", from, to);
        String file = e.toFileString();
        // expected: E | 0 | meeting | 2025-06-05 14:00 | 2025-06-05 16:00
        Assertions.assertTrue(file.matches("^E \\| [01] \\| meeting \\| 2025-06-05 14:00 \\| 2025-06-05 16:00$"),
                "Event should save from/to with date-times");
    }
}
