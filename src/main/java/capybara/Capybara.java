package capybara;

import capybara.command.Command;

/**
 * Entry point of the Capybara application.
 * Coordinates initialization of UI, storage, and task list, and
 * runs the main command loop until the user exits.
 */
public class Capybara {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private boolean lastExit = false;

    /**
     * Constructs a new Capybara instance with the given storage file path.
     *
     * @param filePath Path to the storage file where tasks are saved.
     */
    public Capybara(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showError("capybara.Capybara squeaks… couldn’t load tasks from disk. Starting fresh.");
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    /**
     * Runs the Capybara application.
     * Reads user commands, executes them, and prints results until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (CapyException e) {
                ui.showError(e.getMessage());
            } catch (java.io.IOException io) {
                ui.showError("capybara.Capybara slipped… couldn’t save tasks to disk.");
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    public String getResponse(String input) {
        BufferingUi bui = new BufferingUi();
        try {
            capybara.command.Command c = Parser.parse(input);
            lastExit = c.isExit();            // ByeCommand returns true here
            c.execute(tasks, bui, storage);   // prints goodbye via ui.showGoodbye()
        } catch (CapyException e) {
            bui.showError(e.getMessage());
        } catch (java.io.IOException io) {
            bui.showError("Capybara slipped… couldn’t save tasks to disk.");
        }
        return bui.flush();
    }

    public String getWelcome() {
        BufferingUi u = new BufferingUi();
        u.showWelcome();          // uses Ui.showWelcome(), which calls println(...) -> buffered
        return u.flush();
    }

    public boolean lastWasExit() {
        return lastExit;
    }

    /**
     * Main method for launching the application from the command line.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new Capybara("Data/taskStorage.txt").run();
    }
}
