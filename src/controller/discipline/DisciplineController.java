package controller.discipline;

import exceptions.ValidationException;
import model.discipline.entity.Discipline;
import model.discipline.repository.IDisciplineRepo;
import model.discipline.dto.DisciplineDTO;
import utils.ParseUtils;
import utils.Validation.DisciplineValidation;
import utils.Validation.ValidationUtils;
import view.discipline.IDisciplineView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplineController {
    private final IDisciplineView view;
    private final IDisciplineRepo model;

    public DisciplineController(IDisciplineView view, IDisciplineRepo model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        addInsertDisciplineButtonListener();
        addUpdateDisciplineButtonListener();
        addDeactivateDisciplineButtonListener();
        addReactivateDisciplineButtonListener();
        addDoneButtonListener();
        addInactivesCheckBoxListener();
        addTableListener();
        setFilterFieldListener();
        view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
    }

    private void addInsertDisciplineButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String name = view.getNameText();
            String code = view.getCodeText();
            String description = view.getDescriptionText();

            try {
                DisciplineValidation.validateDisciplineFields(null, name, code, description);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Discipline discipline = new Discipline(name, code, description);
            if (!model.insertDiscipline(discipline)) {
                view.showErrorMessage("Falha ao inserir sala!");
                return;
            }

            view.clearAllFields();
            view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addUpdateDisciplineButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String name = view.getNameText();
            String code = view.getCodeText();
            String description = view.getDescriptionText();

            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                DisciplineValidation.validateDisciplineFields(parsedID, name, code, description);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Discipline discipline = new Discipline(parsedID, name, code, description);
            if (!model.updateDiscipline(discipline)) {
                view.showErrorMessage("Falha ao atualizar sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addDeactivateDisciplineButtonListener() {
        view.addButtonDeleteActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                DisciplineValidation.validateDisciplineFields(parsedID, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.deactivateDiscipline(parsedID)) {
                view.showErrorMessage("Falha ao desativar sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addReactivateDisciplineButtonListener() {
        view.addButtonReactivateActionListener(e -> {

            if (!view.confirmReactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                DisciplineValidation.validateDisciplineFields(parsedID, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.reactivateDiscipline(parsedID)) {
                view.showErrorMessage("Falha ao reactivar sala!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addTableListener() {
        view.addTableDisciplineListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedDisciplineRowIndex();
                if (selectedRow >= 0) {
                    String disciplineId = view.getDisciplineIdAt(selectedRow);

                    List<Discipline> discipline = getDiscipline(disciplineId, view.isCheckBoxInactivesSelected());
                    if (discipline == null) {
                        return;
                    }

                    if (!view.isButtonDoneEnabled()) {
                        view.switchButtons(true);
                        view.setButtonReactivate(false);
                    }

                    if (view.isCheckBoxInactivesSelected()) {
                        view.setButtonReactivate(true);
                    }

                    view.setFieldId(disciplineId);
                    view.setFieldTexts(convertDisciplinesToDTO(discipline).get(0));
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
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
            view.setTableDisciplineModel(getAllDisciplinesDTO(view.isCheckBoxInactivesSelected()));
            return;
        }
        view.setTableDisciplineModel(getFilteredDisciplinesDTO(searchBoxData, view.isCheckBoxInactivesSelected()));
    }

    private List<Discipline> getDiscipline(final String id, final boolean onlyInactive) {
        int parsedID;
        List<Discipline> discipline;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        discipline = mapResultSetToDisciplines(model.listDiscipline(parsedID, onlyInactive));
        if (discipline.isEmpty()) {
            view.showErrorMessage("Falha ao listar sala!");
            return null;
        }
        return discipline;
    }

    private List<Discipline> getFilteredDisciplines(final String value, final boolean onlyInactive) {
        if (value == null) {
            view.showErrorMessage("Filtro inválido");
        }

        List<Discipline> disciplines = mapResultSetToDisciplines(model.listDisciplinesByParam(value, onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // disciplines.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (disciplines == null) {
            view.showErrorMessage("Falha ao listar salas filtradas!");
        }
        return disciplines;
    }

    private List<Discipline> mapResultSetToDisciplines(ResultSet rs) {
        List<Discipline> disciplineList = new ArrayList<>();
        try {
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId(rs.getInt(1));
                discipline.setName(rs.getString(2));
                discipline.setCode(rs.getString(3));
                discipline.setDescription(rs.getString(4));
                disciplineList.add(discipline);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear salas!");
        }
        return disciplineList;
    }

    public IDisciplineView getView() {
        return view;
    }

    private List<DisciplineDTO> convertDisciplinesToDTO(List<Discipline> disciplines) {
        List<DisciplineDTO> disciplineDTOList = new ArrayList<>();
        for (Discipline discipline : disciplines) {
            disciplineDTOList.add(new DisciplineDTO("" + discipline.getId(), discipline.getName(), discipline.getCode(), discipline.getDescription()));
        }
        return disciplineDTOList;
    }

    public List<DisciplineDTO> getAllDisciplinesDTO(final boolean onlyInactive) {
        List<Discipline> disciplines = mapResultSetToDisciplines(model.listAllDisciplines(onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // disciplines.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (disciplines == null) {
            view.showErrorMessage("Falha ao listar salas!");
        }
        return convertDisciplinesToDTO(disciplines);
    }

    private List<DisciplineDTO> getFilteredDisciplinesDTO(final String value, final boolean onlyInactive) {
        List<Discipline> disciplines = getFilteredDisciplines(value, onlyInactive);
        return convertDisciplinesToDTO(disciplines);
    }
}

