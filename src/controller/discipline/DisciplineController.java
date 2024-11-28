package controller.discipline;

import exceptions.ValidationException;
import model.discipline.entity.Discipline;
import model.discipline.repository.IDisciplineRepository;
import model.discipline.dto.DisciplineDTO;
import utils.ParseUtils;
import utils.Validation.DisciplineValidation;
import utils.Validation.ValidationUtils;
import view.discipline.IDisciplineView;

import java.util.ArrayList;
import java.util.List;

public class DisciplineController {
    private final IDisciplineView view;
    private final IDisciplineRepository repository;

    public DisciplineController(IDisciplineView view, IDisciplineRepository repository) {
        this.view = view;
        this.repository = repository;
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
        setSearchFieldListener();
        setSearchButtonListener();
        view.setTableDisciplineModel(getAllDisciplinesDTOs());
    }

    private void addInsertDisciplineButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String name = view.getNameText();
            String code = view.getCodeText();
            String description = view.getDescriptionText();
            String periodo = view.getPeriodoText();

            try {
                DisciplineValidation.validateDisciplineFields(null, name, code, description, periodo);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Discipline discipline = new Discipline(name, code, description, periodo);
            if (!repository.insertDiscipline(discipline)) {
                view.showErrorMessage("Falha ao inserir sala!");
                return;
            }

            view.clearAllFields();
            view.setTableDisciplineModel(getAllDisciplinesDTOs());
        });
    }

    private void addUpdateDisciplineButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String name = view.getNameText();
            String code = view.getCodeText();
            String description = view.getDescriptionText();
            String periodo = view.getPeriodoText();

            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                DisciplineValidation.validateDisciplineFields(parsedID, name, code, description, periodo);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Discipline discipline = new Discipline(parsedID, name, code, description, periodo);
            if (!repository.updateDiscipline(discipline)) {
                view.showErrorMessage("Falha ao atualizar sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableDisciplineModel(getAllDisciplinesDTOs());
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
                DisciplineValidation.validateDisciplineFields(parsedID, null, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!repository.deactivateDiscipline(parsedID)) {
                view.showErrorMessage("Falha ao excluir sala!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableDisciplineModel(getAllDisciplinesDTOs());
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
                DisciplineValidation.validateDisciplineFields(parsedID, null, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!repository.reactivateDiscipline(parsedID)) {
                view.showErrorMessage("Falha ao reactivar sala!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableDisciplineModel(getAllDisciplinesDTOs());
        });
    }

    private void addTableListener() {
        view.addTableDisciplineListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedDisciplineRowIndex();
                if (selectedRow >= 0) {
                    String disciplineId = view.getDisciplineIdAt(selectedRow);

                    DisciplineDTO discipline = getDiscipline(disciplineId, view.isCheckBoxInactivesSelected());
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
                    view.setFieldTexts(discipline);
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableDisciplineModel(getAllDisciplinesDTOs());
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
        view.setTableDisciplineModel(getAllDisciplinesDTOs());
    }

    private DisciplineDTO getDiscipline(final String id, final boolean onlyInactive) {
        int parsedID;
        DisciplineDTO discipline;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        discipline = mapEntityToDTO(repository.listDiscipline(parsedID, onlyInactive));
        if (discipline == null) {
            view.showErrorMessage("Falha ao listar sala!");
            return null;
        }
        return discipline;
    }

    public List<DisciplineDTO> getAllDisciplinesDTOs() {
        boolean onlyInactive = view.isCheckBoxInactivesSelected();
        String searchText = view.getFilterText();

        List<DisciplineDTO> disciplineList = mapEntityToDTO(repository.listDisciplinesByParam(searchText, onlyInactive));

        if (disciplineList == null) {
            view.showErrorMessage("Falha ao listar salas!");
        }
        return disciplineList;
    }

    private DisciplineDTO mapEntityToDTO(Discipline discipline) {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setId(discipline.getId() + "");
        disciplineDTO.setName(discipline.getName());
        disciplineDTO.setCode(discipline.getCode());
        disciplineDTO.setDescription(discipline.getDescription());
        disciplineDTO.setPeriodo(discipline.getPeriodo());
        return disciplineDTO;
    }

    private List<DisciplineDTO> mapEntityToDTO(List<Discipline> disciplineList) {
        List<DisciplineDTO> disciplineDTOList = new ArrayList<>();

        for (Discipline discipline : disciplineList) {
            disciplineDTOList.add(mapEntityToDTO(discipline));
        }

        return disciplineDTOList;
    }

    public IDisciplineView getView() {
        return view;
    }
}

