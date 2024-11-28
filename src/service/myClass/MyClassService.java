package service.myClass;

import exceptions.ValidationException;
import model.classroom.entity.Classroom;
import model.classroom.repository.IClassroomRepository;
import model.discipline.entity.Discipline;
import model.discipline.repository.IDisciplineRepository;
import model.myClass.entity.MyClass;
import model.myClass.repository.IMyClassRepository;
import model.professor.entity.Professor;
import model.professor.repository.IProfessorRepository;
import utils.ParseUtils;
import utils.Validation.MyClassValidation;
import utils.Validation.ValidationUtils;

import java.util.List;

public class MyClassService implements IMyClassService {
    private final IMyClassRepository myClassRepo;
    private final IProfessorRepository professorRepo;
    private final IDisciplineRepository disciplineRepo;
    private final IClassroomRepository classroomRepo;

    public MyClassService(
            IMyClassRepository myClassRepo,
            IProfessorRepository professorRepo,
            IDisciplineRepository disciplineRepo,
            IClassroomRepository classroomRepo
    ) {
        this.myClassRepo = myClassRepo;
        this.professorRepo = professorRepo;
        this.disciplineRepo = disciplineRepo;
        this.classroomRepo = classroomRepo;
    }

    @Override
    public boolean insertMyClass(String professorId, String disciplineId, String classroomId,String weekDay,  String startTime, String finishTime, String semester) throws ValidationException {
        int parsedProfessorId = ParseUtils.parseId(professorId);
        int parsedDisciplineId = ParseUtils.parseId(disciplineId);
        int parsedClassroomId = ParseUtils.parseId(classroomId);
        MyClassValidation.validateMyClassFields(null, parsedProfessorId, parsedDisciplineId, parsedClassroomId,weekDay,  startTime, finishTime, semester);

        Professor professor = new Professor(parsedProfessorId);
        Discipline discipline = new Discipline(parsedDisciplineId);
        Classroom classroom = new Classroom(parsedClassroomId);
        return myClassRepo.insertMyClass(
                new MyClass(
                        professor, discipline, classroom,
                        weekDay,  startTime, finishTime, semester
                )
        );
    }


    @Override
    public boolean updateMyClass(String myClassId, String professorId, String disciplineId, String classroomId, String weekDay, String startTime, String finishTime, String semester) throws ValidationException {
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        int parsedProfessorId = ParseUtils.parseId(professorId);
        int parsedDisciplineId = ParseUtils.parseId(disciplineId);
        int parsedClassroomId = ParseUtils.parseId(classroomId);
        MyClassValidation.validateMyClassFields(parsedMyClassId, parsedProfessorId, parsedDisciplineId, parsedClassroomId, weekDay, startTime, finishTime, semester);

        Professor professor = new Professor(parsedProfessorId);
        Discipline discipline = new Discipline(parsedDisciplineId);
        Classroom classroom = new Classroom(parsedClassroomId);
        return myClassRepo.updateMyClass(
                new MyClass(
                        parsedMyClassId, professor, discipline, classroom,
                        weekDay, startTime, finishTime, semester
                )
        );
    }

    @Override
    public boolean deleteMyClass(String myClassId) throws ValidationException {
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        ValidationUtils.validateId(parsedMyClassId);
        return myClassRepo.deactivateMyClass(parsedMyClassId);
    }

    @Override
    public boolean activateMyClass(String myClassId) throws ValidationException {
        int parsedMyClassId = ParseUtils.parseId(myClassId);
        ValidationUtils.validateId(parsedMyClassId);
        return myClassRepo.reactivateMyClass(parsedMyClassId);
    }

    @Override
    public MyClass getMyClass(String myclassId, boolean onlyInactive) throws ValidationException {

        int parsedID = ParseUtils.parseId(myclassId);
        ValidationUtils.validateId(parsedID);
        return myClassRepo.listMyClass(parsedID, onlyInactive);
    }

    @Override
    public List<MyClass> getMyClassList(String value, boolean onlyInactive) {
        //TODO: If ever needed, check for sql injection
        return myClassRepo.listMyClassesByParam(value, onlyInactive);
    }

    @Override
    public List<Professor> getAllActiveProfessorsList() {
        return professorRepo.listAllActiveProfessors();
    }

    @Override
    public List<Discipline> getAllActiveDisciplinesList() {
        return disciplineRepo.listAllActiveDisciplines();
    }

    @Override
    public List<Classroom> getAllActiveClassroomsList() {
        return classroomRepo.listAllActiveClassrooms();
    }
}
