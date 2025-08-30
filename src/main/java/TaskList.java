/** Holds and mutates the list of tasks. */
public class TaskList {
    private final Task[] tasks;
    private int count;

    /** Creates an empty list with capacity 100. */
    public TaskList() {
        this.tasks = new Task[100];
        this.count = 0;
    }

    /** Wraps an existing array and size (used after loading from storage). */
    public TaskList(Task[] initial, int count) {
        this.tasks = initial;
        this.count = count;
    }

    /** Returns number of tasks. */
    public int size() {
        return count;
    }

    /** Returns the task at zero-based index. */
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

    /** Marks task (1-based index) as done and returns it. */
    public Task mark(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        validateIndex(oneBasedIndex);
        tasks[i].markDone();
        return tasks[i];
    }

    /** Marks task (1-based index) as not done and returns it. */
    public Task unmark(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        validateIndex(oneBasedIndex);
        tasks[i].markUndone();
        return tasks[i];
    }

    /** Deletes task (1-based index) and returns it. */
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

    /** Exposes the backing array for persistence (Storage.save). */
    public Task[] backingArray() {
        return tasks;
    }

    private void validateIndex(int oneBasedIndex) {
        if (oneBasedIndex < 1 || oneBasedIndex > count) {
            throw new IllegalArgumentException("Task number " + oneBasedIndex + " does not exist.");
        }
    }
}
