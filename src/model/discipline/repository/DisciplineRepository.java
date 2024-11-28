package model.discipline.repository;

import model.discipline.entity.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class DisciplineRepository implements IDisciplineRepository {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertDiscipline(final Discipline discipline) {

        String sql = "INSERT INTO disciplines (name, code, description, periodo) values (?, ?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, discipline.getName());
            ps.setString(2, discipline.getCode());
            ps.setString(3, discipline.getDescription());
            ps.setString(4, discipline.getPeriodo());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir disciplina", e);
        }
    }

    @Override
    public boolean updateDiscipline(final Discipline discipline) {

        String sql = "UPDATE disciplines SET name = ?, code = ?, description = ?, periodo = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, discipline.getName());
            ps.setString(2, discipline.getCode());
            ps.setString(3, discipline.getDescription());
            ps.setString(4, discipline.getPeriodo());
            ps.setInt(5, discipline.getId());
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
            throw new RuntimeException("Falha ao excluir disciplina", e);
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
    public Discipline listDiscipline(final int id, final boolean onlyInactive) {

        String sql = "SELECT id, name, code, description, periodo FROM disciplines WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            ResultSet rs = ps.executeQuery();
            List<Discipline> disciplines = mapResultSetToEntity(rs);
            if (disciplines.isEmpty()) {
                return null;
            }
            return disciplines.get(0);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao  listar disciplina", e);
        }
    }

    @Override
    public List<Discipline> listDisciplinesByParam(final String filterValue, final boolean onlyInactive) {

        String sql = """
                SELECT id, name, code, description, periodo FROM disciplines
                WHERE (id = ? OR name LIKE ? OR code LIKE ? OR description LIKE ? OR periodo LIKE ?) AND disciplines.inactive = ?
                """;

        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setString(++cont, filterValue);
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setString(++cont, '%' + filterValue + '%');
            ps.setBoolean(++cont, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar disciplinas", e);
        }
    }

    @Override
    public List<Discipline> listAllDisciplines(final boolean onlyInactive) {

        String sql = "SELECT id, name, code, description , periodo FROM disciplines WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as disciplinas", e);
        }
    }

    @Override
    public List<Discipline> listAllActiveDisciplines() {

        String sql = "SELECT id, name, code, description, periodo FROM disciplines WHERE inactive = false";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar professores ativos", e);
        }
    }

    private List<Discipline> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        List<Discipline> disciplineList = new ArrayList<>();

        while (resultSet.next()) {
            Discipline discipline = new Discipline();
            discipline.setId(resultSet.getInt("id"));
            discipline.setName(resultSet.getString("name"));
            discipline.setCode(resultSet.getString("code"));
            discipline.setDescription(resultSet.getString("description"));
            discipline.setPeriodo(resultSet.getString("periodo"));
            disciplineList.add(discipline);
        }
        return disciplineList;
    }
}
