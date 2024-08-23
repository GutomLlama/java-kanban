package service;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
    }

    public void addTaskToHistory(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
