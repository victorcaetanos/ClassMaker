package model.discipline.repository;

import model.discipline.entity.Discipline;

import java.sql.ResultSet;

public interface IDisciplineRepo {

    boolean insertDiscipline(final Discipline discipline);

    boolean updateDiscipline(final Discipline discipline);

    boolean deactivateDiscipline(final int id);

    boolean reactivateDiscipline(final int id);

    ResultSet listDiscipline(final int id, final boolean onlyInactive);

    ResultSet listDisciplinesByParam(final String filterValue, final boolean onlyInactive);

    ResultSet listAllDisciplines(final boolean onlyInactive);
}
