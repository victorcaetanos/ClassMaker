package model.professor.repository;

import model.professor.entity.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class ProfessorRepo implements IProfessorRepo {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertProfessor(final Professor professor) {

        String sql = "INSERT INTO professors (name, email, phoneNumber) values (?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getPhoneNumber());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir professor", e);
        }
    }

    @Override
    public boolean updateProfessor(final Professor professor) {

        String sql = "UPDATE professors SET name = ?, email = ?, phoneNumber = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getPhoneNumber());
            ps.setInt(4, professor.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar professor", e);
        }
    }

    @Override
    public boolean deactivateProfessor(final int id) {

        String sql = "UPDATE professors SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar professor", e);
        }
    }

    @Override
    public boolean reactivateProfessor(final int id) {

        String sql = "UPDATE professors SET inactive = false WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar professor", e);
        }
    }

    @Override
    public ResultSet listProfessor(final int id, final boolean onlyInactive) {

        String sql = "SELECT id, name, email, phoneNumber FROM professors WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao  listar professor", e);
        }
    }

    @Override
    public ResultSet listProfessorsByParam(final String filterValue, final boolean onlyInactive) {

        String sql = """
                SELECT id, name, email, phoneNumber FROM professors
                WHERE (id = ?, name LIKE ?, email LIKE ?, phoneNumber LIKE ?) AND professors.inactive = ?
                """;

        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setBoolean(++cont, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar professores", e);
        }
    }

    @Override
    public ResultSet listAllProfessors(final boolean onlyInactive) {

        String sql = "SELECT id, name, email, phoneNumber FROM professors WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todos os professores", e);
        }
    }
}
