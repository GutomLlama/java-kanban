package service;

import model.*;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;
    private int idCounter;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.idCounter = 0;
    }

    // Генерация нового ID
    private int generateId() {
        return idCounter++;
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
        Epic oldEpic = epics.get(epic.getId());
        if (oldEpic != null) {
            oldEpic.setName(epic.getName());
            oldEpic.setDescription(epic.getDescription());
        }
    }

    // Добавление нового сабтаска
    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.getSubtasksIds().add(subtask.getId());
            updateEpicStatus(subtask.getEpicId());
        }
    }

    // Обновление существующего сабтаска
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    // Удаление существующей задачи
    public void removeTask(int id) {
        tasks.remove(id);
    }

    // Удаление существующего эпика
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtasksIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    // Удаление существующего сабтаска
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
    public void removeAllTasks() {
        tasks.clear();
    }

    // Удаление всех эпиков
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    // Удаление всех сабтасков
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            epic.setStatus(Status.NEW);
        }
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