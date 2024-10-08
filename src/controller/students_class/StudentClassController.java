package controller.students_class;

import exceptions.ValidationException;
import exceptions.sql.DuplicateKeyEntryException;
import model.myClass.dto.MyClassDTO;
import model.myClass.entity.MyClass;
import model.student.dto.StudentDTO;
import model.student.entity.Student;
import model.student_class.entity.StudentClass;
import model.student_class.dto.StudentClassDTO;
import service.student_class.IStudentClassService;
import utils.ParseUtils;
import utils.Validation.ValidationUtils;
import view.student_class.IStudentClassView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class StudentClassController {
    private final IStudentClassView view;
    private final IStudentClassService service;

    public StudentClassController(IStudentClassView view, IStudentClassService service) {
        this.view = view;
        this.service = service;
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

        addMyClassSearchButtonListener();
        addStudentSearchButtonListener();
        addStudentMyClassSearchButtonListener();

        view.setTableMyClassModel(getAllMyClassesDTO());
        view.setTableStudentModel(getAllStudentsDTO());
    }

    private void addInsertStudentClassButtonListener() {
        view.addInsertButtonActionListener(e -> {
            String idStudent = view.getStudentIdAt(view.getSelectedStudentRowIndex());
            String idClass = view.getMyClassIdAt(view.getSelectedMyClassRowIndex());

            try {
                if (!service.insertStudentClass(idStudent, idClass)) {
                    view.showErrorMessage("Falha ao inserir aluno em turma!");
                    return;
                }
            } catch (ValidationException | DuplicateKeyEntryException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.setTableStudentMyClassModel(getAllStudentMyClassesDTO());
        });
    }

    private void addDeactivateStudentClassButtonListener() {
        view.addDeleteButtonActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            List<String> ids = view.getStudentClassIdsAt(view.getSelectedStudentClassRowIndex());

            try {
                if (!service.deleteStudentClass(ids.get(0), ids.get(1))) {
                    view.showErrorMessage("Falha ao excluir aluno em turma!");
                    return;
                }
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.setEnableThirdTableButtons(false);
            view.setTableStudentMyClassModel(getAllStudentMyClassesDTO());
        });
    }

    private void addReactivateStudentClassButtonListener() {
        view.addReactivateButtonActionListener(e -> {
            if (!view.confirmReactivation()) {
                return;
            }

            List<String> ids = view.getStudentClassIdsAt(view.getSelectedStudentClassRowIndex());

            try {
                if (!service.activateStudentClass(ids.get(0), ids.get(1))) {
                    view.showErrorMessage("Falha ao ativar aluno em turma!");
                    return;
                }
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            view.setEnableThirdTableButtons(false);
            view.setTableStudentMyClassModel(getAllStudentMyClassesDTO());
        });
    }

    private void addTableMyClassListener() {
        view.addTableMyClassListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedMyClassRowIndex();
                if (selectedRow >= 0) {
                    view.setEnableFirstTableButtons(true);
                    view.setTableStudentMyClassModel(getAllStudentMyClassesDTO());
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

    private List<MyClassDTO> getAllMyClassesDTO() {
        return mapEntitySetToMyClassesDTO(service.listAllActiveMyClasses(view.getMyClassSearchFieldText()));
    }

    private List<MyClassDTO> mapEntitySetToMyClassesDTO(List<MyClass> myClassList) {
        List<MyClassDTO> myClassListDTOList = new ArrayList<>();

        for (MyClass myClass : myClassList) {
            MyClassDTO myClassDTO = new MyClassDTO();
            myClassDTO.setId(myClass.getId() + "");
            myClassDTO.setProfessorName(myClass.getProfessor().getName());
            myClassDTO.setDisciplineName(myClass.getDiscipline().getName());
            myClassDTO.setClassroomName(myClass.getClassroom().getName());
            myClassDTO.setSemester(myClass.getSemester());
            myClassListDTOList.add(myClassDTO);
        }
        return myClassListDTOList;
    }

    private List<StudentDTO> getAllStudentsDTO() {
        return mapEntityToStudentsDTO(service.listAllActiveStudents(view.getStudentSearchFieldText()));
    }

    private List<StudentDTO> mapEntityToStudentsDTO(List<Student> studentList) {
        List<StudentDTO> studentDTOList = new ArrayList<>();

        for (Student student : studentList) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId() + "");
            studentDTO.setName(student.getName());
            studentDTO.setCpf(student.getCpf());
            studentDTO.setEmail(student.getEmail());
            studentDTO.setPhoneNumber(student.getPhoneNumber());
            studentDTO.setAddress(student.getAddress());
            studentDTOList.add(studentDTO);
        }
        return studentDTOList;
    }

    private List<StudentClassDTO> getAllStudentMyClassesDTO() {
        List<StudentClassDTO> studentClass;

        try {
            studentClass = mapEntityToStudentClassDTO(
                    service.listStudentClass(
                            view.getMyClassIdAt(view.getSelectedMyClassRowIndex()),
                            view.getStudentClassSearchFieldText(),
                            view.isCheckBoxInactivesSelected()
                    )
            );
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }
        return studentClass;
    }

    private List<StudentClassDTO> mapEntityToStudentClassDTO(List<StudentClass> studentClassList) {
        List<StudentClassDTO> studentClassDTOList = new ArrayList<>();

        for (StudentClass studentClass : studentClassList) {
            StudentClassDTO studentClassDTO = new StudentClassDTO();
            studentClassDTO.setDisciplineCode(studentClass.getMyClass().getDiscipline().getCode());
            studentClassDTO.setDisciplineName(studentClass.getMyClass().getDiscipline().getName());
            studentClassDTO.setClassSemester(studentClass.getMyClass().getSemester());
            studentClassDTO.setStudentName(studentClass.getStudent().getName());
            studentClassDTO.setStudentEmail(studentClass.getStudent().getEmail());
            studentClassDTO.setStudentId(studentClass.getStudent().getId() + "");
            studentClassDTO.setClassId(studentClass.getMyClass().getId() + "");
            studentClassDTOList.add(studentClassDTO);
        }
        return studentClassDTOList;
    }

    // Methods to filter JTables

    private void addMyClassSearchFieldListener() {
        view.addMyClassSearchFieldActionListener(e -> view.setTableMyClassModel(getAllMyClassesDTO()));
    }

    private void addStudentSearchFieldListener() {
        view.addStudentSearchFieldActionListener(e -> view.setTableStudentModel(getAllStudentsDTO()));
    }

    private void addStudentClassSearchFieldListener() {
        view.addStudentClassSearchFieldActionListener(e -> view.setTableStudentMyClassModel(getAllStudentMyClassesDTO()));
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> view.setTableStudentMyClassModel(getAllStudentMyClassesDTO()));
    }

    private void addMyClassSearchButtonListener() {
        view.addMyClassSearchButtonActionListener(e -> view.setTableMyClassModel(getAllMyClassesDTO()));
    }

    private void addStudentSearchButtonListener() {
        view.addStudentSearchButtonActionListener(e -> view.setTableStudentModel(getAllStudentsDTO()));
    }

    private void addStudentMyClassSearchButtonListener() {
        view.addStudentClassSearchButtonActionListener(e -> view.setTableStudentMyClassModel(getAllStudentMyClassesDTO()));
    }
}

