import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Handles persistence of tasks to disk and loading them on startup.
 * File format (UTF-8, one task per line):
 *   T | 1 | read book
 *   D | 0 | return book | 2019-10-15
 *   E | 1 | project meeting | Mon 2pm | 4pm
 */
public class Storage {
    private final Path file; // e.g., data/mang.txt (relative, OS-independent)

    /** Creates a storage using the default file location {@code data/mang.txt}. */
    public Storage() {
        this(Paths.get("data", "mang.txt"));
    }

    /** Creates a storage that reads/writes to the given path. */
    public Storage(Path file) {
        this.file = file;
    }

    /**
     * Loads tasks from disk into {@code dest} and returns the number loaded.
     * Never throws; returns 0 on first run or I/O errors.
     */
    public int load(Task[] dest) {
        if (dest == null) {
            throw new IllegalArgumentException("Destination array cannot be null.");
        }
        try {
            ensureParentDir();
            if (!Files.exists(file)) {
                Files.createFile(file); // first run
                return 0;
            }
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            int count = 0;
            for (String raw : lines) {
                if (raw == null || raw.trim().isEmpty()) {
                    continue;
                }
                String[] parts = raw.split("\\s*\\|\\s*"); // tolerate spaces around '|'
                if (parts.length < 3) {
                    continue; // corrupted → skip
                }
                String type = parts[0];
                boolean done = "1".equals(parts[1]);
                String desc = parts[2];

                Task t;
                switch (type) {
                    case "T":
                        t = new Todo(desc);
                        break;
                    case "D":
                        if (parts.length < 4) {
                            continue; // corrupted → skip
                        }
                        try {
                            LocalDate by = LocalDate.parse(parts[3]);
                            t = new Deadline(desc, by);
                        } catch (DateTimeParseException e) {
                            continue; // malformed date → skip the line
                        }
                        break;
                    case "E":
                        String from = parts.length >= 4 ? parts[3] : "unspecified";
                        String to = parts.length >= 5 ? parts[4] : "unspecified";
                        t = new Event(desc, from, to);
                        break;
                    default:
                        continue; // unknown → skip
                }
                if (done) {
                    t.markDone();
                }
                if (count < dest.length) {
                    dest[count++] = t;
                } else {
                    break; // capacity reached
                }
            }
            return count;
        } catch (IOException e) {
            return 0; // non-fatal: start empty
        }
    }

    /** Saves the first {@code count} tasks to disk (overwrites the data file). */
    public void save(Task[] tasks, int count) {
        try {
            ensureParentDir();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                Task t = tasks[i];
                if (t == null) {
                    continue;
                }
                sb.append(serialize(t)).append(System.lineSeparator());
            }
            Files.write(
                    file,
                    sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
        } catch (IOException ignored) {
            // Best-effort persistence for Level-7/8.
        }
    }

    private void ensureParentDir() throws IOException {
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private String serialize(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.format("T | %s | %s", done, t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            // Store as ISO date for easy parsing
            return String.format("D | %s | %s | %s",
                    done, d.getDescription(), d.getBy().toString());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.format("E | %s | %s | %s | %s",
                    done, e.getDescription(), e.getFrom(), e.getTo());
        }
        return String.format("T | %s | %s", done, t.toString());
    }
}
