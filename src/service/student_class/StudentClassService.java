package service.student_class;

import exceptions.ValidationException;
import exceptions.sql.DuplicateKeyEntryException;
import model.myClass.entity.MyClass;
import model.myClass.repository.IMyClassRepository;
import model.student.entity.Student;
import model.student.repository.IStudentRepository;
import model.student_class.entity.StudentClass;
import model.student_class.repository.IStudentClassRepository;
import utils.ParseUtils;
import utils.Validation.ValidationUtils;

import java.util.List;

public class StudentClassService implements IStudentClassService {
    IStudentClassRepository studentClassRepo;
    IStudentRepository studentRepo;
    IMyClassRepository myClassRepo;

    public StudentClassService(
            IStudentClassRepository studentClassRepo,
            IStudentRepository studentRepo,
            IMyClassRepository myClassRepo
    ) {
        this.studentClassRepo = studentClassRepo;
        this.myClassRepo = myClassRepo;
        this.studentRepo = studentRepo;
    }

    public IStudentClassRepository getStudentClassRepo() {
        return studentClassRepo;
    }

    public void setStudentClassRepo(IStudentClassRepository studentClassRepo) {
        this.studentClassRepo = studentClassRepo;
    }

    public IStudentRepository getStudentRepo() {
        return studentRepo;
    }

    public void setStudentRepo(IStudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    public IMyClassRepository getMyClassRepo() {
        return myClassRepo;
    }

    public void setMyClassRepo(IMyClassRepository myClassRepo) {
        this.myClassRepo = myClassRepo;
    }

    @Override
    public boolean insertStudentClass(String studentId, String myClassId) throws ValidationException, DuplicateKeyEntryException {
        int parsedStudentId = ParseUtils.parseId(studentId);
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        ValidationUtils.validateId(parsedStudentId);
        ValidationUtils.validateId(parsedMyClassId);
        return studentClassRepo.insertStudentClass(
                new StudentClass(
                        new Student(parsedStudentId),
                        new MyClass(parsedMyClassId)
                )
        );
    }

    @Override
    public boolean deleteStudentClass(String studentId, String myClassId) throws ValidationException {
        int parsedStudentId = ParseUtils.parseId(studentId);
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        ValidationUtils.validateId(parsedStudentId);
        ValidationUtils.validateId(parsedMyClassId);
        return studentClassRepo.deactivateStudentClass(
                new StudentClass(
                        new Student(parsedStudentId),
                        new MyClass(parsedMyClassId)
                )
        );
    }

    @Override
    public boolean activateStudentClass(String studentId, String myClassId) throws ValidationException {
        int parsedStudentId = ParseUtils.parseId(studentId);
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        ValidationUtils.validateId(parsedStudentId);
        ValidationUtils.validateId(parsedMyClassId);
        return studentClassRepo.reactivateStudentClass(
                new StudentClass(
                        new Student(parsedStudentId),
                        new MyClass(parsedMyClassId)
                )
        );
    }

    @Override
    public List<StudentClass> listStudentClass(String myClassId, String filterValue, boolean onlyInactive) throws ValidationException {
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        ValidationUtils.validateId(parsedMyClassId);
        return studentClassRepo.listStudentClass(parsedMyClassId, filterValue, onlyInactive);
    }

    @Override
    public List<Student> listAllActiveStudents(String filterValue) {
        return studentRepo.listAllActiveStudents(filterValue);
    }

    @Override
    public List<MyClass> listAllActiveMyClasses(String filterValue) {
        return myClassRepo.listAllActiveMyClasses(filterValue);
    }
}
