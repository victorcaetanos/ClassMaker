package model.student_class.repository;

import model.student_class.entity.StudentClass;

import java.sql.ResultSet;

public interface IStudentClassRepo {

    boolean insertStudentClass(final StudentClass studentClass);

    boolean deactivateStudentClass(final StudentClass studentClass);

    boolean reactivateStudentClass(final StudentClass studentClass);

    ResultSet listStudentClass(final int MyClassId, final String filterValue, final boolean onlyInactive);

    ResultSet listAllActiveStudents(final String filterValue);

    ResultSet listAllActiveMyClasses(final String filterValue);
}
