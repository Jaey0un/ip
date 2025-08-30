package mang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void toString_validTodo_correctFormat() {
        Todo todo = new Todo("read book");
        // Expect task to be shown with "[T]" type and unchecked "[ ]"
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void markDone_toString_correctlyUpdatesStatus() {
        Todo todo = new Todo("read book");
        todo.markDone();
        // After marking done, should show "[X]"
        assertEquals("[T][X] read book", todo.toString());
    }
}