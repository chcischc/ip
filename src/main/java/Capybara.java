import java.util.ArrayList;
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

    private static final String LOGO =
            "     _🍊_          \n"
            + "   (´ ᴖ `)       \n"
            + "  (-┬- -┬-)       \n"
            + "  (_______)       \n";

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
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }
            if (input.equalsIgnoreCase("list")) {
                printList();
                continue;
            }
            if (input.startsWith("mark ")) {
                String numberPart = input.substring(5); // skip "mark "
                int x = Integer.parseInt(numberPart);
                Task cur = list.get(x-1);
                cur.markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + cur);
                System.out.println(LINE);
                continue;
            }
            if (input.startsWith("unmark ")) {
                String numberPart = input.substring(7); // skip "mark "
                int x = Integer.parseInt(numberPart);
                Task cur = list.get(x-1);
                cur.markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + cur);
                System.out.println(LINE);
                continue;
            }
            if (input.startsWith("todo ") || input.startsWith("deadline") ||
                    input.startsWith("event ")) {
                addTask(input);
            }
        }
        sc.close();

    }
}
