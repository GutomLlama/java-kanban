package model;

public class Subtask extends Task {
    // Идентификатор эпика, к которому принадлежит сабтаск
    private int epicId;

    // Конструктор сабтаска
    public Subtask(String name, String description, Integer id, Status status, int epicId) {
        super();
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status) {
    }

    public Subtask(String testSubtask, String testSubtaskDescription, Status status, int id) {
    }

    // Возвращает идентификатор эпика, к которому принадлежит сабтаск
    public int getEpicId() {
        return epicId;
    }

    // Устанавливает идентификатор эпика, к которому принадлежит сабтаск
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    // Возвращает строковое представление сабтаска
    @Override
    public String toString() {
        return '\n' + "model.Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", idCode=" + getId() +
                ", epicId=" + epicId +
                '}';
    }
}