package mang;

/**
 * Represents the main class for the Mang chatbot.
 * It contains the core logic for processing user commands and managing tasks.
 */
public class Mang {

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructor for the Mang chatbot.
     * Initializes storage and loads tasks from the data file.
     */
    public Mang() {
        ui = new Ui();
        storage = new Storage();
        Task[] loaded = new Task[100];
        int loadedCount;
        try {
            loadedCount = storage.load(loaded);
        } catch (StorageException se) {
            // GUI에서는 에러를 문자열로 반환
            // 여기서는 간단하게 초기화
            loadedCount = 0;
        }
        tasks = new TaskList(loaded, loadedCount);
    }

    /**
     * Processes a user command and generates a response.
     * This method is called by the GUI controller to interact with the chatbot's core logic.
     *
     * @param input The command string entered by the user.
     * @return A string containing the chatbot's response.
     */
    public String getResponse(String input) {
        try {
            if (Parser.isBye(input)) {
                return "Bye. Hope to see you again soon!";

            } else if (Parser.isList(input)) {
                return formatList();

            } else if (Parser.startsWith(input, "mark")) {
                int idx = Parser.parseIndexAfter(input, "mark");
                Task t = tasks.mark(idx);
                persistGui();
                return "Nice! I've marked this task as done:\n  " + t;

            } else if (Parser.startsWith(input, "unmark")) {
                int idx = Parser.parseIndexAfter(input, "unmark");
                Task t = tasks.unmark(idx);
                persistGui();
                return "OK, I've marked this task as not done yet:\n  " + t;
                // ↓↓↓ duplicated add logic refactored into addTask() ↓↓↓
            } else if (input.startsWith("todo")) {
                return addTask(Parser.parseTodo(input));

            } else if (input.startsWith("deadline")) {
                return addTask(Parser.parseDeadline(input));

            } else if (input.startsWith("event")) {
                return addTask(Parser.parseEvent(input));

            } else if (Parser.startsWith(input, "delete")) {
                int idx = Parser.parseIndexAfter(input, "delete");
                Task removed = tasks.delete(idx);
                persistGui();
                return "Noted. I've removed this task:\n  " + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            } else if (Parser.isFind(input)) {
                String keyword = Parser.parseFindKeyword(input);
                Task[] found = tasks.find(keyword);
                return formatFound(found);

            } else if (Parser.isSort(input)) {
                String sortType = Parser.parseSortType(input);
                if ("deadline".equalsIgnoreCase(sortType)) {
                    tasks.sortByDeadline();
                    persistGui();
                    return "Tasks have been sorted by deadline.\n" + formatList();
                } else {
                    tasks.sortByDescription();
                    persistGui();
                    return "Tasks have been sorted by description.\n" + formatList();
                }
            } else {
                throw new UnsupportedOperationException("Unknown command: " + input);
            }
        } catch (Exception e) {
            return "OOPS! " + e.getMessage(); // 예외 메시지를 문자열로 반환
        }
    }

    /**
     * Helper method extracted from getResponse() with the help of ChatGPT
     * to reduce duplication when adding tasks.
     *
     * @param t the Task to add
     * @return the formatted string to show to the user
     */
    private String addTask(Task t) {
        Task added = tasks.add(t);
        persistGui();
        return formatAdded(t);
    }

    // Convert the methods of the Ui class to string return types
    private String formatAdded(Task t) {
        return "Got it. I've added this task:\n  " + t
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String formatList() {
        if (tasks.size() == 0) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    private String formatFound(Task[] results) {
        if (results.length == 0) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < results.length; i++) {
            sb.append(" ").append(i + 1).append(".").append(results[i]).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Save your work to a file.
     */
    private void persistGui() throws StorageException {
        storage.save(tasks.backingArray(), tasks.size());
    }

    /**
     * Runs the CLI loop: reads commands, mutates task list, and persists changes.
     *
     * @param args Unused CLI arguments.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage();

        // Load tasks from disk
        Task[] loaded = new Task[100];
        int loadedCount;
        try {
            loadedCount = storage.load(loaded);
        } catch (StorageException se) {
            ui.showError("Storage error: " + se.getMessage());
            return; // Fail fast on startup load errors.
        }

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
                    persist(storage, tasks, ui);
                    ui.showMarked(t);

                } else if (Parser.startsWith(input, "unmark")) {
                    int idx = Parser.parseIndexAfter(input, "unmark");
                    Task t = tasks.unmark(idx);
                    persist(storage, tasks, ui);
                    ui.showUnmarked(t);

                } else if (input.startsWith("todo")) {
                    Task t = tasks.add(Parser.parseTodo(input));
                    persist(storage, tasks, ui);
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("deadline")) {
                    // FIX: Previously CLI was wrongly using parseTodo() for deadlines.
                    //      Updated to use Parser.parseDeadline() for consistency with GUI.
                    //      Change done with the help of ChatGPT (AI-assisted coding).
                    Task t = tasks.add(Parser.parseDeadline(input));
                    persist(storage, tasks, ui);
                    ui.showAdded(t, tasks.size());

                } else if (input.startsWith("event")) {
                    // FIX: Previously CLI was wrongly using parseTodo() for events.
                    //      Updated to use Parser.parseEvent() for consistency with GUI.
                    //      Change done with the help of ChatGPT (AI-assisted coding).
                    Task t = tasks.add(Parser.parseEvent(input));
                    persist(storage, tasks, ui);
                    ui.showAdded(t, tasks.size());

                } else if (Parser.startsWith(input, "delete")) {
                    try {
                        int idx = Parser.parseIndexAfter(input, "delete");
                        Task removed = tasks.delete(idx);
                        persist(storage, tasks, ui);
                        ui.showRemoved(removed, tasks.size());
                    } catch (NumberFormatException e) {
                        ui.showError("Please provide a valid task number, e.g., 'delete 2'.");
                    }

                } else if (Parser.isFind(input)) {
                    String keyword = Parser.parseFindKeyword(input);
                    Task[] found = tasks.find(keyword);
                    ui.showFound(found);

                } else {
                    throw new UnsupportedOperationException("Unknown command: " + input);
                }

            } catch (NumberFormatException e) {
                ui.showError("OOPS! That does not look like a valid number.");
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
        }
    }

    /**
     * Persists the current tasks to storage; shows a user-friendly error if it fails.
     */
    private static void persist(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.backingArray(), tasks.size());
        } catch (StorageException se) {
            ui.showError("Storage error: " + se.getMessage());
        }
    }
}
