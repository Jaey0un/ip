import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Parses raw user input into actionable parts or task objects. */
public final class Parser {
    private Parser() {}

    public static boolean isBye(String input) {
        return "bye".equals(input);
    }

    public static boolean isList(String input) {
        return "list".equals(input);
    }

    public static boolean startsWith(String input, String prefix) {
        return input.startsWith(prefix + " ");
    }

    public static int parseIndexAfter(String input, String prefix) {
        try {
            return Integer.parseInt(input.substring(prefix.length() + 1).trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("OOPS! That does not look like a valid number.");
        }
    }

    public static Todo parseTodo(String input) {
        String desc = input.length() > 4 ? input.substring(4).trim() : "";
        if (desc.isEmpty()) {
            throw new IllegalArgumentException("The description of a todo cannot be empty.");
        }
        return new Todo(desc);
    }

    public static Deadline parseDeadline(String input) {
        String rest = input.length() > 8 ? input.substring(8).trim() : "";
        if (rest.isEmpty()) {
            throw new IllegalArgumentException("The description of a deadline cannot be empty.");
        }
        String[] parts = rest.split("/by", 2);
        String desc = parts[0].trim();
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Deadline requires a date. Use yyyy-MM-dd, e.g., 2019-10-15");
        }
        String byStr = parts[1].trim();
        try {
            LocalDate by = LocalDate.parse(byStr); // yyyy-MM-dd
            return new Deadline(desc, by);
        } catch (DateTimeParseException pe) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd, e.g., 2019-10-15");
        }
    }

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
