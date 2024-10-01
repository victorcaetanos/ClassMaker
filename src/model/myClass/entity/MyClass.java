package model.myClass.entity;

public class MyClass {
    private int id;
    private int professorId;
    private int disciplineId;
    private int classroomId;
    private String startTime;
    private String finishTime;
    private String semester;
    private boolean inactive;

    public MyClass() {
    }

    public MyClass(int professorId, int disciplineId, int classroomId, String startTime, String finishTime, String semester) {
        this.professorId = professorId;
        this.disciplineId = disciplineId;
        this.classroomId = classroomId;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.semester = semester;
    }

    public MyClass(int id, int professorId, int disciplineId, int classroomId, String startTime, String finishTime, String semester) {
        this(professorId, disciplineId, classroomId, startTime, finishTime, semester);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(final int professorId) {
        this.professorId = professorId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(final int classroomId) {
        this.classroomId = classroomId;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(final int disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(final String finishTime) {
        this.finishTime = finishTime;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(final String semester) {
        this.semester = semester;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(final boolean inactive) {
        this.inactive = inactive;
    }

}
