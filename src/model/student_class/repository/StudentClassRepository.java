package model.student_class.repository;

import exceptions.sql.DuplicateKeyEntryException;
import model.discipline.entity.Discipline;
import model.myClass.entity.MyClass;
import model.student.entity.Student;
import model.student_class.entity.StudentClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class StudentClassRepository implements IStudentClassRepository {

    private static Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertStudentClass(StudentClass studentClass) throws DuplicateKeyEntryException {

        String sql = "INSERT INTO student_class (id_student, id_class) values (?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setInt(1, studentClass.getStudent().getId());
            ps.setInt(2, studentClass.getMyClass().getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'student_class.PRIMARY'")) {
                throw new DuplicateKeyEntryException("Estudante já vinculado a essa matéria");
            }
            throw new RuntimeException("Falha ao vincular estudante a materia", e);
        }
    }

    @Override
    public boolean deactivateStudentClass(StudentClass studentClass) {

        String sql = "UPDATE student_class SET inactive = true WHERE id_student = ? AND  id_class = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, studentClass.getStudent().getId());
            ps.setInt(2, studentClass.getMyClass().getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar estudante vinculado a materia", e);
        }
    }

    @Override
    public boolean reactivateStudentClass(StudentClass studentClass) {

        String sql = "UPDATE student_class SET inactive = false WHERE id_student = ? AND  id_class = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, studentClass.getStudent().getId());
            ps.setInt(2, studentClass.getMyClass().getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar estudante vinculado a materia", e);
        }
    }

    @Override
    public List<StudentClass> listStudentClass(int myClassId, String filterValue, boolean onlyInactive) {
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
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao selecionar estudantes vinculados a materia", e);
        }
    }

    private List<StudentClass> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        List<StudentClass> studentClasses = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();
            student.setName(resultSet.getString("students.name"));
            student.setEmail(resultSet.getString("students.email"));
            student.setId(resultSet.getInt("students.id"));

            Discipline discipline = new Discipline();
            discipline.setCode(resultSet.getString("disciplines.code"));
            discipline.setName(resultSet.getString("disciplines.name"));
            MyClass myClass = new MyClass();
            myClass.setSemester(resultSet.getString("classes.semester"));
            myClass.setId(resultSet.getInt("classes.id"));
            myClass.setDiscipline(discipline);

            StudentClass studentClass = new StudentClass();
            studentClass.setStudent(student);
            studentClass.setMyClass(myClass);
            studentClasses.add(studentClass);
        }
        return studentClasses;
    }
}
