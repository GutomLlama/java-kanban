package service;

import model.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;
    private int idCounter;
    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistoryManager();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.idCounter = 0;
    }

    // Возвращение списка истории просмотров задач
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Генерация нового ID
    private int generateId() {
        return idCounter++;
    }

    // Добавление новой задачи
    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    // Обновление существующей задачи
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    // Добавление нового эпика
    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    // Обновление существующего эпика
    @Override
    public void updateEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        if (oldEpic != null) {
            oldEpic.setName(epic.getName());
            oldEpic.setDescription(epic.getDescription());
        }
    }

    // Добавление нового сабтаска
    @Override
    public void addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(generateId());
            subtasks.put(subtask.getId(), subtask);
            epic.getSubtasksIds().add(subtask.getId());
            updateEpicStatus(subtask.getEpicId());
        }
    }

    // Обновление существующего сабтаска
    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(subtask.getEpicId());
            }
        } else {
            System.out.println("Подзадача не найдена.");
        }
    }

    // Удаление существующей задачи
    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    // Удаление существующего эпика
    @Override
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtasksIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    // Удаление существующего сабтаска
    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtasksIds().remove(id);
                updateEpicStatus(subtask.getEpicId());
            }
        }
    }

    // Удаление всех задач
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    // Удаление всех эпиков
    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    // Удаление всех сабтасков
    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            epic.setStatus(Status.NEW);
        }
    }

    // Удаление всех задач, эпиков и сабтасков
    @Override
    public void removeAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    // Возвращение задачи по id
    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    // Возвращение эпика по id
    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    // Возвращение сабтаска по id
    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    // Возвращение списка всех задач
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Возвращение списка всех эпиков
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // Возвращение списка всех сабтасков
    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Возвращение списка всех сабтасков для эпика
    @Override
    public ArrayList<Subtask> getSubtasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }
        ArrayList<Subtask> subtasksForEpic = new ArrayList<>();
        for (int subtaskId : epic.getSubtasksIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                subtasksForEpic.add(subtask);
            }
        }
        return subtasksForEpic;
    }

    // Обновление статуса эпика на основе статусов его сабтасков
    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtasksForEpic = getSubtasksForEpic(epicId);

        if (subtasksForEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        int newCount = 0;
        int doneCount = 0;

        for (Subtask subtask : subtasksForEpic) {
            switch (subtask.getStatus()) {
                case IN_PROGRESS:
                    epic.setStatus(Status.IN_PROGRESS);
                    return;
                case NEW:
                    newCount++;
                    break;
                case DONE:
                    doneCount++;
                    break;
            }
        }

        if (doneCount == subtasksForEpic.size()) {
            epic.setStatus(Status.DONE);
        } else if (newCount == subtasksForEpic.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}