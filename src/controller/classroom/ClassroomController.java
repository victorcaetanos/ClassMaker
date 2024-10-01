package controller.classroom;

import exceptions.ValidationException;
import model.classroom.entity.Classroom;
import model.classroom.repository.IClassroomRepo;
import model.classroom.dto.ClassroomDTO;
import utils.ParseUtils;
import utils.Validation.ClassroomValidation;
import utils.Validation.ValidationUtils;
import view.classroom.IClassroomView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomController {
    private final IClassroomView view;
    private final IClassroomRepo model;

    public ClassroomController(IClassroomView view, IClassroomRepo model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        addInsertClassroomButtonListener();
        addUpdateClassroomButtonListener();
        addDeactivateClassroomButtonListener();
        addReactivateClassroomButtonListener();
        addDoneButtonListener();
        addInactivesCheckBoxListener();
        addTableListener();
        setFilterFieldListener();
        view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
    }

    private void addInsertClassroomButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String name = view.getNameText();
            String capacity = view.getCapacityText();
            String location = view.getLocationText();

            int parsedCapacity;
            try {
                parsedCapacity = ParseUtils.parseCapacity(capacity);
                ClassroomValidation.validateClassroomFields(null, name, parsedCapacity, location);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Classroom classroom = new Classroom(name, parsedCapacity, location);
            if (!model.insertClassroom(classroom)) {
                view.showErrorMessage("Falha ao inserir sala!");
                return;
            }

            view.clearAllFields();
            view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addUpdateClassroomButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String name = view.getNameText();
            String capacity = view.getCapacityText();
            String location = view.getLocationText();

            int parsedID;
            int parsedCapacity;
            try {
                parsedID = ParseUtils.parseId(id);
                parsedCapacity = ParseUtils.parseCapacity(capacity);
                ClassroomValidation.validateClassroomFields(parsedID, name, parsedCapacity, location);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Classroom classroom = new Classroom(parsedID, name, parsedCapacity, location);
            if (!model.updateClassroom(classroom)) {
                view.showErrorMessage("Falha ao atualizar sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addDeactivateClassroomButtonListener() {
        view.addButtonDeleteActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                ClassroomValidation.validateClassroomFields(parsedID, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.deactivateClassroom(parsedID)) {
                view.showErrorMessage("Falha ao desativar sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addReactivateClassroomButtonListener() {
        view.addButtonReactivateActionListener(e -> {

            if (!view.confirmReactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                ClassroomValidation.validateClassroomFields(parsedID, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.reactivateClassroom(parsedID)) {
                view.showErrorMessage("Falha ao reactivar sala!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addTableListener() {
        view.addTableClassroomListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedClassroomRowIndex();
                if (selectedRow >= 0) {
                    String classroomId = view.getClassroomIdAt(selectedRow);

                    List<Classroom> classroom = getClassroom(classroomId, view.isCheckBoxInactivesSelected());
                    if (classroom == null) {
                        return;
                    }

                    if (!view.isButtonDoneEnabled()) {
                        view.switchButtons(true);
                        view.setButtonReactivate(false);
                    }

                    if (view.isCheckBoxInactivesSelected()) {
                        view.setButtonReactivate(true);
                    }

                    view.setFieldID(classroomId);
                    view.setFieldTexts(convertClassroomsToDTO(classroom).get(0));
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
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
            view.setTableClassroomModel(getAllClassroomsDTO(view.isCheckBoxInactivesSelected()));
            return;
        }
        view.setTableClassroomModel(getFilteredClassroomsDTO(searchBoxData, view.isCheckBoxInactivesSelected()));
    }

    private List<Classroom> getClassroom(final String id, final boolean onlyInactive) {
        int parsedID;
        List<Classroom> classroom;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        classroom = mapResultSetToClassrooms(model.listClassroom(parsedID, onlyInactive));
        if (classroom.isEmpty()) {
            view.showErrorMessage("Falha ao listar sala!");
            return null;
        }
        return classroom;
    }

    private List<Classroom> getFilteredClassrooms(final String value, final boolean onlyInactive) {
        if (value == null) {
            view.showErrorMessage("Filtro inválido");
        }

        List<Classroom> classrooms = mapResultSetToClassrooms(model.listClassroomsByParam(value, onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // classrooms.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (classrooms == null) {
            view.showErrorMessage("Falha ao listar salas filtradas!");
        }
        return classrooms;
    }

    private List<Classroom> mapResultSetToClassrooms(ResultSet rs) {
        List<Classroom> classroomList = new ArrayList<>();
        try {
            while (rs.next()) {
                Classroom classroom = new Classroom();
                classroom.setId(rs.getInt(1));
                classroom.setName(rs.getString(2));
                classroom.setCapacity(rs.getInt(3));
                classroom.setLocation(rs.getString(4));
                classroomList.add(classroom);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear salas!");
        }
        return classroomList;
    }

    public IClassroomView getView() {
        return view;
    }

    private List<ClassroomDTO> convertClassroomsToDTO(List<Classroom> classrooms) {
        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            classroomDTOList.add(new ClassroomDTO("" + classroom.getId(), classroom.getName(), "" + classroom.getCapacity(), classroom.getLocation()));
        }
        return classroomDTOList;
    }

    public List<ClassroomDTO> getAllClassroomsDTO(final boolean onlyInactive) {
        List<Classroom> classrooms = mapResultSetToClassrooms(model.listAllClassrooms(onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // classrooms.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (classrooms == null) {
            view.showErrorMessage("Falha ao listar salas!");
        }
        return convertClassroomsToDTO(classrooms);
    }

    private List<ClassroomDTO> getFilteredClassroomsDTO(final String value, final boolean onlyInactive) {
        List<Classroom> classrooms = getFilteredClassrooms(value, onlyInactive);
        return convertClassroomsToDTO(classrooms);
    }
}

