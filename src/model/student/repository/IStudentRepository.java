package model.student.repository;

import model.student.entity.Student;

import java.util.List;

public interface IStudentRepository {

    boolean insertStudent(Student student);

    boolean updateStudent(Student student);

    boolean deactivateStudent(int id);

    boolean reactivateStudent(int id);

    Student listStudent(int id, boolean onlyInactive);

    List<Student> listStudentsByParam(String filterValue, boolean onlyInactive);

    List<Student> listAllStudents(boolean onlyInactive);

    List<Student> listAllActiveStudents(String filterValue);
}
