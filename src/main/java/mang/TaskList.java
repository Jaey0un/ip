package mang;

/** Holds and mutates the list of tasks. */
public class TaskList {
    private final Task[] tasks;
    private int count;

    /**
     * Creates an empty task list with a fixed capacity of 100 tasks.
     */
    public TaskList() {
        this.tasks = new Task[100];
        this.count = 0;
    }

    /**
     * Creates a TaskList that wraps an existing array of tasks and its count.
     *
     * @param initial The initial array of tasks.
     * @param count The number of tasks already in the array.
     */
    public TaskList(Task[] initial, int count) {
        this.tasks = initial;
        this.count = count;
    }

    /** Returns number of tasks. */
    public int size() {
        return count;
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param zeroBasedIndex The index starting from 0.
     * @return The task at the given position.
     */
    public Task get(int zeroBasedIndex) {
        return tasks[zeroBasedIndex];
    }

    /** Adds a task; returns the added task. */
    public Task add(Task t) {
        if (count >= tasks.length) {
            throw new IllegalStateException("Sorry, I can only store up to " + tasks.length + " items.");
        }
        tasks[count] = t;
        count++;
        return t;
    }

    /**
     * Marks the task at the given 1-based index as done.
     *
     * @param oneBasedIndex Index of the task to mark (starting from 1).
     * @return The task that was marked as done.
     * @throws IllegalArgumentException If the index is out of bounds.
     */
    public Task mark(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        validateIndex(oneBasedIndex);
        tasks[i].markDone();
        return tasks[i];
    }

    /**
     * Marks the task at the given 1-based index as not done.
     *
     * @param oneBasedIndex Index of the task to unmark (starting from 1).
     * @return The task that was marked as not done.
     * @throws IllegalArgumentException If the index is out of bounds.
     */
    public Task unmark(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        validateIndex(oneBasedIndex);
        tasks[i].markUndone();
        return tasks[i];
    }

    /**
     * Deletes the task at the given 1-based index.
     *
     * @param oneBasedIndex Index of the task to delete (starting from 1).
     * @return The removed task.
     * @throws IllegalArgumentException If the index is out of bounds.
     */
    public Task delete(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        validateIndex(oneBasedIndex);
        Task removed = tasks[i];
        for (int j = i; j < count - 1; j++) {
            tasks[j] = tasks[j + 1];
        }
        tasks[count - 1] = null;
        count--;
        return removed;
    }

    /**
     * Returns the underlying task array used for storage.
     *
     * @return The backing task array.
     */
    public Task[] backingArray() {
        return tasks;
    }

    private void validateIndex(int oneBasedIndex) {
        if (oneBasedIndex < 1 || oneBasedIndex > count) {
            throw new IllegalArgumentException("mang.Task number " + oneBasedIndex + " does not exist.");
        }
    }
}
