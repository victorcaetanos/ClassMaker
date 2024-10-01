package model.classroom.entity;

public class Classroom {
    private int id;
    private String name;
    private int capacity;
    private String location;
    private boolean inactive;

    public Classroom() {
    }

    public Classroom(String name, int capacity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public Classroom(int id, String name, int capacity, String location) {
        this(name, capacity, location);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(final boolean inactive) {
        this.inactive = inactive;
    }
}
