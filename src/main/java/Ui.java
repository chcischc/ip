import java.util.Scanner;

public class Ui {
    private static final String LINE =
            "____________________________________________________________";
    private final Scanner sc = new Scanner(System.in);
    private static final String LOGO =
            "     _🍊_          \n"
                    + "   (´ ᴖ `)       \n"
                    + "  (-┬- -┬-)       \n"
                    + "  (_______)       \n"
                    + "~~~~~~~~~~~~~~\n";

    /** Reads one command line from user input. */
    public String readCommand() {
        if (sc.hasNextLine()) {
            return sc.nextLine().trim();
        } else {
            return "";
        }
    }

    public void println(String msg) {
        System.out.println(msg);
    }

    public void printMessage(String msg) {
        println(LINE);
        println(msg);
        println(LINE);
    }

    public void showWelcome() {
        println(LINE);
        println(LOGO);
        println(" Hello! I'm Capybara");
        println(" What can I do for you... Zzzzz");
        println(LINE);
    }

    public void showGoodbye() {
        println(" Bye. Hope to see you again soon!");
    }

    public void showLine() {
        println(LINE);
    }

    public void showError(String msg) {
        println("☹ OOPS!!! " + msg);
    }

    public void showAdded(Task t, int count) {
        println("Got it. I've added this task:");
        println("  " + t);
        println("Now you have " + count + " tasks in the list.");
    }

    public void showRemoved(Task t, int count) {
        println("Noted. I've removed this task:");
        println("  " + t);
        println("Now you have " + count + " tasks in the list.");
    }

    public void showList(String formatted) {
        println(formatted);
    }

    public void close() {
        sc.close();
    }
}