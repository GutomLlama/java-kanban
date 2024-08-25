package service;

import model.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // Добавление новой задачи
    void addTask(Task task);

    // Обновление существующей задачи
    void updateTask(Task task);

    // Добавление нового эпика
    void addEpic(Epic epic);

    // Обновление существующего эпика
    void updateEpic(Epic epic);

    // Добавление нового сабтаска
    void addSubtask(Subtask subtask);

    // Обновление существующего сабтаска
    void updateSubtask(Subtask subtask);

    // Удаление существующей задачи
    void removeTask(int id);

    // Удаление существующего эпика
    void removeEpic(int id);

    // Удаление существующего сабтаска
    void removeSubtask(int id);

    // Удаление всех задач
    void removeAllTasks();

    // Удаление всех эпиков
    void removeAllEpics();

    // Удаление всех сабтасков
    void removeAllSubtasks();

    // Удаление всех задач, эпиков и сабтасков
    void removeAll();

    // Возвращение задачи по id
    Task getTask(int id);

    // Возвращение эпика по id
    Epic getEpic(int id);

    // Возвращение сабтаска по id
    Subtask getSubtask(int id);

    // Возвращение списка всех задач
    ArrayList<Task> getAllTasks();

    // Возвращение списка всех эпиков
    ArrayList<Epic> getAllEpics();

    // Возвращение списка всех сабтасков
    ArrayList<Subtask> getAllSubtasks();

    // Возвращение списка всех сабтасков для эпика
    ArrayList<Subtask> getSubtasksForEpic(int epicId);

    // Возвращение истории просмотров задач
    List<Task> getHistory();
}