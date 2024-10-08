package model.myClass.repository;

import model.myClass.entity.MyClass;

import java.util.List;

public interface IMyClassRepository {

    boolean insertMyClass(MyClass myClass);

    boolean updateMyClass(MyClass myClass);

    boolean deactivateMyClass(int id);

    boolean reactivateMyClass(int id);

    MyClass listMyClass(int id, boolean onlyInactive);

    List<MyClass> listMyClassesByParam(String filterValue, boolean onlyInactive);

    List<MyClass> listAllActiveMyClasses(String filterValue);
}
