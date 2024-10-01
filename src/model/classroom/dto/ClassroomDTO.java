package model.classroom.dto;

import java.util.Objects;

public class ClassroomDTO {
    private String id;
    private String name;
    private String capacity;
    private String location;

    public ClassroomDTO() {
    }

    public ClassroomDTO(String id, String name, String capacity, String location) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(final String capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassroomDTO other = (ClassroomDTO) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
