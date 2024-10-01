package controller.student;

import exceptions.ValidationException;
import model.student.repository.IStudentRepo;
import model.student.entity.Student;
import model.student.dto.StudentDTO;
import utils.ParseUtils;
import utils.Validation.StudentValidation;
import utils.Validation.ValidationUtils;
import view.student.IStudentView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private final IStudentView view;
    private final IStudentRepo model;
    public StudentController(IStudentView view, IStudentRepo model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        addInsertStudentButtonListener();
        addUpdateStudentButtonListener();
        addDeactivateStudentButtonListener();
        addReactivateStudentButtonListener();
        addDoneButtonListener();
        addInactivesCheckBoxListener();
        addTableListener();
        setFilterFieldListener();
        view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
    }

    private void addInsertStudentButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String name = view.getNameText();
            String cpf = view.getCpfText();
            String email = view.getEmailText();
            String phoneNumber = view.getPhoneNumberText();
            String address = view.getAddressText();

            String parsedCpf;
            String parsedPhoneN;
            try {
                parsedCpf = ParseUtils.parseCpf(cpf);
                parsedPhoneN = ParseUtils.parsePhoneNumber(phoneNumber);
                StudentValidation.validateStudentFields(null, name, parsedCpf, email, parsedPhoneN, address);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Student student = new Student(name, parsedCpf, email, parsedPhoneN, address);
            if (!model.insertStudent(student)) {
                view.showErrorMessage("Falha ao inserir aluno!");
                return;
            }

            view.clearAllFields();
            view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addUpdateStudentButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String name = view.getNameText();
            String cpf = view.getCpfText();
            String email = view.getEmailText();
            String phoneNumber = view.getPhoneNumberText();
            String address = view.getAddressText();

            int parsedID;
            String parsedCpf;
            String parsedPhoneN;
            try {
                parsedID = ParseUtils.parseId(id);
                parsedCpf = ParseUtils.parseCpf(cpf);
                parsedPhoneN = ParseUtils.parsePhoneNumber(phoneNumber);
                StudentValidation.validateStudentFields(parsedID, name, parsedCpf, email, parsedPhoneN, address);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Student student = new Student(parsedID, name, parsedCpf, email, parsedPhoneN, address);
            if (!model.updateStudent(student)) {
                view.showErrorMessage("Falha ao atualizar aluno!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addDeactivateStudentButtonListener() {
        view.addButtonDeleteActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                StudentValidation.validateStudentFields(parsedID, null, null, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.deactivateStudent(parsedID)) {
                view.showErrorMessage("Falha ao desativar aluno!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addReactivateStudentButtonListener() {
        view.addButtonReactivateActionListener(e -> {

            if (!view.confirmReactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                StudentValidation.validateStudentFields(parsedID, null, null, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.reactivateStudent(parsedID)) {
                view.showErrorMessage("Falha ao reactivar aluno!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addTableListener() {
        view.addTableStudentListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedStudentRowIndex();
                if (selectedRow >= 0) {
                    String studentId = view.getStudentIdAt(selectedRow);

                    List<Student> student = getStudent(studentId, view.isCheckBoxInactivesSelected());
                    if (student == null) {
                        return;
                    }

                    if (!view.isButtonDoneEnabled()) {
                        view.switchButtons(true);
                        view.setButtonReactivate(false);
                    }

                    if (view.isCheckBoxInactivesSelected()) {
                        view.setButtonReactivate(true);
                    }

                    view.setFieldID(studentId);
                    view.setFieldTexts(convertStudentsToDTO(student).get(0));
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> searchWithFilter());
    }

    private void setFilterFieldListener() {
        view.addFieldFilterActionListener(e -> searchWithFilter());
    }

    private void searchWithFilter() {
        String searchBoxData = view.getFilterText();
        if (searchBoxData.isEmpty()) {
            view.setTableStudentModel(getAllStudentsDTO(view.isCheckBoxInactivesSelected()));
            return;
        }
        view.setTableStudentModel(getFilteredStudentsDTO( searchBoxData, view.isCheckBoxInactivesSelected()));
    }

    private List<Student> getStudent(final String id, final boolean onlyInactive) {
        int parsedID;
        List<Student> student;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        student = mapResultSetToStudents(model.listStudent(parsedID, onlyInactive));
        if (student.isEmpty()) {
            view.showErrorMessage("Falha ao listar aluno!");
            return null;
        }
        return student;
    }

    private List<Student> getFilteredStudents(final String value, final boolean onlyInactive) {
        if ( value == null) {
            view.showErrorMessage("Filtro inválido");
        }

        List<Student> students= mapResultSetToStudents(model.listStudentsByParam( value, onlyInactive));


        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // students.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (students == null) {
            view.showErrorMessage("Falha ao listar alunos filtrados!");
        }
        return students;
    }

    private List<Student> mapResultSetToStudents(ResultSet rs) {
        List<Student> studentList = new ArrayList<>();
        try {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setCpf(rs.getString(3));
                student.setEmail(rs.getString(4));
                student.setPhoneNumber(rs.getString(5));
                student.setAddress(rs.getString(6));
                studentList.add(student);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear alunos!");
        }
        return studentList;
    }

    public IStudentView getView() {
        return view;
    }

    private List<StudentDTO> convertStudentsToDTO(List<Student> students) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (Student student : students) {
            studentDTOList.add(new StudentDTO("" + student.getId(), student.getName(), student.getCpf(), student.getEmail(), student.getPhoneNumber(), student.getAddress()));
        }
        return studentDTOList;
    }

    public List<StudentDTO> getAllStudentsDTO(final boolean onlyInactive) {
        List<Student> students = mapResultSetToStudents(model.listAllStudents(onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // students.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (students == null) {
            view.showErrorMessage("Falha ao listar alunos!");
        }
        return convertStudentsToDTO(students);
    }

    private List<StudentDTO> getFilteredStudentsDTO(final String value, final boolean onlyInactive) {
        List<Student> students = getFilteredStudents(value, onlyInactive);
        return convertStudentsToDTO(students);
    }
}

