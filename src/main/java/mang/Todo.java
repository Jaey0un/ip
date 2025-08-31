package mang;

/** mang.Todo task (no date/time). */
public class Todo extends Task {
    /**
     * Creates a Todo task with the given description.
     *
     * @param description The task description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}