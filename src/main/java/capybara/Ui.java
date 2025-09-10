package capybara;

import java.util.Scanner;

/**
 * Handles user interaction for Capybara.
 * Provides methods to read commands from the user and print messages to the console.
 */
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

    /**
     * Reads the next command entered by the user.
     *
     * @return User’s raw command string.
     */
    public String readCommand() {
        if (sc.hasNextLine()) {
            return sc.nextLine().trim();
        } else {
            return "";
        }
    }

    /**
     * Shows the message.
     */
    public void println(String msg) {
        System.out.println(msg);
    }

    /**
     * Shows the message between two horizontal divider lines.
     */
    public void printMessage(String msg) {
        println(LINE);
        println(msg);
        println(LINE);
    }

    /**
     * Shows the welcome message and logo.
     */
    public void showWelcome() {
        println(LINE);
        println(LOGO);
        println(" Hello! I'm capybara.Capybara");
        println(" What can I do for you... Zzzzz");
        println(LINE);
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        println(" Bye. Hope to see you again soon!");
    }

    /**
     * Shows a horizontal divider line.
     */
    public void showLine() {
        println(LINE);
    }

    /**
     * Shows the given error message.
     *
     * @param msg Error message to display.
     */
    public void showError(String msg) {
        println(msg);
    }

    /**
     * Shows the added task and tasks information.
     */
    public void showAdded(Task t, int count) {
        println("Got it. I've added this task:");
        println("  " + t);
        println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Shows the removed task and tasks information.
     */
    public void showRemoved(Task t, int count) {
        println("Noted. I've removed this task:");
        println("  " + t);
        println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Shows the task list.
     */
    public void showList(String formatted) {
        println(formatted);
    }

    /**
     * Closes the scanner resource used to read user input.
     */
    public void close() {
        sc.close();
    }
}