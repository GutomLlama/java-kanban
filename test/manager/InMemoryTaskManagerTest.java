package manager;

import model.*;
import service.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InMemoryTaskManagerTest {
    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();

    }

    @Test
    void shouldAddDifferentTypesOfTasks() {
        // InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id
        Task task = new Task("Test task", "Test task description", Status.NEW);
        Epic epic = new Epic("Test epic", "Test epic description");
        Subtask subtask = new Subtask("Test subtask", "Test subtask description", Status.NEW, epic.getId());

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        Task savedTask = taskManager.getTask(task.getId());
        Epic savedEpic = taskManager.getEpic(epic.getId());
        Subtask savedSubtask = taskManager.getSubtask(subtask.getId());

        assertNotNull(savedTask, "Задача не найдена");
        assertNotNull(savedEpic, "Эпик не найден");
        assertNotNull(savedSubtask, "Сабтаск не найден");
    }

    @Test
    void shouldNotConflictIds() {
        // Задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
        Task task1 = new Task("Test task 1", "Test task 1 description", Status.NEW);
        Task task2 = new Task("Test task 2", "Test task 2 description", Status.NEW);
        task2.setId(task1.getId());

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task savedTask1 = taskManager.getTask(task1.getId());
        Task savedTask2 = taskManager.getTask(task2.getId());

        assertNotNull(savedTask1, "Задача 1 не найдена");
        assertNotNull(savedTask2, "Задача 2 не найдена");
        assertNotEquals(savedTask1, savedTask2, "Задачи конфликтуют");
    }

    @Test
    void shouldNotModifyTask() {
        // Неизменность задачи (по всем полям) при добавлении задачи в менеджер
        Task task = new Task("Test task", "Test task description", Status.NEW);

        taskManager.addTask(task);

        Task savedTask = taskManager.getTask(task.getId());

        assertEquals(task, savedTask, "Задача была изменена");
    }

    @Test
    void tasksWithSameIdAreEqual() {
        // Экземпляры класса Task равны друг другу, если равен их id
        Task task1 = new Task("Test task", "Test task description", Status.NEW);
        Task task2 = new Task("Test task", "Test task description", Status.NEW);
        task2.setId(task1.getId());

        assertEquals(task1, task2, "Задачи с одинаковым id не равны");
    }

    @Test
    void taskSubclassesWithSameIdAreEqual() {
        // Наследники класса Task равны друг другу, если равен их id
        Epic epic1 = new Epic("Test epic", "Test epic description");
        Epic epic2 = new Epic("Test epic", "Test epic description");
        epic2.setId(epic1.getId());

        assertEquals(epic1, epic2, "Эпики с одинаковым id не равны");

        Subtask subtask1 = new Subtask("Test subtask", "Test subtask description", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Test subtask", "Test subtask description", Status.NEW, epic1.getId());
        subtask2.setId(subtask1.getId());

        assertEquals(subtask1, subtask2, "Сабтаски с одинаковым id не равны");
    }

    @Test
    void addTaskToHistory() {
        // Добавление в историю
        Task task = new Task("Test addTaskToHistory", "Test addTaskToHistory description", Status.NEW);
        taskManager.addTask(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая");
        assertEquals(1, history.size(), "История не пустая");
    }
}