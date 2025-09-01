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

    /**
     * Main method for launching the application from the command line.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new Capybara("Data/taskStorage.txt").run();
    }
}
