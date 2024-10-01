package model.professor.repository;

import model.professor.entity.Professor;

import java.sql.ResultSet;

public interface IProfessorRepo {

    boolean insertProfessor(final Professor professor);

    boolean updateProfessor(final Professor professor);

    boolean deactivateProfessor(final int id);

    boolean reactivateProfessor(final int id);

    ResultSet listProfessor(final int id, final boolean onlyInactive);

    ResultSet listProfessorsByParam(final String filterValue, final boolean onlyInactive);

    ResultSet listAllProfessors(final boolean onlyInactive);
}
