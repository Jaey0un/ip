package mang;

import java.util.Scanner;

/**
 * Deals with all user interactions (printing and reading).
 */
public class Ui {
    private static final String LINE =
            "____________________________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints the horizontal divider line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Mang, your friendly neighborhood chatbot!");
        System.out.println(" What can I do for you?");
        System.out.println(" Type 'bye' whenever you want to end our chat.");
        showLine();
    }

    /**
     * Shows the goodbye message.
     */
    public void showBye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Reads one command line from STDIN, trimmed.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Shows the current task list.
     */
    public void showList(TaskList list) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(" " + (i + 1) + "." + list.get(i));
        }
        showLine();
    }

    /**
     * Shows a task added message.
     */
    public void showAdded(Task t, int newCount) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + newCount + " tasks in the list.");
        showLine();
    }

    /**
     * Shows a task marked as done message.
     */
    public void showMarked(Task t) {
        showLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + t);
        showLine();
    }

    /**
     * Shows a task marked as not done message.
     */
    public void showUnmarked(Task t) {
        showLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + t);
        showLine();
    }

    /**
     * Shows a removed task message.
     */
    public void showRemoved(Task removed, int newCount) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removed);
        System.out.println(" Now you have " + newCount + " tasks in the list.");
        showLine();
    }

    /**
     * Shows a generic error.
     */
    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
    }

    /**
     * Shows the results of a {@code find} command.
     *
     * @param results Matching tasks to display (maybe empty).
     */
    public void showFound(Task[] results) {
        showLine();
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < results.length; i++) {
            System.out.println(" " + (i + 1) + "." + results[i]);
        }
        showLine();
    }
}
