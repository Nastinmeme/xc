package manager;

import java.util.List;

import model.Epic;
import model.Subtask;
import model.Task;

public interface TaskManager {
    int getGenId();

    void setGenId(int genId);

    /**
     * Методы для задач
     * <p>
     * 1.+
     * Получение списка всех задач
     */
    List<Task> getTasks();

    /**
     * 2+
     * Удаление задач
     */
    void deleteAllTasks();

    /**
     * 3+
     * Получение по идентификатору
     */
    Task getTask(int id);

    /**
     * 4+
     * Создание задачи
     */
    Task createTask(Task task);

    /**
     * 5+
     * Обновление задачи
     */
    void updateTask(Task task);

    /**
     * 6+
     * Удаление задачи по ИД
     */
    void removeTask(int id);
    /**
     Методы для Эпиков
     */

    /**
     * 7+
     * Получение всех эпиков
     */
    List<Epic> getEpics();

    /**
     * 8+
     * удаление всех эпиков
     */
    void deleteAllEpics();

    /**
     * 9+
     * Получение эпика по ид
     */
    Epic getEpic(int id);

    /**
     * 10+
     * Создание эпика
     */
    Epic createEpic(Epic epic);

    /**
     * 11+
     * Обновление эпика
     */
    void updateEpic(Epic epic);

    /**
     * 12+
     * Удаление эпика по ид.
     */
    void removeEpicId(int id);

    /**
     * Методы для Подзадач
     * 13+
     * Получение всех подзадач
     */
    List<Subtask> getSubtasks();

    /**
     * 14+
     * удаление всех подзадач
     */
    void deleteAllSubtasks();

    /**
     * 15+
     * Получение подзадач по ид
     */
    Subtask getSubtask(int id);

    /**
     * 16
     * Создание подзадач
     */
    Subtask createSubtask(Subtask subtask);

    /**
     * 17
     * Обновление подзадач
     */
    void updateSubtask(Subtask subtask);

    /**
     * 18+
     * Удаление подзадач по ид.
     */
    void removeSubtaskId(int id);

    /**
     * Получение подзадач по эпику
     */
    List<Task> getSubtaskEpic(int epicId);

    List<Task> getHistory();
}


