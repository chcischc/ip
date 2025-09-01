package capybara;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TaskListTest {

    @Test
    public void add_and_get_preservesOrderAndCount() {
        TaskList list = new TaskList();

        Task t1 = new ToDo("read book");
        Task t2 = new ToDo("write report");

        list.add(t1);
        list.add(t2);

        Assertions.assertEquals(2, list.size(), "Size should be 2 after adding two tasks");
        Assertions.assertSame(t1, list.get(0), "First task should be the first added");
        Assertions.assertSame(t2, list.get(1), "Second task should be the second added");
    }

    @Test
    public void remove_returnsRemovedTaskAndShrinksSize() {
        TaskList list = new TaskList();

        Task t1 = new ToDo("alpha");
        Task t2 = new ToDo("beta");
        list.add(t1);
        list.add(t2);

        Task removed = list.remove(0);

        Assertions.assertSame(t1, removed, "remove(0) should return the first task");
        Assertions.assertEquals(1, list.size(), "Size should shrink by one after remove");
        Assertions.assertSame(t2, list.get(0), "Remaining task should now be at index 0");
    }
}
