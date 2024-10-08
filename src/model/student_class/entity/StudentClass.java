package model.student_class.entity;

import model.myClass.entity.MyClass;
import model.student.entity.Student;

public class StudentClass {
    private Student student;
    private MyClass myClass;
    private boolean inactive;

    public StudentClass() {
    }

    public StudentClass(Student student, MyClass myClass) {
        this.student = student;
        this.myClass = myClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public MyClass getMyClass() {
        return myClass;
    }

    public void setMyClass(MyClass myClass) {
        this.myClass = myClass;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }
}
