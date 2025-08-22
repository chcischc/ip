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

    private static ArrayList<String> list = new ArrayList<>();

    private static void printList() {
        int i = 1;
        for (String s : list) {
            System.out.println(i + ". " + s);
            i++;
        }
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
        System.out.println(" What can I do for ... Zzzzz");
        System.out.println(LINE);

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }
            printMessage(input);
            if (input.equalsIgnoreCase("list")) {
                printList();
                continue;
            }
            list.add(input);
        }
        sc.close();

    }
}
