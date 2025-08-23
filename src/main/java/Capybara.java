import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Capybara {
    private static final String LINE =
            "____________________________________________________________";

    private static final List<String> VALID_COMMANDS = List.of(
            "bye", "list", "todo", "deadline", "event", "mark", "unmark", "delete"
    );

    private static void printMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    private static ArrayList<Task> list = new ArrayList<>();

    private static void printList() {
        System.out.println("Here are the tasks in your list:");
        int i = 1;
        for (Task task : list) {
            System.out.println(i + ". " + task);
            i++;
        }
        System.out.println(LINE);
    }

    private static void addTask(String input) {
        Task task = null;
        if (input.startsWith("todo ")) {
            task = new ToDo(input.substring(5));
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split("/");
            String name = parts[0].trim();
            String time = parts[1].trim().substring(3);
            task = new Deadline(name, time);
        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split("/");
            String name = parts[0].trim();
            String startTime = parts[1].trim().substring(5);
            String endTime = parts[2].trim().substring(3);
            task = new Event(name, startTime, endTime);
        }
        list.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void handle(String input) throws CapyException {
        String[] cmds = input.trim().split("\\s+", 2);
        String cmd = cmds[0].toLowerCase();

        if (!VALID_COMMANDS.contains(cmd)) {
            throw new UnknownCommandException(cmd);
        } else if ((input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event"))
                && input.split(" ").length < 2) {
            String kind = input.split(" ")[0].trim();
            throw new EmptyDescriptionException(kind);
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.split("/by ");
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                // if no "/by " or time part is blank
                throw new EmptyTimeException("deadline");
            } else if ((input.substring(9).split("/by "))[0].isEmpty()) {
                throw new EmptyDescriptionException("deadline");
            }
        } else if (input.startsWith("event ")) {
            String[] parts1 = input.split("/from");
            String[] parts2 = input.split("/to");
            if (parts1.length < 2 || parts1[1].trim().isEmpty() ||
                parts2.length < 2 || parts2[1].trim().isEmpty()) {
                throw new EmptyTimeException("event");
            } else if ((input.substring(6).split("/from "))[0].isEmpty()) {
                throw new EmptyDescriptionException("event");
            }
        }
    }

    private static final String LOGO =
            "     _🍊_          \n"
            + "   (´ ᴖ `)       \n"
            + "  (-┬- -┬-)       \n"
            + "  (_______)       \n"
            + "~~~~~~~~~~~~~~\n";

    public static void main(String[] args) {

        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println(" Hello! I'm Capybara");
        System.out.println(" What can I do for you... Zzzzz");
        System.out.println(LINE);

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();
            System.out.println(LINE);
            try {
                handle(input);
            } catch (CapyException e) {
                System.out.println(e.getMessage());
                System.out.println(LINE);
                continue;
            }
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                printList();
                continue;
            } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
                boolean isMark = input.startsWith("mark ");
                int offset = isMark ? 5 : 7;  // "mark " = 5 chars, "unmark " = 7 chars
                String numberPart = input.substring(offset).trim();

                try {
                    int x = Integer.parseInt(numberPart);
                    Task cur = list.get(x - 1);

                    if (isMark) {
                        cur.markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                    } else {
                        cur.markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                    }

                    System.out.println("  " + cur);
                    System.out.println(LINE);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Capybara can’t find task number " + numberPart + " in the list.");
                    System.out.println(LINE);
                } catch (NumberFormatException e) {
                    System.out.println("Capybara tilts head… '" + numberPart + "' is not a valid task number.");
                    System.out.println(LINE);
                }
            } else if (input.startsWith("todo ") || input.startsWith("deadline ") ||
                    input.startsWith("event ")) {
                addTask(input);
            }
        }
        sc.close();

    }
}
