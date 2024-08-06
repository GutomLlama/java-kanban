public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Создаем задачи
        Task task1 = new Task("Задача 1", "Описание 1", 1, Status.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", 2, Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Создаем эпики и подзадачи
        Epic epic1 = new Epic("Эпик 1", "Описание 1", 3, Status.NEW);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", 4, Status.NEW, 3);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", 5, Status.NEW, 3);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание 2", 6, Status.NEW);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание 3", 7, Status.NEW, 6);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);

        // Печатаем списки
        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        System.out.println("Подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        // Обновляем статусы
        task1.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.DONE);

        taskManager.updateTask(task1);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        taskManager.updateEpicStatus(3);
        taskManager.updateEpicStatus(6);

        // Печатаем обновленные списки
        System.out.println("Обновленные задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Обновленные эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        System.out.println("Обновленные подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        // Удаляем задачу и эпик
        taskManager.removeTask(1);
        taskManager.removeEpic(3);

        // Печатаем списки после удаления
        System.out.println("Задачи после удаления:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики после удаления:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }
        System.out.println("Подзадачи после удаления:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }
}