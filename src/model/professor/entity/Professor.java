package model.professor.entity;

public class Professor {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean inactive;

    public Professor() {
    }

    public Professor(String name, String email, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Professor(int id, String name, String email, String phoneNumber) {
        this(name, email, phoneNumber);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(final boolean inactive) {
        this.inactive = inactive;
    }
}
