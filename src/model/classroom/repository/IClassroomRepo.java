package model.classroom.repository;

import model.classroom.entity.Classroom;

import java.sql.ResultSet;

public interface IClassroomRepo {

    boolean insertClassroom(final Classroom classroom);

    boolean updateClassroom(final Classroom classroom);

    boolean deactivateClassroom(final int id);

    boolean reactivateClassroom(final int id);

    ResultSet listClassroom(final int id, final boolean onlyInactive);

    ResultSet listClassroomsByParam(final String filterValue, final boolean onlyInactive);

    ResultSet listAllClassrooms(final boolean onlyInactive);
}
