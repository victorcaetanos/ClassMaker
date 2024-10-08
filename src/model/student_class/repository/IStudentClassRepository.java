package model.student_class.repository;

import exceptions.sql.DuplicateKeyEntryException;
import model.student_class.entity.StudentClass;

import java.util.List;

public interface IStudentClassRepository {

    boolean insertStudentClass(StudentClass studentClass) throws DuplicateKeyEntryException;

    boolean deactivateStudentClass(StudentClass studentClass);

    boolean reactivateStudentClass(StudentClass studentClass);

    List<StudentClass> listStudentClass(int MyClassId, String filterValue, boolean onlyInactive);
}
