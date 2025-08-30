package mang;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    @Test
    public void saveAndLoad_validTodo_success() throws Exception {
        Path tempFile = Files.createTempFile("mang-test", ".txt");
        Storage storage = new Storage(tempFile);

        Task[] tasks = new Task[10];
        tasks[0] = new Todo("read book");
        tasks[1] = new Deadline("return book", LocalDate.parse("2019-10-15"));

        // Save 2 tasks
        storage.save(tasks, 2);

        // Load back
        Task[] loaded = new Task[10];
        int count = storage.load(loaded);

        assertEquals(2, count);
        assertEquals("[T][ ] read book", loaded[0].toString());
        assertEquals("[D][ ] return book (by: Oct 15 2019)", loaded[1].toString());
    }
}
