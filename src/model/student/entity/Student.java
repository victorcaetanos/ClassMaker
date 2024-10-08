package model.student.entity;

public class Student {
    private int id;
    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean inactive;

    public Student() {
    }

    public Student(final int id) {
        this.id = id;
    }

    public Student(final String name, final String cpf, final String email, final String phoneNumber, final String address) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Student(final int id, final String name, final String cpf, final String email, final String phoneNumber, final String address) {
        this(name, cpf, email, phoneNumber, address);
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(final boolean inactive) {
        this.inactive = inactive;
    }
}
