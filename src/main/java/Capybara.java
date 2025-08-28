import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Capybara {
    private static final String LINE =
            "____________________________________________________________";


    private static void printMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    private static ArrayList<Task> list = new ArrayList<>();
    private static Storage storage = new Storage("./Data/taskStorage.txt");

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
        java.time.format.DateTimeFormatter DATE_FMT =
                java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;                // yyyy-MM-dd
        java.time.format.DateTimeFormatter DATETIME_FMT =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // yyyy-MM-dd 18:00

        Task task = null;
        if (input.startsWith("todo ")) {
            task = new ToDo(input.substring(5));
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split("/");
            String name = parts[0].trim();
            String raw = parts[1].trim().substring(3);
            LocalDateTime by;
            try {
                // Try full date-time first
                by = LocalDateTime.parse(raw, DATETIME_FMT);
            } catch (java.time.format.DateTimeParseException e1) {
                try {
                    // Fallback: date-only → midnight
                    by = LocalDate.parse(raw, DATE_FMT).atStartOfDay();
                } catch (java.time.format.DateTimeParseException e2) {
                    System.out.println("Capybara can’t read that date 🐹🍊. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                    System.out.println(LINE);
                    return;
                }
            }
            task = new Deadline(name, by);
        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split("/");
            String name = parts[0].trim();
            String fromRaw = parts[1].trim().substring(5);
            String toRaw = parts[2].trim().substring(3);
            LocalDateTime from, to;
            try {
                from = LocalDateTime.parse(fromRaw, DATETIME_FMT);
            } catch (java.time.format.DateTimeParseException e1) {
                try {
                    from = LocalDate.parse(fromRaw, DATE_FMT).atStartOfDay();
                } catch (java.time.format.DateTimeParseException e2) {
                    System.out.println("Capybara can’t read the /from time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                    System.out.println(LINE);
                    return;
                }
            }

            try {
                to = LocalDateTime.parse(toRaw, DATETIME_FMT);
            } catch (java.time.format.DateTimeParseException e1) {
                try {
                    to = LocalDate.parse(toRaw, DATE_FMT).atStartOfDay();
                } catch (java.time.format.DateTimeParseException e2) {
                    System.out.println("Capybara can’t read the /to time. Try 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'.");
                    System.out.println(LINE);
                    return;
                }
            }
            task = new Event(name, from, to);
        }
        list.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void handleMarkUnmark(String input) {
        if (!(input.startsWith("mark ") || input.startsWith("unmark "))) {
            return;
        }

        boolean isMark = input.startsWith("mark ");
        int offset = isMark ? 5 : 7;  // "mark " = 5, "unmark " = 7
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
            storage.save(list);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Capybara can’t find task number " + numberPart + " in the list.");
            System.out.println(LINE);
        } catch (NumberFormatException e) {
            System.out.println("Capybara tilts head… '" + numberPart + "' is not a valid task number.");
            System.out.println(LINE);
        } catch (IOException e) {
            System.out.println("Capybara slipped… couldn’t save tasks to disk.");
            System.out.println(LINE);
        }
    }

    private static void handleDelete(String input) {
        if (!input.startsWith("delete ")) {
            return; // not our command
        }

        String numberPart = input.substring(7).trim();

        try {
            int x = Integer.parseInt(numberPart);
            Task cur = list.get(x - 1);
            list.remove(x - 1);

            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + cur);
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            System.out.println(LINE);
            storage.save(list);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Capybara can’t find task number " + numberPart + " in the list.");
            System.out.println(LINE);
        } catch (NumberFormatException e) {
            System.out.println("Capybara tilts head… '" + numberPart + "' is not a valid task number.");
            System.out.println(LINE);
        } catch (IOException e) {
            System.out.println("Capybara slipped… couldn’t save tasks to disk.");
            System.out.println(LINE);
        }
    }

    private static void handle(String input) throws CapyException {
        Command cmd = Command.fromInput(input);

        switch (cmd) {
            case TODO:
            case DEADLINE:
            case EVENT:
                if (input.split(" ").length < 2) {
                    throw new EmptyDescriptionException(cmd.getKeyword());
                }
                if (cmd == Command.DEADLINE) {
                    String[] parts = input.split("/by ");
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new EmptyTimeException("deadline");
                    }
                } else if (cmd == Command.EVENT) {
                    String[] parts1 = input.split("/from");
                    String[] parts2 = input.split("/to");
                    if (parts1.length < 2 || parts1[1].trim().isEmpty() ||
                            parts2.length < 2 || parts2[1].trim().isEmpty()) {
                        throw new EmptyTimeException("event");
                    }
                }
                break;

            case MARK:
            case UNMARK:
            case DELETE:
                if (input.split(" ").length < 2) {
                    throw new EmptyTaskNumberException(cmd.getKeyword());
                }
                break;

            default:
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

        try {
            list = storage.load();
        } catch (IOException e) {
            System.out.println("Capybara squeaks… couldn’t load tasks from disk. Starting fresh.");
            System.out.println(LINE);
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();
            System.out.println(LINE);
            try {
                Command cmd = Command.fromInput(input);
                handle(input);

                switch (cmd) {
                    case BYE:
                        System.out.println(" Bye. Hope to see you again soon!");
                        System.out.println(LINE);
                        sc.close();
                        return;

                    case LIST:
                        printList();
                        break;

                    case MARK:
                    case UNMARK:
                        handleMarkUnmark(input);
                        break;

                    case TODO:
                    case DEADLINE:
                    case EVENT:
                        addTask(input);
                        try {
                            storage.save(list);
                        } catch (IOException e) {
                            System.out.println("Capybara slipped… couldn’t save tasks to disk.");
                            System.out.println(LINE);
                        }
                        break;

                    case DELETE:
                        handleDelete(input);
                        break;
                }
            } catch (CapyException e) {
                System.out.println(e.getMessage());
                System.out.println(LINE);
            }
        }
    }
}
