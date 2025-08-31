package mang;

/**
 * Represents a generic task with a description and a completion status
 * (done or not done).
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task with the given {@code description}.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is marked done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markUndone() {
        this.isDone = false;
    }

    private String statusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return statusIcon() + " " + description;
    }
}