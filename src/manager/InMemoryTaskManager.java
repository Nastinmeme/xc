package manager;

import model.Status;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    protected final HashMap<Integer, Task> allTasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> allSubtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> allEpics = new HashMap<>();
    protected int genId = 0;
    protected final HistoryManager history = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {

        return history.getHistory();
    }

    @Override
    public int getGenId() {
        return genId;
    }

    @Override
    public void setGenId(int genId) {
        this.genId = genId;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(allTasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Integer id : allTasks.keySet()) {
            history.remove(id);
        }
        allTasks.clear();
    }

    @Override
    public Task getTask(int id) {
        Task task = allTasks.get(id);
        history.add(task);
        return task;
    }

    @Override
    public Task createTask(Task task) {
        task.setId(++genId);
        allTasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (!allTasks.containsKey(task.getId())) {
            return;
        }
        allTasks.put(task.getId(), task);
    }

    @Override
    public void removeTask(int id) {
        allTasks.remove(id);
        history.remove(id);
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(allEpics.values());
    }

    @Override
    public void deleteAllEpics() {
        for (Integer id : allEpics.keySet()) {
            history.remove(id);
        }
        allEpics.clear();
        deleteAllSubtasks();
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = allEpics.get(id);
        history.add(epic);
        return epic;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(++genId);
        allEpics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {

        if (!allEpics.containsKey(epic.getId())) {
            return;
        }
        allEpics.put(epic.getId(), epic);
    }

    @Override
    public void removeEpicId(int id) {
        Epic epic = allEpics.get(id);
        for (Integer subId : epic.getSubstasksIds()) {
            allSubtasks.remove(subId);
            history.remove(subId);
        }
        allEpics.remove(id);
        history.remove(id);

    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(allSubtasks.values());

    }

    @Override
    public void deleteAllSubtasks() {
        for (Integer id : allSubtasks.keySet()) {
            history.remove(id);
        }
        allSubtasks.clear();
        for (Epic epic : allEpics.values()) {
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = allSubtasks.get(id);
        history.add(subtask);
        return subtask;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        subtask.setId(++genId);
        int ids = subtask.getEpicId();
        Epic epic = allEpics.get(ids);
        if (epic == null) {
            return null;
        } else {
            if (epic.getSubstasksIds() == null) {
                ArrayList<Integer> subtasks = new ArrayList<>();
                subtasks.add(subtask.getId());
                epic.setSubstasksIds(subtasks);
            } else {
                ArrayList<Integer> subtasks = epic.getSubstasksIds();
                subtasks.add(subtask.getId());
                epic.setSubstasksIds(subtasks);
            }
        }

        allSubtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (allSubtasks.containsKey(subtask.getId())) {
            allTasks.put(subtask.getId(), subtask);
            Epic epic = getEpic(subtask.getEpicId());
            changeEpicStatus(epic);
        }
    }

    @Override
    public void removeSubtaskId(int id) {
        Subtask subtask = getSubtask(id);
        int epicId = subtask.getEpicId();
        allSubtasks.remove(id);
        history.remove(id);
        changeEpicStatus(getEpic(epicId));

    }

    @Override
    public List<Task> getSubtaskEpic(int epicId) {
        return new ArrayList<>();

    }

    private void changeEpicStatus(Epic epic) {
        int countNew = 0;
        int countDone = 0;
        if (epic.getSubstasksIds() == null) {
            epic.setStatus(Status.NEW);
        } else {
            List<Integer> subtasks = epic.getSubstasksIds();
            for (Integer idSubtask : subtasks) {
                Subtask subtask = getSubtask(idSubtask);
                switch (subtask.getStatus()) {
                    case NEW:
                        countNew = +1;
                    case DONE:
                        countDone = +1;
                    default:

                }
                if (countNew == subtasks.size()) {
                    epic.setStatus(Status.NEW);
                } else if (countDone == subtasks.size()) {
                    epic.setStatus(Status.DONE);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
        }
    }


}
