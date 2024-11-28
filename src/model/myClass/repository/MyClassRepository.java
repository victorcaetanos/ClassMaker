package model.myClass.repository;

import model.classroom.entity.Classroom;
import model.discipline.entity.Discipline;
import model.myClass.entity.MyClass;
import model.professor.entity.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.DbConnection.getConnection;

public class MyClassRepository implements IMyClassRepository {

    private static final Connection con = getConnection();
    private PreparedStatement ps;

    @Override
    public boolean insertMyClass(final MyClass myClass) {

        String sql = "INSERT INTO classes (professor_id, discipline_id, classroom_id,week_day, start_time, finish_time, semester) values (?,?, ?, ?, ?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setInt(1, myClass.getProfessor().getId());
            ps.setInt(2, myClass.getDiscipline().getId());
            ps.setInt(3, myClass.getClassroom().getId());
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

        String sql = "UPDATE classes SET professor_id = ?, discipline_id = ?, classroom_id = ?, week_day = ?, start_time = ?, finish_time = ?, semester = ? WHERE id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql)) {
            ps.setInt(1, myClass.getProfessor().getId());
            ps.setInt(2, myClass.getDiscipline().getId());
            ps.setInt(3, myClass.getClassroom().getId());
            ps.setString(4, myClass.getWeeDay());
            ps.setString(5, myClass.getStartTime());
            ps.setString(6, myClass.getFinishTime());
            ps.setString(7, myClass.getSemester());
            ps.setInt(8, myClass.getId());
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
            throw new RuntimeException("Falha ao excluir turma", e);
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
    public MyClass listMyClass(final int id, final boolean onlyInactive) {

        String sql = """
                SELECT classes.id,
                    professors.name, professors.id,
                    disciplines.name, disciplines.id,
                    classrooms.name, classrooms.id,
                    week_day, start_time, finish_time, semester
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
            ResultSet rs = ps.executeQuery();
            List<MyClass> myClass = mapResultSetToEntity(rs);
            if (myClass.isEmpty()) {
                return null;
            }
            return myClass.get(0);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar turma", e);
        }
    }

    @Override
    public List<MyClass> listMyClassesByParam(final String filterValue, final boolean onlyInactive) {
        // this was requested by the university professor, even though it kills the database performance
        String sql = """
                SELECT classes.id, professors.name, disciplines.name, classrooms.name, week_day, start_time, finish_time, semester FROM classes
                    INNER JOIN professors    ON professors.id  = classes.professor_id
                    INNER JOIN disciplines   ON disciplines.id = classes.discipline_id
                    INNER JOIN classrooms    ON classrooms.id  = classes.classroom_id
                    LEFT  JOIN student_class ON classes.id     = student_class.id_class
                    LEFT  JOIN students      ON student_class.id_student = students.id
                WHERE ((
                    classes.id = ? OR professors.id = ? OR disciplines.id = ? OR classrooms.id = ? OR students.id = ?
                ) OR (
                    classes.start_time LIKE ? OR classes.finish_time LIKE ? OR classes.semester        LIKE ? OR
                    professors.name    LIKE ? OR professors.email    LIKE ? OR professors.phoneNumber  LIKE ? OR
                    disciplines.name   LIKE ? OR disciplines.code    LIKE ? OR disciplines.description LIKE ? OR
                    classrooms.name    LIKE ? OR classrooms.capacity LIKE ? OR classrooms.location     LIKE ? OR
                    students.name      LIKE ? OR students.email      LIKE ? OR students.phoneNumber    LIKE ? OR
                    students.cpf       LIKE ? OR students.address    LIKE ? OR classes.week_day        LIKE ?
                ))
                    AND classes.inactive = ?
                GROUP BY classes.id, professors.name, disciplines.name, classrooms.name, semester
                """;
        try {
            int cont = 0;
            ps = Objects.requireNonNull(con).prepareStatement(sql);
            while (cont < 6) {
                ps.setString(++cont, filterValue);
            }
            while (cont < 23) {
                ps.setString(++cont, "%" + filterValue + "%");
            }
            ps.setBoolean(++cont, onlyInactive);
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao filtrar turmas", e);
        }
    }

    @Override
    public List<MyClass> listAllActiveMyClasses(final String filterValue) {
        // this was requested by the university professor, even though it kills the database performance
        String sql = """
                SELECT classes.id, professors.name, disciplines.name, classrooms.name, week_day, start_time, finish_time, semester FROM classes
                    INNER JOIN professors    ON professors.id  = classes.professor_id
                    INNER JOIN disciplines   ON disciplines.id = classes.discipline_id
                    INNER JOIN classrooms    ON classrooms.id  = classes.classroom_id
                    LEFT  JOIN student_class ON classes.id     = student_class.id_class
                    LEFT  JOIN students      ON student_class.id_student = students.id
                WHERE ((
                    classes.id = ? OR professors.id = ? OR disciplines.id = ? OR classrooms.id = ? OR students.id = ?
                ) OR (
                    classes.start_time LIKE ? OR classes.finish_time LIKE ? OR classes.semester        LIKE ? OR
                    professors.name    LIKE ? OR professors.email    LIKE ? OR professors.phoneNumber  LIKE ? OR
                    disciplines.name   LIKE ? OR disciplines.code    LIKE ? OR disciplines.description LIKE ? OR
                    classrooms.name    LIKE ? OR classrooms.capacity LIKE ? OR classrooms.location     LIKE ? OR
                    students.name      LIKE ? OR students.email      LIKE ? OR students.phoneNumber    LIKE ? OR
                    students.cpf       LIKE ? OR students.address    LIKE ? OR classes.week_day        LIKE ?
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
            while (cont < 23) {
                ps.setString(++cont, "%" + filterValue + "%");
            }
            return mapResultSetToEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Falha ao listar turmas ativas", e);
        }
    }

    private List<MyClass> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        List<MyClass> myClassList = new ArrayList<>();

        while (resultSet.next()) {
            Professor professor = new Professor(resultSet.getString("professors.name"));
            Discipline discipline = new Discipline(resultSet.getString("disciplines.name"));
            Classroom classroom = new Classroom(resultSet.getString("classrooms.name"));
            if (resultSet.getMetaData().getColumnCount() == 10) {
                professor.setId(resultSet.getInt("professors.id"));
                discipline.setId(resultSet.getInt("disciplines.id"));
                classroom.setId(resultSet.getInt("classrooms.id"));
            }

            MyClass myClass = new MyClass();
            myClass.setId(resultSet.getInt("classes.id"));
            myClass.setProfessor(professor);
            myClass.setDiscipline(discipline);
            myClass.setClassroom(classroom);
            myClass.setWeeDay(resultSet.getString("week_day"));
            myClass.setStartTime(resultSet.getString("start_time"));
            myClass.setFinishTime(resultSet.getString("finish_time"));
            myClass.setSemester(resultSet.getString("semester"));
            myClassList.add(myClass);
        }
        return myClassList;
    }
}
