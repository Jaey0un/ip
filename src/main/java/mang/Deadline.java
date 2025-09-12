package mang;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * mang.Deadline task.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a Deadline task with a description and a due date.
     *
     * @param description The task description.
     * @param by          The due date of the deadline.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date.
     */
    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        // Print in format: "Oct 15 2019"
        String formattedDate = by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }
}
