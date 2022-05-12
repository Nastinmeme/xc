package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.Type.TASK;
import static model.Type.valueOf;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    private void save() {

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<Integer, Task> entry : allTasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.append("\n");

            }
            for (Map.Entry<Integer, Epic> entry : allEpics.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.append("\n");

            }
            for (Map.Entry<Integer, Subtask> entry : allSubtasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.append("\n");

            }

            for (Task task : Managers.getDefaultHistory().getHistory()) {
                int id= task.getId();
                writer.append(Integer.toString(id));
                writer.append(",");

            }


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }

    }

    private void load() {


    }

    public static FileBackedTasksManager loadFromFile(File file) {
        return null;
    }


    @Override
    public ArrayList<Task> getTasks() {
        final ArrayList<Task> tasks = super.getTasks();
        save();
        return tasks;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTask(int id) {
        final Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Task createTask(Task task) {
        final Task task1 = super.createTask(task);
        save();
        return task1;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        final ArrayList<Epic> epics = super.getEpics();
        save();
        return epics;
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Epic createEpic(Epic epic) {
        final Epic epic1 = super.createEpic(epic);
        save();
        return epic1;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeEpicId(int id) {
        super.removeEpicId(id);
        save();
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        final ArrayList<Subtask> subtasks = super.getSubtasks();
        save();
        return subtasks;
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Subtask getSubtask(int id) {
        final Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Subtask subtask1 = super.createSubtask(subtask);
        save();
        return subtask1;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeSubtaskId(int id) {
        super.removeSubtaskId(id);
        save();
    }

    @Override
    public List<Task> getSubtaskEpic(int epicId) {
        List<Task> subtaskEpic = super.getSubtaskEpic(epicId);
        save();
        return subtaskEpic;
    }

    private String toString(Task task) {

        return String.format("%d,%s,%s,%s,%s", task.getId(), task.getType(), task.getName(), task.getDescription(),
                task.getStatus());
    }

    private String toString(Epic epic) {

        return String.format("%d,%s,%s,%s,%s", epic.getId(), TASK, epic.getName(), epic.getDescription(),
                epic.getStatus());
    }

    private String toString(Subtask subtask) {

        return String.format("%d,%s,%s,%s,%s,%d", subtask.getId(), TASK, subtask.getName(), subtask.getDescription(),
                subtask.getStatus(), subtask.getEpicId());
    }

    private Task fromString(String value) {
        String[] typeString = value.split(",");
        Task task;
        switch (valueOf(typeString[1])) {
            case TASK:
                task = new Task(typeString[2], typeString[3], Integer.parseInt(typeString[0]), Status.valueOf(typeString[4]),
                        valueOf(typeString[1]));
                break;
            case EPIC:
                task = new Epic(typeString[2], typeString[3], Integer.parseInt(typeString[0]), Status.valueOf(typeString[4]),
                        valueOf(typeString[1]));
                break;
            case SUBTASK:
                task = new Subtask(typeString[2], typeString[3], Integer.parseInt(typeString[0]), Status.valueOf(typeString[4]),
                        valueOf(typeString[1]), Integer.parseInt(typeString[5]));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + valueOf(typeString[1]));
        }
        return task;
    }

    static String toString(HistoryManager manager) {
        StringBuilder historyString = new StringBuilder();
        for (Task task : manager.getHistory()) {
            historyString.append(task.getId() + ",");
        }

        return historyString.toString();
    }


    static List<Integer> historyFromString(String value) {
        String[] historyId = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String id : historyId) {
            history.add(Integer.parseInt(id));
        }
        return history;
    }
}

