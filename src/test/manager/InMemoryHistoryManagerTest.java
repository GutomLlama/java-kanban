package test.manager;

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

        historyManager.add(task);
        historyManager.add(updatedTask);

        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не найдена");
        assertEquals(2, history.size(), "История не содержит двух версий задачи");
        assertEquals(task, history.get(0), "Предыдущая версия задачи не найдена");
        assertEquals(updatedTask, history.get(1), "Обновленная версия задачи не найдена");
    }
}