package service.student_class;

import java.util.List;

import exceptions.ValidationException;
import exceptions.sql.DuplicateKeyEntryException;
import model.myClass.entity.MyClass;
import model.student.entity.Student;
import model.student_class.entity.StudentClass;

public interface IStudentClassService {

    boolean insertStudentClass(String studentId, String myClassId) throws ValidationException, DuplicateKeyEntryException;

    boolean deleteStudentClass(String studentId, String myClassId) throws ValidationException;

    boolean activateStudentClass(String studentId, String myClassId) throws ValidationException;

    List<StudentClass> listStudentClass(String myClassId, String filterValue, boolean onlyInactive) throws ValidationException;

    List<Student> listAllActiveStudents(String filterValue);

    List<MyClass> listAllActiveMyClasses(String filterValue);
}