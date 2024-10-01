package model.student.repository;

import model.student.entity.Student;

import java.sql.ResultSet;

public interface IStudentRepo {

    boolean insertStudent(final Student student);

    boolean updateStudent(final Student student);

    boolean deactivateStudent(final int id);

    boolean reactivateStudent(final int id);

    ResultSet listStudent(final int id, final boolean onlyInactive);

    ResultSet listStudentsByParam(final String filterValue, final boolean onlyInactive);

    ResultSet listAllStudents(final boolean onlyInactive);
}
