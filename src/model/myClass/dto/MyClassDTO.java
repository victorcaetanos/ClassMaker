package model.myClass.dto;

import java.util.Objects;

public class MyClassDTO {
    private String id;

    private String professorId;
    private String professorName;

    private String disciplineId;
    private String disciplineName;

    private String classroomId;
    private String classroomName;

    private String startTime;
    private String finishTime;
    private String semester;

    public MyClassDTO() {
    }

    public MyClassDTO(String id, String disciplineName) {
        this.id = id;
        this.disciplineName = disciplineName;
    }

    public MyClassDTO(String professorName, String disciplineName, String classroomName, String startTime, String finishTime, String semester) {
        this.professorName = professorName;
        this.disciplineName = disciplineName;
        this.classroomName = classroomName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.semester = semester;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(final String professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(final String professorName) {
        this.professorName = professorName;
    }

    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(final String disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(final String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(final String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(final String classroomName) {
        this.classroomName = classroomName;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyClassDTO other = (MyClassDTO) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
