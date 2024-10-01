package model.myClass.repository;

import model.myClass.entity.MyClass;

import java.sql.ResultSet;

public interface IMyClassRepo {

    boolean insertMyClass(final MyClass myClass);

    boolean updateMyClass(final MyClass myClass);

    boolean deactivateMyClass(final int id);

    boolean reactivateMyClass(final int id);

    ResultSet listMyClass(final int id, final boolean onlyInactive);

    ResultSet listMyClassesByParam(final String filterValue, final boolean onlyInactive);

    ResultSet listAllMyClasses(final boolean onlyInactive);

    ResultSet listAllActiveProfessors();

    ResultSet listAllActiveDisciplines();

    ResultSet listAllActiveClassrooms();
}
