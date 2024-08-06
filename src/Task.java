public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;

    // Конструктор задачи
    public Task(String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    // Возвращает название задачи
    public String getName() {
        return name;
    }

    // Устанавливает название задачи
    public void setName(String name) {
        this.name = name;
    }

    // Возвращает описание задачи
    public String getDescription() {
        return description;
    }

    // Устанавливает описание задачи
    public void setDescription(String description) {
        this.description = description;
    }

    // Возвращает уникальный идентификатор задачи
    public int getId() {
        return id;
    }

    // Устанавливает уникальный идентификатор задачи
    public void setId(int id) {
        this.id = id;
    }

    // Возвращает статус задачи
    public Status getStatus() {
        return status;
    }

    // Устанавливает статус задач
    public void setStatus(Status status) {
        this.status = status;
    }

    // Строковое представление задачи
    @Override
    public String toString() {
        return '\n' + "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", idCode=" + id +
                '}';
    }
}