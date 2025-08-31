package mang;

/** mang.Event task with a start and an end. */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an Event task with a description, a start time, and an end time.
     *
     * @param description The task description.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Returns the start time/string. */
    public String getFrom() {
        return from;
    }

    /** Returns the end time/string. */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}