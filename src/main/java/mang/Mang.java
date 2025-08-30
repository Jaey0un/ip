package mang;

/**
 * Entry point of the mang.Mang chatbot.
 * Supports add/list/mark/unmark/delete and persists tasks on disk.
 */
public class Mang {
    /**
     * Runs the mang.Mang CLI loop: reads commands, mutates task list, and persists changes.
     *
     * @param args Unused CLI arguments.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage();

        // Load tasks from disk
        Task[] loaded = new Task[100];
        int loadedCount = storage.load(loaded);
        TaskList tasks = new TaskList(loaded, loadedCount);

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            try {
                if (Parser.isBye(input)) {
                    ui.showBye();
                    break;

                } else if (Parser.isList(input)) {
                    ui.showList(tasks);

                } else if (Parser.startsWith(input, "mark")) {
                    int idx = Parser.parseIndexAfter(input, "mark");
                    Task t = tasks.mark(idx);
                    storage.save(tasks.backingArray(), tasks.size());
                    ui.showMarked(t);

                } else if (Parser.startsWith(input, "unmark")) {
                    int idx = Parser.parseIndexAfter(input, "unmark");
                    Task t = tasks.unmark(idx);
                    storage.save(tasks.backingArray(), tasks.size());
                    ui.showUnmarked(t);

                } else if (input.startsWith("todo")) {
                    Task t = Parser.parseTodo(input);
                    tasks.add(t);
                    storage.save(tasks.backingArray(), tasks.size());
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("deadline")) {
                    Task t = Parser.parseDeadline(input);
                    tasks.add(t);
                    storage.save(tasks.backingArray(), tasks.size());
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("event")) {
                    Task t = Parser.parseEvent(input);
                    tasks.add(t);
                    storage.save(tasks.backingArray(), tasks.size());
                    ui.showAdded(t, tasks.size());

                } else if (Parser.startsWith(input, "delete")) {
                    try {
                        int idx = Parser.parseIndexAfter(input, "delete");
                        Task removed = tasks.delete(idx);
                        storage.save(tasks.backingArray(), tasks.size());
                        ui.showRemoved(removed, tasks.size());
                    } catch (NumberFormatException e) {
                        ui.showError(" Please provide a valid task number, e.g., 'delete 2'.");
                    }

                } else {
                    throw new UnsupportedOperationException("Unknown command: " + input);
                }

            } catch (NumberFormatException e) {
                ui.showError(" OOPS! That does not look like a valid number.");
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError(" Unexpected error: " + e.getMessage());
            }
        }
    }
}
