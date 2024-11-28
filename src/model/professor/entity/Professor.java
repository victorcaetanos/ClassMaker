package model.professor.entity;

public class Professor {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String cpf;
    private String title;
    private boolean inactive;

    public Professor() {
    }

    public Professor(int id) {
        this.id = id;
    }

    public Professor(String name) {
        this.name = name;
    }

    public Professor(String name, String email, String phoneNumber, String cpf, String title) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cpf = cpf;
        this.title = title;
    }

    public Professor(int id, String name, String email, String phoneNumber, String cpf, String title) {
        this(name, email, phoneNumber, cpf, title);
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(final boolean inactive) {
        this.inactive = inactive;
    }
}
