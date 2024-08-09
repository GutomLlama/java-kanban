package model;

import java.util.ArrayList;

public class Epic extends Task {
    // Список идентификаторов сабтасков эпика
    private ArrayList<Integer> subtasksIds = new ArrayList<>();

    // Конструктор эпика
    public Epic(String name, String description, Integer id) {
        super(name, description, id, Status.NEW);
    }

    // Возвращает список идентификаторов сабтасков эпика
    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    // Добавляет идентификатор сабтаска в список эпика
    public void addSubtaskId(int subtaskId) {
        subtasksIds.add(subtaskId);
    }

    // Удаляет идентификатор сабтаска из списка эпика
    public void removeSubtaskId(Integer subtaskId) {
        subtasksIds.remove(subtaskId);
    }

    // Устанавливает список идентификаторов сабтасков эпика
    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    // Очищает список идентификаторов сабтасков эпика
    public void cleanSubtaskIds() {
        subtasksIds.clear();
    }

    // Строковое представление эпика
    @Override
    public String toString() {
        return '\n' + "model.Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", idCode=" + getId() +
                ", subtaskIds=" + subtasksIds +
                '}';
    }
}