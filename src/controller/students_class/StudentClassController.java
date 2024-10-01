package controller.students_class;

import exceptions.ValidationException;
import model.myClass.dto.MyClassDTO;
import model.student.dto.StudentDTO;
import model.student_class.repository.IStudentClassRepo;
import model.student_class.entity.StudentClass;
import model.student_class.dto.StudentClassDTO;
import utils.ParseUtils;
import utils.Validation.StudentClassValidation;
import utils.Validation.ValidationUtils;
import view.student_class.IStudentClassView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentClassController {
    private final IStudentClassView view;
    private final IStudentClassRepo model;

    public StudentClassController(IStudentClassView view, IStudentClassRepo model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        addInsertStudentClassButtonListener();
        addDeactivateStudentClassButtonListener();
        addReactivateStudentClassButtonListener();

        addTableMyClassListener();
        addTableStudentListener();
        addTableStudentClassListener();

        addInactivesCheckBoxListener();
        addMyClassSearchFieldListener();
        addStudentSearchFieldListener();
        addStudentClassSearchFieldListener();

        view.setTableMyClassModel(getAllMyClassesDTO());
        view.setTableStudentModel(getAllStudentsDTO());
    }

    private void addInsertStudentClassButtonListener() {
        view.addInsertButtonActionListener(e -> {
            String idStudent = view.getStudentIdAt(view.getSelectedStudentRowIndex());
            String idClass = view.getMyClassIdAt(view.getSelectedMyClassRowIndex());

            int parsedIdStudent;
            int parsedIdClass;
            try {
                parsedIdStudent = ParseUtils.parseId(idStudent);
                parsedIdClass = ParseUtils.parseId(idClass);
                StudentClassValidation.validateStudentClassFields(parsedIdStudent, parsedIdClass);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            StudentClass studentClass = new StudentClass(parsedIdStudent, parsedIdClass);
            if (!model.insertStudentClass(studentClass)) {
                view.showErrorMessage("Falha ao inserir aluno em turma!");
                return;
            }
            view.setTableStudentClassModel(getAllStudentMyClassesDTO( ));
        });
    }

    private void addDeactivateStudentClassButtonListener() {
        view.addDeleteButtonActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            List<String> ids = view.getStudentClassIdsAt(view.getSelectedStudentClassRowIndex());
            int parsedIdStudent;
            int parsedIdClass;
            try {
                parsedIdStudent = ParseUtils.parseId(ids.get(0));
                parsedIdClass = ParseUtils.parseId(ids.get(1));
                StudentClassValidation.validateStudentClassFields(parsedIdStudent, parsedIdClass);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            StudentClass studentClass = new StudentClass(parsedIdStudent, parsedIdClass);
            if (!model.deactivateStudentClass(studentClass)) {
                view.showErrorMessage("Falha ao desativar aluno em turma!");
                return;
            }

            view.setEnableThirdTableButtons(false);
            view.setTableStudentClassModel(getAllStudentMyClassesDTO( ));
        });
    }

    private void addReactivateStudentClassButtonListener() {
        view.addReactivateButtonActionListener(e -> {
            if (!view.confirmReactivation()) {
                return;
            }

            List<String> ids = view.getStudentClassIdsAt(view.getSelectedStudentClassRowIndex());
            int parsedIdStudent;
            int parsedIdClass;
            try {
                parsedIdStudent = ParseUtils.parseId(ids.get(0));
                parsedIdClass = ParseUtils.parseId(ids.get(1));
                StudentClassValidation.validateStudentClassFields(parsedIdStudent, parsedIdClass);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            StudentClass studentClass = new StudentClass(parsedIdStudent, parsedIdClass);
            if (!model.reactivateStudentClass(studentClass)) {
                view.showErrorMessage("Falha ao reactivar aluno em turma!");
                return;
            }

            view.setEnableThirdTableButtons(false);
            view.setTableStudentClassModel(getAllStudentMyClassesDTO());
        });
    }

    private void addTableMyClassListener() {
        view.addTableMyClassListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedMyClassRowIndex();
                if (selectedRow >= 0) {
                    view.setEnableFirstTableButtons(true);
                    view.setTableStudentClassModel(getAllStudentMyClassesDTO( ));
                }
            }
        });
    }

    private void addTableStudentListener() {
        view.addTableStudentListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedStudentRowIndex();
                if (selectedRow >= 0) {
                    view.setEnableSecondTableButtons(true);
                }
            }
        });
    }

    private void addTableStudentClassListener() {
        view.addTableStudentClassListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedStudentClassRowIndex();
                if (selectedRow >= 0) {
                    view.setEnableThirdTableButtons(true);
                }
            }
        });
    }

    public IStudentClassView getView() {
        return view;
    }


    // methods to populate JTables
    private List<MyClassDTO> getAllMyClassesDTO() {
        return mapResultSetToMyClassesDTO(model.listAllActiveMyClasses(view.getMyClassSearchFieldText()));
    }

    private List<MyClassDTO> mapResultSetToMyClassesDTO(ResultSet resultSet) {
        List<MyClassDTO> myClassList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                MyClassDTO myClass = new MyClassDTO();
                myClass.setId(resultSet.getString(1));
                myClass.setProfessorName(resultSet.getString(2));
                myClass.setDisciplineName(resultSet.getString(3));
                myClass.setClassroomName(resultSet.getString(4));
                myClass.setSemester(resultSet.getString(5));
                myClassList.add(myClass);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear turmas!");
        }
        return myClassList;
    }

    private List<StudentDTO> getAllStudentsDTO() {
        return mapResultSetToStudentsDTO(model.listAllActiveStudents(view.getStudentSearchFieldText()));
    }

    private List<StudentDTO> mapResultSetToStudentsDTO(ResultSet resultSet) {
        List<StudentDTO> studentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                StudentDTO myClass = new StudentDTO();
                myClass.setId(resultSet.getString(1));
                myClass.setName(resultSet.getString(2));
                myClass.setEmail(resultSet.getString(3));
                myClass.setPhoneNumber(resultSet.getString(4));
                studentList.add(myClass);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear alunos!");
        }
        return studentList;
    }

    private List<StudentClassDTO> getAllStudentMyClassesDTO() {
        int parsedID;

        try {
            parsedID = ParseUtils.parseId(view.getMyClassIdAt(view.getSelectedMyClassRowIndex()));
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        List<StudentClassDTO> studentClass = mapResultSetToStudentClassDTO(
                model.listStudentClass(
                        parsedID,
                        view.getStudentClassSearchFieldText(),
                        view.isCheckBoxInactivesSelected()
                )
        );
        if (studentClass == null) {
            view.showErrorMessage("Falha ao listar alunos da turma selecionada!");
            return null;
        }
        return studentClass;
    }

    private List<StudentClassDTO> mapResultSetToStudentClassDTO(ResultSet rs) {
        List<StudentClassDTO> studentClassList = new ArrayList<>();
        try {
            while (rs.next()) {
                StudentClassDTO studentClass = new StudentClassDTO();
                studentClass.setDisciplineCode(rs.getString(1));
                studentClass.setDisciplineName(rs.getString(2));
                studentClass.setClassSemester(rs.getString(3));
                studentClass.setStudentName(rs.getString(4));
                studentClass.setStudentEmail(rs.getString(5));
                studentClass.setStudentId(rs.getString(6));
                studentClass.setClassId(rs.getString(7));
                studentClassList.add(studentClass);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear alunos da turma selecionada!");
        }
        return studentClassList;
    }

    // Methods to filter JTables

    private void addMyClassSearchFieldListener() {
        view.addMyClassSearchFieldActionListener(e -> view.setTableMyClassModel(getAllMyClassesDTO()));
    }

    private void addStudentSearchFieldListener() {
        view.addStudentSearchFieldActionListener(e -> view.setTableStudentModel(getAllStudentsDTO()));
    }

    private void addStudentClassSearchFieldListener() {
        view.addStudentClassSearchFieldActionListener(e -> view.setTableStudentClassModel(getAllStudentMyClassesDTO()));
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> view.setTableStudentClassModel(getAllStudentMyClassesDTO()));
    }
}

