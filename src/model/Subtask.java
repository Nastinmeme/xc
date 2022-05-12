package model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description,  status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int id, Status status, Type type, int epicId) {
        super(name, description, id, status, type);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {

        this.epicId = epicId;
    }
    @Override
    public Type getType() {
        return Type.SUBTASK;
    }
}
