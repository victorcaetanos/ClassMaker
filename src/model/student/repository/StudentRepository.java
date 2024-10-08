package model.student.repository;

import model.student.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class StudentRepository implements IStudentRepository {

    private static Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertStudent(Student student) {

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
    public boolean updateStudent(Student student) {

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
    public boolean deactivateStudent(int id) {

        String sql = "UPDATE students SET inactive = true WHERE id = ?";

        try {
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao excluir estudante", e);
        }
    }

    @Override
    public boolean reactivateStudent(int id) {

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
    public Student listStudent(int id, boolean onlyInactive) {

        String sql = "SELECT id, name, cpf, email, phoneNumber, address FROM students WHERE id = ? AND inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setInt(1, id);
            ps.setBoolean(2, onlyInactive);
            ResultSet rs = ps.executeQuery();
            List<Student> students = mapResultSetToEntity(rs);
            if (students.isEmpty()) {
                return null;
            }
            return students.get(0);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar estudante", e);
        }
    }

    @Override
    public List<Student> listStudentsByParam(String filterValue, boolean onlyInactive) {

        String sql = """
                SELECT id, name, cpf, email, phoneNumber, address FROM students
                WHERE (id = ? OR name LIKE ? OR cpf LIKE ? OR email LIKE ? OR phoneNumber LIKE ? OR address LIKE ?) AND students.inactive = ?
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
            throw new RuntimeException("Falha ao filtrar estudantes", e);
        }
    }

    @Override
    public List<Student> listAllStudents(boolean onlyInactive) {

        String sql = "SELECT id, name, cpf, email, phoneNumber, address FROM students WHERE inactive = ?";

        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            ps.setBoolean(1, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar todas as estudantes", e);
        }
    }


    @Override
    public List<Student> listAllActiveStudents(String filterValue) {
        String sql = """
                SELECT id, name, cpf, email, phoneNumber, address FROM students
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
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar estudantes ativos", e);
        }
    }

    private List<Student> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        List<Student> studentList = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setCpf(resultSet.getString("cpf"));
            student.setEmail(resultSet.getString("email"));
            student.setPhoneNumber(resultSet.getString("phoneNumber"));
            student.setAddress(resultSet.getString("address"));
            studentList.add(student);
        }
        return studentList;
    }
}
