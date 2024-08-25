package model;

import model.Task;
import static model.Status.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void testEqualityTasksById() {
        Task task1 = new Task("Task 1", "Description 1", NEW);
        Task task2 = new Task("Task 1", "Description 1", NEW);
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2);
    }

    @Test
    void testInequalityTasksById() {
        Task task1 = new Task("Task 1", "Description 1", NEW);
        Task task2 = new Task("Task 2", "Description 2", IN_PROGRESS);
        task1.setId(1);
        task2.setId(2);
        assertNotEquals(task1, task2);
    }
}