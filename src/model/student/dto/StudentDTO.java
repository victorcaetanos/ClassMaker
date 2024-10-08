package model.student.dto;

import java.util.Objects;

public class StudentDTO {
    private String id;
    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;
    private String address;

    public StudentDTO() {
    }

    public StudentDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentDTO(String id, String name, String cpf, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StudentDTO other = (StudentDTO) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
