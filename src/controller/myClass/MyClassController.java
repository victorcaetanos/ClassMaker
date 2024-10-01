package controller.myClass;

import exceptions.ValidationException;
import model.classroom.dto.ClassroomDTO;
import model.discipline.dto.DisciplineDTO;
import model.myClass.repository.IMyClassRepo;
import model.myClass.entity.MyClass;
import model.myClass.dto.MyClassDTO;
import model.professor.dto.ProfessorDTO;
import utils.ParseUtils;
import utils.Validation.MyClassValidation;
import utils.Validation.ValidationUtils;
import view.myClass.IMyClassView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyClassController {
    private final IMyClassView view;
    private final IMyClassRepo model;

    public MyClassController(IMyClassView view, IMyClassRepo model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        addInsertMyClassButtonListener();
        addUpdateMyClassButtonListener();
        addDeactivateMyClassButtonListener();
        addReactivateMyClassButtonListener();
        addDoneButtonListener();
        addInactivesCheckBoxListener();
        addTableListener();
        setFilterFieldListener();
        view.populateProfessorComboBox(mapResultSetToDTO(model.listAllActiveProfessors(), ProfessorDTO.class));
        view.populateDisciplineComboBox(mapResultSetToDTO(model.listAllActiveDisciplines(), DisciplineDTO.class));
        view.populateClassroomComboBox(mapResultSetToDTO(model.listAllActiveClassrooms(), ClassroomDTO.class));
        view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
    }

    private void addInsertMyClassButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String professorId = view.getComboBoxProfessorId();
            String disciplineId = view.getComboBoxDisciplineId();
            String classroomId = view.getComboBoxClassroomId();
            String startTime = view.getStartTimeText();
            String finishTime = view.getFinishTimeText();
            String semester = view.getSemesterText();

            int parsedProfessorId;
            int parsedDisciplineId;
            int parsedClassroomId;
            try {
                parsedProfessorId = ParseUtils.parseId(professorId);
                parsedDisciplineId = ParseUtils.parseId(disciplineId);
                parsedClassroomId = ParseUtils.parseId(classroomId);
                MyClassValidation.validateMyClassFields(null, parsedProfessorId, parsedDisciplineId, parsedClassroomId, startTime, finishTime, semester);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            MyClass myClass = new MyClass(parsedProfessorId, parsedDisciplineId, parsedClassroomId, startTime, finishTime, semester);
            if (!model.insertMyClass(myClass)) {
                view.showErrorMessage("Falha ao inserir turma!");
                return;
            }

            view.clearAllFields();
            view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addUpdateMyClassButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String professorId = view.getComboBoxProfessorId();
            String disciplineId = view.getComboBoxDisciplineId();
            String classroomId = view.getComboBoxClassroomId();
            String startTime = view.getStartTimeText();
            String finishTime = view.getFinishTimeText();
            String semester = view.getSemesterText();

            int parsedId;
            int parsedProfessorId;
            int parsedDisciplineId;
            int parsedClassroomId;
            try {
                parsedId = ParseUtils.parseId(id);
                parsedProfessorId = ParseUtils.parseId(professorId);
                parsedDisciplineId = ParseUtils.parseId(disciplineId);
                parsedClassroomId = ParseUtils.parseId(classroomId);
                MyClassValidation.validateMyClassFields(parsedId, parsedProfessorId, parsedDisciplineId, parsedClassroomId, startTime, finishTime, semester);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            MyClass myClass = new MyClass(parsedId, parsedProfessorId, parsedDisciplineId, parsedClassroomId, startTime, finishTime, semester);
            if (!model.updateMyClass(myClass)) {
                view.showErrorMessage("Falha ao atualizar turma!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addDeactivateMyClassButtonListener() {
        view.addButtonDeleteActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                ValidationUtils.validateId(parsedID);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.deactivateMyClass(parsedID)) {
                view.showErrorMessage("Falha ao desativar turma!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addReactivateMyClassButtonListener() {
        view.addButtonReactivateActionListener(e -> {

            if (!view.confirmReactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                ValidationUtils.validateId(parsedID);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.reactivateMyClass(parsedID)) {
                view.showErrorMessage("Falha ao reactivar turma!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addTableListener() {
        view.addTableMyClassListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedMyClassRowIndex();
                if (selectedRow >= 0) {
                    String myClassId = view.getMyClassIdAt(selectedRow);

                    List<MyClassDTO> myClass = getMyClassDTO(myClassId, view.isCheckBoxInactivesSelected());
                    if (myClass == null) {
                        return;
                    }

                    if (!view.isButtonDoneEnabled()) {
                        view.switchButtons(true);
                        view.setButtonReactivate(false);
                    }

                    if (view.isCheckBoxInactivesSelected()) {
                        view.setButtonReactivate(true);
                    }

                    view.setFieldID(myClassId);
                    view.setFieldTexts(myClass.get(0));
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> searchWithFilter());
    }

    private void setFilterFieldListener() {
        view.addFieldSearchActionListener(e -> searchWithFilter());
    }

    private void searchWithFilter() {
        String searchBoxData = view.getFilterText();
        if (searchBoxData.isEmpty()) {
            view.setTableMyClassModel(getAllMyClassesDTO(view.isCheckBoxInactivesSelected()));
            return;
        }
        view.setTableMyClassModel(getFilteredMyClassesDTO(searchBoxData, view.isCheckBoxInactivesSelected()));
    }

    private List<MyClassDTO> getMyClassDTO(final String id, final boolean onlyInactive) {
        int parsedID;
        List<MyClassDTO> myClass;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        myClass = mapResultSetToMyClassesDTOForSelectedLine(model.listMyClass(parsedID, onlyInactive));
        if (myClass.isEmpty()) {
            view.showErrorMessage("Falha ao listar turma!");
            return null;
        }
        return myClass;
    }

    private List<MyClassDTO> getFilteredMyClassesDTO(final String value, final boolean onlyInactive) {
        if (value == null) {
            view.showErrorMessage("Valor inválido");
        }

        List<MyClassDTO> myClasses = mapResultSetToMyClassesDTO(model.listMyClassesByParam(value, onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // myClasses.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (myClasses == null) {
            view.showErrorMessage("Falha ao listar myClasses filtrados!");
        }
        return myClasses;
    }

    public IMyClassView getView() {
        return view;
    }

    public List<MyClassDTO> getAllMyClassesDTO(final boolean onlyInactive) {
        List<MyClassDTO> myClasses = mapResultSetToMyClassesDTO(model.listAllMyClasses(onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // myClasses.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (myClasses == null) {
            view.showErrorMessage("Falha ao listar turmas!");
        }
        return myClasses;
    }

    private List<MyClassDTO> mapResultSetToMyClassesDTO(ResultSet rs) {
        List<MyClassDTO> myClassList = new ArrayList<>();
        try {
            while (rs.next()) {
                MyClassDTO myClass = new MyClassDTO();
                myClass.setId(rs.getString(1));
                myClass.setProfessorName(rs.getString(2));
                myClass.setDisciplineName(rs.getString(3));
                myClass.setClassroomName(rs.getString(4));
                myClass.setStartTime(rs.getString(5));
                myClass.setFinishTime(rs.getString(6));
                myClass.setSemester(rs.getString(7));
                myClassList.add(myClass);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear turmas!");
        }
        return myClassList;
    }

    private List<MyClassDTO> mapResultSetToMyClassesDTOForSelectedLine(ResultSet rs) {
        List<MyClassDTO> myClassList = new ArrayList<>();
        try {
            while (rs.next()) {
                MyClassDTO myClass = new MyClassDTO();
                myClass.setId(rs.getString(1));
                myClass.setProfessorName(rs.getString(2));
                myClass.setProfessorId(rs.getString(3));
                myClass.setDisciplineName(rs.getString(4));
                myClass.setDisciplineId(rs.getString(5));
                myClass.setClassroomName(rs.getString(6));
                myClass.setClassroomId(rs.getString(7));
                myClass.setStartTime(rs.getString(8));
                myClass.setFinishTime(rs.getString(9));
                myClass.setSemester(rs.getString(10));
                myClassList.add(myClass);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear turmas!");
        }
        return myClassList;
    }

    // I guess this would be the definition of gambiarra, but works as intended in every way
    private <T> List<T> mapResultSetToDTO(ResultSet rs, Class<T> dtoClass) {
        List<T> dtoList = new ArrayList<>();
        try {
            dtoList.add(createDefaultDTO(dtoClass));
            while (rs.next()) {
                dtoList.add(createDTOFromResultSet(rs, dtoClass));
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorMessage("Falha ao mapear DTOs!");
        }
        return dtoList;
    }

    private <T> T createDefaultDTO(Class<T> dtoClass) throws Exception {
        T dto = dtoClass.getDeclaredConstructor().newInstance();
        String displayName = getDisplayName(dtoClass);
        dtoClass.getMethod("setId", String.class).invoke(dto, "0");
        dtoClass.getMethod("setName", String.class).invoke(dto, displayName);
        return dto;
    }

    private String getDisplayName(Class<?> dtoClass) {
        if (dtoClass.equals(ProfessorDTO.class)) {
            return "Escolha um professor";
        } else if (dtoClass.equals(DisciplineDTO.class)) {
            return "Escolha uma disciplina";
        } else {
            return "Escolha uma sala";
        }
    }

    private <T> T createDTOFromResultSet(ResultSet rs, Class<T> dtoClass) throws Exception {
        T dto = dtoClass.getDeclaredConstructor().newInstance();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            String columnName = rs.getMetaData().getColumnName(i);
            String value = rs.getString(i);
            dtoClass.getMethod("set" + Character.toUpperCase(columnName.charAt(0)) + columnName.substring(1), String.class).invoke(dto, value);
        }
        return dto;
    }
}

