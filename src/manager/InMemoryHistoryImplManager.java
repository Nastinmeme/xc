package manager;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryImplManager implements HistoryManager {

    HashMap<Integer, Node> historyMap = new HashMap<>();
    public Node head = null;
    public Node tail = null;

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        linkLast(task);
    }

    private void linkLast(Task task) {
        int newId = task.getId();
        if (task == null) {
            return;
        }
        Node<Task> newNode = new Node<>(null, task, null);
        Node oldNode = historyMap.get(newId);

        if (oldNode != null) {
            removeNode(oldNode);
        }

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        newNode.next = null;
        historyMap.put(task.getId(), newNode);
    }


    private void removeNode(Node oldNode) {
        Node<Task> next = oldNode.next;
        Node<Task> prev = oldNode.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            oldNode.prev = null;
        }

        if (next == null) {
            tail = prev;

        } else {
            next.prev = prev;
            oldNode.next = null;
        }
        oldNode.task = null;
    }


    @Override
    public void remove(int id) {

        try {
            Node node = historyMap.get(id);
            removeNode(node);
            historyMap.remove(id);
        }catch (NullPointerException exp){
            exp.getMessage();
        }


    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = head;
        while (node != null) {
            tasks.add((Task) node.task);
            node = node.next;
        }
        return tasks;
    }

    static class Node<Task> {
        Task task;
        Node next;
        Node prev;

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }

    }
}
