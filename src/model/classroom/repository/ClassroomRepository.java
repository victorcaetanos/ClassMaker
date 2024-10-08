package model.classroom.repository;

import model.classroom.entity.Classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class ClassroomRepository implements IClassroomRepository {

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
            throw new RuntimeException("Falha ao excluir sala", e);
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
    public Classroom listClassroom(final int id, final boolean onlyInactive) {

        String sql = "SELECT id, name, capacity, location FROM classrooms WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            ResultSet rs = ps.executeQuery();
            List<Classroom> classrooms = mapResultSetToEntity(rs);
            if (classrooms.isEmpty()) {
                return null;
            }
            return classrooms.get(0);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao  listar sala", e);
        }
    }

    @Override
    public List<Classroom> listClassroomsByParam(final String filterValue, final boolean onlyInactive) {

        String sql = """
                SELECT id, name, location, capacity FROM classrooms
                WHERE (id = ? OR name LIKE ? OR location LIKE ? OR capacity LIKE ?) AND classrooms.inactive = ?
                """;
        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            ps.setString(++cont, "%" + filterValue + "%");
            ps.setString(++cont, "%" + filterValue + "%");
            ps.setString(++cont, "%" + filterValue + "%");
            ps.setBoolean(++cont, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar salas", e);
        }
    }

    @Override
    public List<Classroom> listAllClassrooms(final boolean onlyInactive) {

        String sql = "SELECT id, name, capacity, location FROM classrooms WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as salas", e);
        }
    }

    @Override
    public List<Classroom> listAllActiveClassrooms() {

        String sql = "SELECT id, name, location, capacity FROM classrooms WHERE inactive = false";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar salas ativas", e);
        }
    }

    private List<Classroom> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        List<Classroom> classroomList = new ArrayList<>();

        while (resultSet.next()) {
            Classroom classroom = new Classroom();
            classroom.setId(resultSet.getInt("id"));
            classroom.setName(resultSet.getString("name"));
            classroom.setCapacity(resultSet.getInt("capacity"));
            classroom.setLocation(resultSet.getString("location"));
            classroomList.add(classroom);
        }
        return classroomList;
    }
}
