package mang;

import java.util.Objects;

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

    /**
     * Returns a string icon representing the status of the task.
     */
    private String statusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return statusIcon() + " " + description;
    }

    /**
     * Checks if this task is equal to another object.
     * Two tasks are equal if they have the same description and completion status.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task other = (Task) obj;
        return isDone == other.isDone
                && Objects.equals(description, other.description);
    }

    /**
     * Returns a hash code value for this task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(description, isDone);
    }
}
