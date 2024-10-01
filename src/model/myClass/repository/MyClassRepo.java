package model.myClass.repository;

import model.myClass.entity.MyClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class MyClassRepo implements IMyClassRepo {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertMyClass(final MyClass myClass) {

        String sql = "INSERT INTO classes (professor_id, discipline_id, classroom_id, start_time, finish_time, semester) values (?, ?, ?, ?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setInt(1, myClass.getProfessorId());
            ps.setInt(2, myClass.getDisciplineId());
            ps.setInt(3, myClass.getClassroomId());
            ps.setString(4, myClass.getStartTime());
            ps.setString(5, myClass.getFinishTime());
            ps.setString(6, myClass.getSemester());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir turma", e);
        }
    }

    @Override
    public boolean updateMyClass(final MyClass myClass) {

        String sql = "UPDATE classes SET professor_id = ?, discipline_id = ?, classroom_id = ?, start_time = ?, finish_time = ?, semester = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setInt(1, myClass.getProfessorId());
            ps.setInt(2, myClass.getDisciplineId());
            ps.setInt(3, myClass.getClassroomId());
            ps.setString(4, myClass.getStartTime());
            ps.setString(5, myClass.getFinishTime());
            ps.setString(6, myClass.getSemester());
            ps.setInt(7, myClass.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar turma", e);
        }
    }

    @Override
    public boolean deactivateMyClass(final int id) {

        String sql = "UPDATE classes SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar turma", e);
        }
    }

    @Override
    public boolean reactivateMyClass(final int id) {

        String sql = "UPDATE classes SET inactive = false WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar turma", e);
        }
    }

    @Override
    public ResultSet listMyClass(final int id, final boolean onlyInactive) {

        String sql = """
                SELECT classes.id,
                    professors.name, professors.id, disciplines.name, disciplines.id,
                    classrooms.name, classrooms.id, start_time, finish_time, semester 
                FROM classes 
                    INNER JOIN professors ON professors.id = classes.professor_id
                    INNER JOIN disciplines ON disciplines.id = classes.discipline_id 
                    INNER JOIN classrooms ON classrooms.id = classes.classroom_id
                WHERE classes.id = ? AND classes.inactive = ?
                """;

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar turma", e);
        }
    }

    @Override
    public ResultSet listMyClassesByParam(final String filterValue, final boolean onlyInactive) {
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
            ps.setBoolean(++cont, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar turmas", e);
        }
    }

    @Override
    public ResultSet listAllMyClasses(final boolean onlyInactive) {

        String sql = """
                SELECT classes.id, professors.name, disciplines.name, classrooms.name, start_time, finish_time, semester FROM classes 
                    INNER JOIN professors  ON professors.id  = classes.professor_id 
                    INNER JOIN disciplines ON disciplines.id = classes.discipline_id
                    INNER JOIN classrooms  ON classrooms.id  = classes.classroom_id
                WHERE classes.inactive = ?
                """;

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as turmas", e);
        }
    }

    @Override
    public ResultSet listAllActiveProfessors() {

        String sql = "SELECT id, name FROM professors WHERE inactive = false";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar professores ativos", e);
        }
    }

    @Override
    public ResultSet listAllActiveDisciplines() {

        String sql = "SELECT id, name, code FROM disciplines WHERE inactive = false";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar professores ativos", e);
        }
    }

    @Override
    public ResultSet listAllActiveClassrooms() {

        String sql = "SELECT id, name, location FROM classrooms WHERE inactive = false";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar salas ativas", e);
        }
    }
}
