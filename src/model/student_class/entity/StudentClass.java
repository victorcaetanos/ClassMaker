package model.student_class.entity;

public class StudentClass {
    private int studentId;
    private int classId;
    private boolean inactive;

    public StudentClass() {
    }

    public StudentClass(int studentId, int classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public StudentClass(int studentId, int classId, boolean inactive) {
        this(studentId, classId);
        this.inactive = inactive;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }
}
