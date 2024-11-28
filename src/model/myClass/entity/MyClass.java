package model.myClass.entity;

import model.classroom.entity.Classroom;
import model.discipline.entity.Discipline;
import model.professor.entity.Professor;

public class MyClass {
    private int id;
    private Professor professor;
    private Discipline discipline;
    private Classroom classroom;
    private String weeDay;
    private String startTime;
    private String finishTime;
    private String semester;
    private boolean inactive;

    public MyClass() {
    }

    public MyClass(int id) {
        this.id = id;
    }

    public MyClass(Professor professor, Discipline discipline, Classroom classroom, String weeDay, String startTime, String finishTime, String semester) {
        this.professor = professor;
        this.discipline = discipline;
        this.classroom = classroom;
        this.weeDay = weeDay;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.semester = semester;
    }

    public MyClass(int id, Professor professor, Discipline discipline, Classroom classroom, String weeDay,String startTime, String finishTime, String semester) {
        this(professor, discipline, classroom,weeDay, startTime, finishTime, semester);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(final Professor professor) {
        this.professor = professor;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(final Classroom classroom) {
        this.classroom = classroom;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(final Discipline discipline) {
        this.discipline = discipline;
    }

    public String getWeeDay() {
        return weeDay;
    }

    public void setWeeDay(String weeDay) {
        this.weeDay = weeDay;
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
