import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task newTask = new Task("первая задача", "ее описание", Status.NEW);
        Task someTask = new Task("вторая задачка", "ее какое-то описание",
                Status.NEW);
        Epic newEpic = new Epic("первый эпик с подзадачами", "опискание эпика,нужно ли оно?", Status.NEW);

        Epic newEpic2 = new Epic("2 эпик без подзадач", "опискание эпика,нужно ли оно?", Status.NEW);
        taskManager.createEpic(newEpic);
        taskManager.createEpic(newEpic2);

        Subtask newSub = new Subtask("Первый саб", "опаисание странного саба", Status.NEW,
                newEpic.getId());
        Subtask newSub2 = new Subtask("2 саб", "опаисание странного саба", Status.NEW, newEpic.
                getId());
        Subtask newSub3 = new Subtask("3 саб", "опаисание странного саба", Status.NEW, newEpic.
                getId());

        taskManager.createTask(newTask);
        taskManager.createTask(someTask);
        taskManager.createSubtask(newSub);
        taskManager.createSubtask(newSub2);
        taskManager.createSubtask(newSub3);
        /**
         * методы
         */
        taskManager.getEpic(newEpic.getId());//вызов 1 эпика
        System.out.println("Размер истории " + (taskManager.getHistory()).size());
        taskManager.getTask(newTask.getId()); //вызов 2
        System.out.println("Размер истории " + (taskManager.getHistory()).size());
        taskManager.getEpic(newEpic2.getId()); //вызов 3
        System.out.println("Размер истории " + (taskManager.getHistory()).size());
        taskManager.getSubtask(newSub3.getId());//вызов 4
        taskManager.getSubtask(newSub2.getId());// вызов 5
        System.out.println("Размер истории " + (taskManager.getHistory()).size());
        taskManager.getTask(newTask.getId());  //повторный вызов
        System.out.println("Размер истории после повтора " + (taskManager.getHistory()).size());
        System.out.println(taskManager.getHistory()); //history
        taskManager.removeTask(newTask.getId()); //удаление таска
        taskManager.removeEpicId(newEpic.getId()); //удаление эпика
        System.out.println("Размер истории после удаление таска и эпиков с сабами " + (taskManager.getHistory()).size());
    }
}
