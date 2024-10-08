package model.classroom.repository;

import model.classroom.entity.Classroom;

import java.util.List;

public interface IClassroomRepository {

    boolean insertClassroom(Classroom classroom);

    boolean updateClassroom(Classroom classroom);

    boolean deactivateClassroom(int id);

    boolean reactivateClassroom(int id);

    Classroom listClassroom(int id, boolean onlyInactive);

    List<Classroom> listClassroomsByParam(String filterValue, boolean onlyInactive);

    List<Classroom> listAllClassrooms(boolean onlyInactive);

    List<Classroom> listAllActiveClassrooms();
}
