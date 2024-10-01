package model.discipline.repository;

import model.discipline.entity.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class DisciplineRepo implements IDisciplineRepo {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertDiscipline(final Discipline discipline) {

        String sql = "INSERT INTO disciplines (name, code, description) values (?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, discipline.getName());
            ps.setString(2, discipline.getCode());
            ps.setString(3, discipline.getDescription());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir disciplina", e);
        }
    }

    @Override
    public boolean updateDiscipline(final Discipline discipline) {

        String sql = "UPDATE disciplines SET name = ?, code = ?, description = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, discipline.getName());
            ps.setString(2, discipline.getCode());
            ps.setString(3, discipline.getDescription());
            ps.setInt(4, discipline.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar disciplina", e);
        }
    }

    @Override
    public boolean deactivateDiscipline(final int id) {

        String sql = "UPDATE disciplines SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao desativar disciplina", e);
        }
    }

    @Override
    public boolean reactivateDiscipline(final int id) {

        String sql = "UPDATE disciplines SET inactive = false WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao reativar disciplina", e);
        }
    }

    @Override
    public ResultSet listDiscipline(final int id, final boolean onlyInactive) {

        String sql = "SELECT id, name, code, description FROM disciplines WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao  listar disciplina", e);
        }
    }

    @Override
    public ResultSet listDisciplinesByParam(final String filterValue, final boolean onlyInactive) {

        String sql = """
                SELECT id, name, code, description FROM disciplines
                WHERE (id = ?, name LIKE ?, code LIKE ?, description LIKE ?) AND disciplines.inactive = ?
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
            throw new RuntimeException("Falha ao filtrar disciplinas", e);
        }
    }

    @Override
    public ResultSet listAllDisciplines(final boolean onlyInactive) {

        String sql = "SELECT id, name, code, description FROM disciplines WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as disciplinas", e);
        }
    }
}
