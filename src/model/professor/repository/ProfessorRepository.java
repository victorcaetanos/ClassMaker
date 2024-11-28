package model.professor.repository;

import model.professor.entity.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class ProfessorRepository implements IProfessorRepository {

    private static Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertProfessor(Professor professor) {

        String sql = "INSERT INTO professors (name, email, phoneNumber, cpf, title) values (?, ?, ?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getPhoneNumber());
            ps.setString(4, professor.getCpf());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao inserir professor", e);
        }
    }

    @Override
    public boolean updateProfessor(Professor professor) {

        String sql = "UPDATE professors SET name = ?, email = ?, phoneNumber = ?, cpf = ? , title = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getPhoneNumber());
            ps.setString(4, professor.getCpf());
            ps.setString(5, professor.getTitle());
            ps.setInt(6, professor.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar professor", e);
        }
    }

    @Override
    public boolean deactivateProfessor(int id) {

        String sql = "UPDATE professors SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao excluir professor", e);
        }
    }

    @Override
    public boolean reactivateProfessor(int id) {

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
    public Professor listProfessor(int id, boolean onlyInactive) {

        String sql = "SELECT id, name, email, phoneNumber, cpf, title FROM professors WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            ResultSet rs = ps.executeQuery();
            List<Professor> professors = mapResultSetToEntity(rs);
            if (professors.isEmpty()) {
                return null;
            }
            return professors.get(0);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao  listar professor", e);
        }
    }

    @Override
    public List<Professor> listProfessorsByParam(String filterValue, boolean onlyInactive) {

        String sql = """
                SELECT id, name, email, phoneNumber, cpf, title FROM professors
                WHERE (id = ? OR name LIKE ? OR email LIKE ? OR phoneNumber LIKE ? OR cpf LIKE ? OR title LIKE ? ) AND professors.inactive = ?
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
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar professores", e);
        }
    }

    @Override
    public List<Professor> listAllProfessors(boolean onlyInactive) {

        String sql = "SELECT id, name, email, phoneNumber, cpf , title FROM professors WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todos os professores", e);
        }
    }

    @Override
    public List<Professor> listAllActiveProfessors() {

        String sql = "SELECT id, name, email, phoneNumber , cpf  , title  FROM professors WHERE inactive = false";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar professores ativos", e);
        }
    }

    private List<Professor> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        List<Professor> professorList = new ArrayList<>();

        while (resultSet.next()) {
            Professor professor = new Professor();
            professor.setId(resultSet.getInt("id"));
            professor.setName(resultSet.getString("name"));
            professor.setEmail(resultSet.getString("email"));
            professor.setPhoneNumber(resultSet.getString("phoneNumber"));
            professor.setCpf(resultSet.getString("cpf"));
            professor.setTitle(resultSet.getString("title"));
            professorList.add(professor);
        }
        return professorList;
    }
}
