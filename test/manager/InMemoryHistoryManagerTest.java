package manager;

import model.*;
import service.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldSavePreviousVersionOfTask() {
        // Задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных
        Task task = new Task("Test task", "Test task description", Status.NEW);
        Task updatedTask = new Task("Test task", "Test task description", Status.IN_PROGRESS);

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.addTask(task);
        taskManager.getTask(task.getId()); // add task to history
        taskManager.updateTask(updatedTask);

        List<Task> history = taskManager.getHistory();

        assertNotNull(history, "История не найдена");
        assertEquals(2, history.size(), "История не содержит двух версий задачи");
        assertEquals(task, history.get(0), "Предыдущая версия задачи не найдена");
        assertEquals(updatedTask, history.get(1), "Обновленная версия задачи не найдена");
    }

    @Test
    void shouldNotAddNullToHistory() {
        // История не должна содержать null значений
        assertThrows(NullPointerException.class, () -> historyManager.add(null));
    }

    @Test
    void shouldNotContainMoreThan10Values() {
        // История не должна содержать более 10 значений
        for (int i = 0; i < 11; i++) {
            Task task = new Task("Task " + i, "Task description " + i, Status.NEW);
            historyManager.add(task);
        }
        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "История содержит более 10 значений");
    }
}