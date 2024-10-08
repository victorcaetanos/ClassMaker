package service.myClass;

import exceptions.ValidationException;
import model.classroom.entity.Classroom;
import model.discipline.entity.Discipline;
import model.myClass.entity.MyClass;
import model.professor.entity.Professor;

import java.util.List;

public interface IMyClassService {

    boolean insertMyClass(String professorId, String disciplineId, String classroomId,
                          String startTime, String finishTime, String semester) throws ValidationException;

    boolean updateMyClass(String myClassId, String professorId, String disciplineId, String classroomId,
                          String startTime, String finishTime, String semester) throws ValidationException;

    boolean deleteMyClass(String myclassId) throws ValidationException;

    boolean activateMyClass(String myclassId) throws ValidationException;

    MyClass getMyClass(String myclassId, boolean onlyInactive) throws ValidationException;

    List<MyClass> getMyClassList(String value, boolean onlyInactive);

    List<Professor> getAllActiveProfessorsList();

    List<Discipline> getAllActiveDisciplinesList();

    List<Classroom> getAllActiveClassroomsList();
}

