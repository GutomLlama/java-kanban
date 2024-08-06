import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    // Добавление новой задачи
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    // Обновление существующей задачи
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    // Добавление нового эпика
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // Обновление существующего эпика
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // Добавление нового сабтаска
    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    // Обновление существующего сабтаска
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    // Удаление существующей задачи
    public void removeTask(int id) {
        tasks.remove(id);
    }

    // Удаление существующего эпика
    public void removeEpic(int id) {
        epics.remove(id);
    }

    // Удаление существующего сабтаска
    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    // Удаление всех задач
    public void removeAllTasks() {
        tasks.clear();
    }

    // Удаление всех эпиков
    public void removeAllEpics() {
        epics.clear();
    }

    // Удаление всех сабтасков
    public void removeAllSubtasks() {
        subtasks.clear();
    }

    // Удаление всех задач, эпиков и сабтасков
    public void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    // Возвращение задачи по id
    public Task getTask(int id) {
        return tasks.get(id);
    }

    // Возвращение эпика по id
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    // Возвращение сабтаска по id
    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    // Возвращение списка всех задач
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Возвращение списка всех эпиков
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // Возвращение списка всех сабтасков
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Возвращение списка всех сабтасков для эпика
    public ArrayList<Subtask> getSubtasksForEpic(int epicId) {
        ArrayList<Subtask> subtasksForEpic = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksForEpic.add(subtask);
            }
        }
        return subtasksForEpic;
    }

    // Обновление статуса эпика на основе статусов его сабтасков
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtasksForEpic = getSubtasksForEpic(epicId);
        Status epicStatus = Status.NEW;
        for (Subtask subtask : subtasksForEpic) {
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                epicStatus = Status.IN_PROGRESS;
                break;
            } else if (subtask.getStatus() == Status.DONE) {
                epicStatus = Status.DONE;
            }
        }
        epic.setStatus(epicStatus);
        epics.put(epicId, epic);
    }
}