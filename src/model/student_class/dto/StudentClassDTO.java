package model.student_class.dto;

import java.util.Objects;

public class StudentClassDTO {
    private String disciplineCode;
    private String classSemester;
    private String studentEmail;

    private String studentId;
    private String studentName;

    private String classId;
    private String disciplineName;

    public StudentClassDTO() {
    }

    public StudentClassDTO(String disciplineCode, String classSemester, String studentName, String studentEmail, String studentId, String classId, String disciplineName) {
        this.disciplineCode = disciplineCode;
        this.classSemester = classSemester;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentId = studentId;
        this.classId = classId;
        this.disciplineName = disciplineName;
    }

    public String getDisciplineCode() {
        return disciplineCode;
    }

    public void setDisciplineCode(String disciplineCode) {
        this.disciplineCode = disciplineCode;
    }

    public String getClassSemester() {
        return classSemester;
    }

    public void setClassSemester(String classSemester) {
        this.classSemester = classSemester;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StudentClassDTO other = (StudentClassDTO) obj;
        return (Objects.equals(this.getStudentId(), other.getStudentId()) && Objects.equals(this.getClassId(), other.getClassId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getClassId());
    }
}
