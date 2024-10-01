package model.classroom.repository;

import model.classroom.entity.Classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class ClassroomRepo implements IClassroomRepo {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertClassroom(final Classroom classroom) {

        String sql = "INSERT INTO classrooms (name, capacity, location) values (?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, classroom.getName());
            ps.setInt(2, classroom.getCapacity());
            ps.setString(3, classroom.getLocation());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir sala", e);
        }
    }

    @Override
    public boolean updateClassroom(final Classroom classroom) {

        String sql = "UPDATE classrooms SET name = ?, capacity = ?, location = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, classroom.getName());
            ps.setInt(2, classroom.getCapacity());
            ps.setString(3, classroom.getLocation());
            ps.setInt(4, classroom.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar sala", e);
        }
    }

    @Override
    public boolean deactivateClassroom(final int id) {

        String sql = "UPDATE classrooms SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar sala", e);
        }
    }

    @Override
    public boolean reactivateClassroom(final int id) {

        String sql = "UPDATE classrooms SET inactive = false WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar sala", e);
        }
    }

    @Override
    public ResultSet listClassroom(final int id, final boolean onlyInactive) {

        String sql = "SELECT id, name, capacity, location FROM classrooms WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao  listar sala", e);
        }
    }

    @Override
    public ResultSet listClassroomsByParam(final String filterValue, final boolean onlyInactive) {

        String sql = """
                SELECT id, name, location, capacity FROM classrooms
                WHERE (id = ?, name LIKE ?, location LIKE ?, capacity LIKE ?) AND classrooms.inactive = ?
                """;
        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            ps.setString(++cont, "%" + filterValue + "%");
            ps.setString(++cont, "%" + filterValue + "%");
            ps.setString(++cont, "%" + filterValue + "%");
            ps.setBoolean(++cont, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar salas", e);
        }
    }

    @Override
    public ResultSet listAllClassrooms(final boolean onlyInactive) {

        String sql = "SELECT id, name, capacity, location FROM classrooms WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as salas", e);
        }
    }
}
