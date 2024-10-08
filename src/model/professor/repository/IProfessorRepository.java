package model.professor.repository;

import model.professor.entity.Professor;

import java.util.List;

public interface IProfessorRepository {

    boolean insertProfessor(Professor professor);

    boolean updateProfessor(Professor professor);

    boolean deactivateProfessor(int id);

    boolean reactivateProfessor(int id);

    Professor listProfessor(int id, boolean onlyInactive);

    List<Professor> listProfessorsByParam(String filterValue, boolean onlyInactive);

    List<Professor> listAllProfessors(boolean onlyInactive);

    List<Professor> listAllActiveProfessors();
}
