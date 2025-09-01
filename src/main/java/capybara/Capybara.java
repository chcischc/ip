package capybara;

import capybara.command.Command;

public class Capybara {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

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

    public static void main(String[] args) {
        new Capybara("Data/taskStorage.txt").run();
    }
}
