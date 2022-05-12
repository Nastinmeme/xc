package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> substasksIds;

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    public Epic(String name, String description, int id, Status status, ArrayList<Integer> substasks) {
        super(name, description, id, status);
        this.substasksIds = substasks;
    }

    public Epic(String name, String description,  Status status) {
        super(name, description,  status);
    }

    public Epic(String name, String description, int id, Status status, Type type) {
        super(name, description, id, status, type);

    }

    @Override
    public Type getType() {
        return Type.EPIC;
    }

    public ArrayList<Integer> getSubstasksIds() {

        return substasksIds;
    }

    public void setSubstasksIds(ArrayList<Integer> substasksIds) {
        this.substasksIds = substasksIds;
    }





}
