package model.student_class.repository;

import model.student_class.entity.StudentClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class StudentClassRepo implements IStudentClassRepo {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertStudentClass(final StudentClass studentClass) {

        String sql = "INSERT INTO student_class (id_student, id_class) values (?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setInt(1, studentClass.getStudentId());
            ps.setInt(2, studentClass.getClassId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao vincular estudante a materia", e);
        }
    }

    @Override
    public boolean deactivateStudentClass(final StudentClass studentClass) {

        String sql = "UPDATE student_class SET inactive = true WHERE id_student = ? AND  id_class = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, studentClass.getStudentId());
            ps.setInt(2, studentClass.getClassId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar estudante vinculado a materia", e);
        }
    }

    @Override
    public boolean reactivateStudentClass(final StudentClass studentClass) {

        String sql = "UPDATE student_class SET inactive = false WHERE id_student = ? AND  id_class = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, studentClass.getStudentId());
            ps.setInt(2, studentClass.getClassId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar estudante vinculado a materia", e);
        }
    }

    @Override
    public ResultSet listStudentClass(final int myClassId, final String filterValue, final boolean onlyInactive) {
        String sql = """
                SELECT disciplines.code, disciplines.name, classes.semester, students.name, students.email, students.id, classes.id FROM student_class
                    INNER JOIN students ON student_class.id_student = students.id
                    INNER JOIN classes ON student_class.id_class = classes.id
                    INNER JOIN disciplines ON classes.discipline_id = disciplines.id
                WHERE (
                    students.id             = ? OR
                    students.name        LIKE ? OR
                    students.email       LIKE ? OR
                    students.phoneNumber LIKE ? OR
                    students.address     LIKE ? OR
                    students.cpf         LIKE ?
                    )
                    AND student_class.id_class = ? AND student_class.inactive = ?
                """;
        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            while (cont < 6)
                ps.setString(++cont, "%" + filterValue + "%");
            ps.setInt(++cont, myClassId);
            ps.setBoolean(++cont, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao selecionar estudantes vinculados a materia", e);
        }
    }

    @Override
    public ResultSet listAllActiveStudents(final String filterValue) {
        String sql = """
                SELECT id, name, email, phoneNumber FROM students
                WHERE (
                    students.id             = ? OR
                    students.name        LIKE ? OR
                    students.email       LIKE ? OR
                    students.phoneNumber LIKE ? OR
                    students.address     LIKE ? OR
                    students.cpf         LIKE ?
                    )
                AND students.inactive = false
                """;

        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            while (cont < 6) {
                ps.setString(++cont, "%" + filterValue + "%");
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar estudantes ativos", e);
        }
    }

    @Override
    public ResultSet listAllActiveMyClasses(final String filterValue) {
        // this was requested by the university professor, even tho it kills the database performance
        String sql = """
                SELECT classes.id, professors.name, disciplines.name, classrooms.name, semester FROM classes
                    INNER JOIN professors ON professors.id = classes.professor_id
                    INNER JOIN disciplines ON disciplines.id = classes.discipline_id
                    INNER JOIN classrooms ON classrooms.id = classes.classroom_id
                    LEFT JOIN student_class ON classes.id = student_class.id_class
                    LEFT JOIN students ON student_class.id_student = students.id
                WHERE ((
                    classes.id = ? OR professors.id = ? OR disciplines.id = ? OR classrooms.id = ? OR students.id = ?
                ) OR (
                    classes.start_time LIKE ? OR classes.finish_time LIKE ? OR classes.semester      LIKE ? OR
                    professors.name  LIKE ? OR professors.email    LIKE ? OR professors.phoneNumber  LIKE ? OR
                    disciplines.name LIKE ? OR disciplines.code    LIKE ? OR disciplines.description LIKE ? OR
                    classrooms.name  LIKE ? OR classrooms.capacity LIKE ? OR classrooms.location     LIKE ? OR
                    students.name    LIKE ? OR students.email      LIKE ? OR students.phoneNumber    LIKE ? OR
                    students.cpf     LIKE ? OR students.address    LIKE ?
                ))
                    AND classes.inactive = false
                GROUP BY classes.id, professors.name, disciplines.name, classrooms.name, semester
                """;
        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            while (cont < 6) {
                ps.setString(++cont, filterValue);
            }
            while (cont < 22) {
                ps.setString(++cont, "%" + filterValue + "%");
            }

            return ps.executeQuery();

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar turmas ativas", e);
        }
    }
}
