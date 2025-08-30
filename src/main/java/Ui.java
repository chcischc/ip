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

    public void println(String msg) {
        System.out.println(msg);
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
        println(LINE);
        sc.close();
    }


}