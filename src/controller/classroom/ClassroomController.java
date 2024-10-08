package controller.classroom;

import exceptions.ValidationException;
import model.classroom.entity.Classroom;
import model.classroom.repository.IClassroomRepository;
import model.classroom.dto.ClassroomDTO;
import utils.ParseUtils;
import utils.Validation.ClassroomValidation;
import utils.Validation.ValidationUtils;
import view.classroom.IClassroomView;

import java.util.ArrayList;
import java.util.List;

public class ClassroomController {
    private final IClassroomView view;
    private final IClassroomRepository repository;

    public ClassroomController(IClassroomView view, IClassroomRepository repository) {
        this.view = view;
        this.repository = repository;
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
        setSearchFieldListener();
        setSearchButtonListener();
        view.setTableClassroomModel(getAllClassroomDTOs());
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
            if (!repository.insertClassroom(classroom)) {
                view.showErrorMessage("Falha ao inserir sala!");
                return;
            }

            view.clearAllFields();
            view.setTableClassroomModel(getAllClassroomDTOs());
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
            if (!repository.updateClassroom(classroom)) {
                view.showErrorMessage("Falha ao atualizar sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableClassroomModel(getAllClassroomDTOs());
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

            if (!repository.deactivateClassroom(parsedID)) {
                view.showErrorMessage("Falha ao excluir sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableClassroomModel(getAllClassroomDTOs());
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

            if (!repository.reactivateClassroom(parsedID)) {
                view.showErrorMessage("Falha ao reactivar sala!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableClassroomModel(getAllClassroomDTOs());
        });
    }

    private void addTableListener() {
        view.addTableClassroomListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedClassroomRowIndex();
                if (selectedRow >= 0) {
                    String classroomId = view.getClassroomIdAt(selectedRow);

                    ClassroomDTO classroom = getClassroom(classroomId, view.isCheckBoxInactivesSelected());
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
                    view.setFieldTexts(classroom);
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableClassroomModel(getAllClassroomDTOs());
        });
    }

    private void addInactivesCheckBoxListener() {
        view.addCheckBoxInactivesActionListener(e -> searchWithFilter());
    }

    private void setSearchFieldListener() {
        view.addSearchFieldActionListener(e -> searchWithFilter());
    }

    private void setSearchButtonListener() {
        view.addSearchButtonActionListener(e -> searchWithFilter());
    }

    private void searchWithFilter() {
        view.setTableClassroomModel(getAllClassroomDTOs());
    }

    private ClassroomDTO getClassroom(final String id, final boolean onlyInactive) {
        int parsedID;
        ClassroomDTO classroom;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        classroom = mapEntityToDTO(repository.listClassroom(parsedID, onlyInactive));
        if (classroom == null) {
            view.showErrorMessage("Falha ao listar sala!");
            return null;
        }
        return classroom;
    }

    public List<ClassroomDTO> getAllClassroomDTOs() {
        boolean onlyInactive = view.isCheckBoxInactivesSelected();
        String searchText = view.getFilterText();

        List<ClassroomDTO> classroomList = mapEntityToDTO(repository.listClassroomsByParam(searchText, onlyInactive));

        if (classroomList == null) {
            view.showErrorMessage("Falha ao listar salas!");
        }
        return classroomList;
    }

    private ClassroomDTO mapEntityToDTO(Classroom classroom) {
        if (classroom == null) {
            return null;
        }
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setId(classroom.getId() + "");
        classroomDTO.setName(classroom.getName());
        classroomDTO.setCapacity(classroom.getCapacity() + "");
        classroomDTO.setLocation(classroom.getLocation());
        return classroomDTO;
    }

    private List<ClassroomDTO> mapEntityToDTO(List<Classroom> classroomList) {
        List<ClassroomDTO> classroomDTOList = new ArrayList<>();

        for (Classroom classroom : classroomList) {
            classroomDTOList.add(mapEntityToDTO(classroom));
        }
        return classroomDTOList;
    }

    public IClassroomView getView() {
        return view;
    }
}

