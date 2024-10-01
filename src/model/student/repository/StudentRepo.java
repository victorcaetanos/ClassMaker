package model.student.repository;

import model.student.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class StudentRepo implements IStudentRepo {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertStudent(final Student student) {

        String sql = "INSERT INTO students (name, cpf, email, phoneNumber, address) values (?, ?, ?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getCpf());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhoneNumber());
            ps.setString(5, student.getAddress());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir estudante", e);
        }
    }

    @Override
    public boolean updateStudent(final Student student) {

        String sql = "UPDATE students SET name = ?, cpf = ?, email = ?, phoneNumber = ?, address = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getCpf());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhoneNumber());
            ps.setString(5, student.getAddress());
            ps.setInt(6, student.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar estudante", e);
        }
    }

    @Override
    public boolean deactivateStudent(final int id) {

        String sql = "UPDATE students SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar estudante", e);
        }
    }

    @Override
    public boolean reactivateStudent(final int id) {

        String sql = "UPDATE students SET inactive = false WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar estudante", e);
        }
    }

    @Override
    public ResultSet listStudent(final int id, final boolean onlyInactive) {

        String sql = "SELECT id, name, cpf, email, phoneNumber, address FROM students WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar estudante", e);
        }
    }

    @Override
    public ResultSet listStudentsByParam(final String filterValue, final boolean onlyInactive) {

        String sql = """
                SELECT id, name, cpf, email, phoneNumber, address FROM students
                WHERE (id = ?, name LIKE ?, cpf LIKE ?, email LIKE ?, phoneNumber LIKE ?, address LIKE ?) AND students.inactive = ?
                """;

        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setBoolean(++cont, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar estudantes", e);
        }
    }

    @Override
    public ResultSet listAllStudents(final boolean onlyInactive) {

        String sql = "SELECT id, name, cpf, email, phoneNumber, address FROM students WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as estudantes", e);
        }
    }
}
