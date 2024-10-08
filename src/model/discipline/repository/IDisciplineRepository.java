package model.discipline.repository;

import model.discipline.entity.Discipline;

import java.util.List;

public interface IDisciplineRepository {

    boolean insertDiscipline(Discipline discipline);

    boolean updateDiscipline(Discipline discipline);

    boolean deactivateDiscipline(int id);

    boolean reactivateDiscipline(int id);

    Discipline listDiscipline(int id, boolean onlyInactive);

    List<Discipline> listDisciplinesByParam(String filterValue, boolean onlyInactive);

    List<Discipline> listAllDisciplines(boolean onlyInactive);

    List<Discipline> listAllActiveDisciplines();
}
