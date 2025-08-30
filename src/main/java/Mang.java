import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Entry point of the Mang chatbot.
 * Supports add/list/mark/unmark/delete and persists tasks on disk.
 */
public class Mang {
    /**
     * Runs the Mang CLI loop: reads commands, mutates task list, and persists changes.
     *
     * @param args Unused CLI arguments.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count;

        Storage storage = new Storage();
        count = storage.load(tasks); // load at startup

        // Greet
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Mang, your friendly neighborhood chatbot!");
        System.out.println(" What can I do for you?");
        System.out.println(" Type 'bye' whenever you want to end our chat.");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine().trim();

            try {
                if (input.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;

                } else if (input.equals("list")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < count; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                    System.out.println("____________________________________________________________");

                } else if (input.startsWith("mark ")) {
                    int idx = Integer.parseInt(input.substring(5).trim());
                    if (idx >= 1 && idx <= count) {
                        tasks[idx - 1].markDone();
                        storage.save(tasks, count);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[idx - 1]);
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new IllegalArgumentException("Task number " + idx + " does not exist.");
                    }

                } else if (input.startsWith("unmark ")) {
                    int idx = Integer.parseInt(input.substring(7).trim());
                    if (idx >= 1 && idx <= count) {
                        tasks[idx - 1].markUndone();
                        storage.save(tasks, count);
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[idx - 1]);
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new IllegalArgumentException("Task number " + idx + " does not exist.");
                    }

                } else if (input.startsWith("todo")) {
                    String desc = input.length() > 4 ? input.substring(4).trim() : "";
                    if (desc.isEmpty()) {
                        throw new IllegalArgumentException("The description of a todo cannot be empty.");
                    }
                    tasks[count] = new Todo(desc);
                    count++;
                    storage.save(tasks, count);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[count - 1]);
                    System.out.println(" Now you have " + count + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } else if (input.startsWith("deadline")) {
                    String rest = input.length() > 8 ? input.substring(8).trim() : "";
                    if (rest.isEmpty()) {
                        throw new IllegalArgumentException("The description of a deadline cannot be empty.");
                    }
                    String[] parts = rest.split("/by", 2);
                    String desc = parts[0].trim();
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new IllegalArgumentException(
                                "Deadline requires a date. Use yyyy-MM-dd, e.g., 2019-10-15");
                    }
                    String byStr = parts[1].trim();
                    try {
                        LocalDate by = LocalDate.parse(byStr); // expects yyyy-MM-dd
                        tasks[count] = new Deadline(desc, by);
                        count++;
                        storage.save(tasks, count);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[count - 1]);
                        System.out.println(" Now you have " + count + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } catch (DateTimeParseException pe) {
                        throw new IllegalArgumentException(
                                "Invalid date format. Please use yyyy-MM-dd, e.g., 2019-10-15");
                    }

                } else if (input.startsWith("event")) {
                    String rest = input.length() > 5 ? input.substring(5).trim() : "";
                    if (rest.isEmpty()) {
                        throw new IllegalArgumentException("The description of an event cannot be empty.");
                    }
                    String[] parts = rest.split("/from|/to");
                    if (parts.length >= 3) {
                        String desc = parts[0].trim();
                        String from = parts[1].trim();
                        String to = parts[2].trim();
                        tasks[count] = new Event(desc, from, to);
                    } else {
                        tasks[count] = new Event(rest, "unspecified", "unspecified");
                    }
                    count++;
                    storage.save(tasks, count);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[count - 1]);
                    System.out.println(" Now you have " + count + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } else if (input.startsWith("delete ")) {
                    try {
                        int idx = Integer.parseInt(input.substring(7).trim());
                        if (idx < 1 || idx > count) {
                            throw new IllegalArgumentException("Task number " + idx + " does not exist.");
                        }
                        Task removed = tasks[idx - 1];
                        for (int i = idx - 1; i < count - 1; i++) {
                            tasks[i] = tasks[i + 1];
                        }
                        tasks[count - 1] = null;
                        count--;
                        storage.save(tasks, count);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removed);
                        System.out.println(" Now you have " + count + " tasks in the list.");
                        System.out.println("____________________________________________________________");

                    } catch (NumberFormatException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Please provide a valid task number, e.g., 'delete 2'.");
                        System.out.println("____________________________________________________________");
                    }

                } else {
                    throw new UnsupportedOperationException("Unknown command: " + input);
                }

            } catch (NumberFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS! That does not look like a valid number.");
                System.out.println("____________________________________________________________");
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" " + e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println(" Unexpected error: " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }
}
