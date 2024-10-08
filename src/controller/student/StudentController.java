package controller.student;

import exceptions.ValidationException;
import model.student.repository.IStudentRepository;
import model.student.entity.Student;
import model.student.dto.StudentDTO;
import utils.ParseUtils;
import utils.Validation.StudentValidation;
import utils.Validation.ValidationUtils;
import view.student.IStudentView;

import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private final IStudentView view;
    private final IStudentRepository repository;

    public StudentController(IStudentView view, IStudentRepository repository) {
        this.view = view;
        this.repository = repository;
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
        setSearchFieldListener();
        setSearchButtonListener();
        view.setTableStudentModel(getAllStudentsDTO());
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
            if (!repository.insertStudent(student)) {
                view.showErrorMessage("Falha ao inserir aluno!");
                return;
            }

            view.clearAllFields();
            view.setTableStudentModel(getAllStudentsDTO());
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
            if (!repository.updateStudent(student)) {
                view.showErrorMessage("Falha ao atualizar aluno!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableStudentModel(getAllStudentsDTO());
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

            if (!repository.deactivateStudent(parsedID)) {
                view.showErrorMessage("Falha ao excluir aluno!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableStudentModel(getAllStudentsDTO());
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

            if (!repository.reactivateStudent(parsedID)) {
                view.showErrorMessage("Falha ao reactivar aluno!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableStudentModel(getAllStudentsDTO());
        });
    }

    private void addTableListener() {
        view.addTableStudentListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedStudentRowIndex();
                if (selectedRow >= 0) {
                    String studentId = view.getStudentIdAt(selectedRow);

                    StudentDTO student = getStudent(studentId);
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
                    view.setFieldTexts(student);
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableStudentModel(getAllStudentsDTO());
        });
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> searchWithFilter());
    }

    private void setSearchFieldListener() {
        view.addFieldSearchActionListener(e -> searchWithFilter());
    }

    private void setSearchButtonListener() {
        view.addSearchButtonActionListener(e -> searchWithFilter());
    }

    private void searchWithFilter() {
        String searchBoxData = view.getFilterText();
        if (searchBoxData.isEmpty()) {
            view.setTableStudentModel(getAllStudentsDTO());
            return;
        }
        view.setTableStudentModel(getAllStudentsDTO());
    }

    private StudentDTO getStudent(final String id) {
        int parsedID;
        StudentDTO student;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        student = mapEntityToDTO(repository.listStudent(parsedID, view.isCheckBoxInactivesSelected()));
        if (student == null) {
            view.showErrorMessage("Falha ao listar aluno!");
            return null;
        }
        return student;
    }

    public List<StudentDTO> getAllStudentsDTO() {
        boolean onlyInactive = view.isCheckBoxInactivesSelected();
        String searchText = view.getFilterText();

        List<StudentDTO> students = mapEntityToDTO(repository.listStudentsByParam(searchText, onlyInactive));

        if (students == null) {
            view.showErrorMessage("Falha ao listar alunos!");
        }
        return students;
    }

    private StudentDTO mapEntityToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId() + "");
        studentDTO.setName(student.getName());
        studentDTO.setCpf(student.getCpf());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setAddress(student.getAddress());
        return studentDTO;
    }

    private List<StudentDTO> mapEntityToDTO(List<Student> studentList) {
        List<StudentDTO> studentDTOList = new ArrayList<>();

        for (Student student : studentList) {
            studentDTOList.add(mapEntityToDTO(student));
        }
        return studentDTOList;
    }

    public IStudentView getView() {
        return view;
    }
}

