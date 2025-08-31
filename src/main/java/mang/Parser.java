package mang;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Parses raw user input into actionable parts or task objects. */
public final class Parser {
    private Parser() {}

    /**
     * Returns true if the input is the exit command ("bye").
     *
     * @param input The raw user input.
     * @return True if the input is "bye".
     */
    public static boolean isBye(String input) {
        return "bye".equals(input);
    }

    /**
     * Returns true if the input is the list command ("list").
     *
     * @param input The raw user input.
     * @return True if the input is "list".
     */
    public static boolean isList(String input) {
        return "list".equals(input);
    }

    /**
     * Returns true if the input starts with the given prefix followed by a space.
     *
     * @param input The raw user input.
     * @param prefix The expected prefix (e.g., "mark").
     * @return True if input starts with the prefix.
     */
    public static boolean startsWith(String input, String prefix) {
        return input.startsWith(prefix + " ");
    }

    /**
     * Parses an index from a command string after a given prefix.
     *
     * @param input The full user command.
     * @param prefix The command keyword (e.g., "mark", "delete").
     * @return The parsed integer index (1-based).
     * @throws NumberFormatException If the index is missing or not a valid number.
     */
    public static int parseIndexAfter(String input, String prefix) {
        try {
            return Integer.parseInt(input.substring(prefix.length() + 1).trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("OOPS! That does not look like a valid number.");
        }
    }

    /**
     * Parses a "todo" command and returns the corresponding Todo task.
     *
     * @param input The full user command (must start with "todo").
     * @return A new Todo task.
     * @throws IllegalArgumentException If the description is empty.
     */
    public static Todo parseTodo(String input) {
        String desc = input.length() > 4 ? input.substring(4).trim() : "";
        if (desc.isEmpty()) {
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }
        return new Todo(desc);
    }

    /**
     * Parses a "deadline" command and returns the corresponding Deadline task.
     *
     * @param input The full user command (must start with "deadline").
     * @return A new Deadline task with description and date.
     * @throws IllegalArgumentException If the description or date is missing/invalid.
     */
    public static Deadline parseDeadline(String input) {
        String rest = input.length() > 8 ? input.substring(8).trim() : "";
        if (rest.isEmpty()) {
            throw new IllegalArgumentException("The description of a deadline cannot be empty.");
        }
        String[] parts = rest.split("/by", 2);
        String desc = parts[0].trim();
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("mang.Deadline requires a date. Use yyyy-MM-dd, e.g., 2019-10-15");
        }
        String byStr = parts[1].trim();
        try {
            LocalDate by = LocalDate.parse(byStr); // yyyy-MM-dd
            return new Deadline(desc, by);
        } catch (DateTimeParseException pe) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd, e.g., 2019-10-15");
        }
    }

    /**
     * Parses an "event" command and returns the corresponding Event task.
     *
     * @param input The full user command (must start with "event").
     * @return A new Event task with description, start, and end.
     * @throws IllegalArgumentException If the description is empty.
     */
    public static Event parseEvent(String input) {
        String rest = input.length() > 5 ? input.substring(5).trim() : "";
        if (rest.isEmpty()) {
            throw new IllegalArgumentException("The description of an event cannot be empty.");
        }
        String[] parts = rest.split("/from|/to");
        if (parts.length >= 3) {
            String desc = parts[0].trim();
            String from = parts[1].trim();
            String to = parts[2].trim();
            return new Event(desc, from, to);
        }
        return new Event(rest, "unspecified", "unspecified");
    }
}
