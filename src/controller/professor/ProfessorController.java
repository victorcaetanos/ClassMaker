package controller.professor;

import exceptions.ValidationException;
import model.professor.repository.IProfessorRepo;
import model.professor.entity.Professor;
import model.professor.dto.ProfessorDTO;
import utils.ParseUtils;
import utils.Validation.ProfessorValidation;
import utils.Validation.ValidationUtils;
import view.professor.IProfessorView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorController {
    private final IProfessorView view;
    private final IProfessorRepo model;

    public ProfessorController(IProfessorView view, IProfessorRepo model) {
        this.view = view;
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        addInsertProfessorButtonListener();
        addUpdateProfessorButtonListener();
        addDeactivateProfessorButtonListener();
        addReactivateProfessorButtonListener();
        addDoneButtonListener();
        addInactivesCheckBoxListener();
        addTableListener();
        setFilterFieldListener();
        view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
    }

    private void addInsertProfessorButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String name = view.getNameText();
            String phoneNumber = view.getPhoneNumberText();
            String email = view.getEmailText();

            String parsedPhoneN;
            try {
                parsedPhoneN = ParseUtils.parsePhoneNumber(phoneNumber);
                ProfessorValidation.validateProfessorFields(null, name, email, parsedPhoneN);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Professor professor = new Professor(name, email, phoneNumber);
            if (!model.insertProfessor(professor)) {
                view.showErrorMessage("Falha ao inserir professor!");
                return;
            }

            view.clearAllFields();
            view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addUpdateProfessorButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String name = view.getNameText();
            String phoneNumber = view.getPhoneNumberText();
            String email = view.getEmailText();

            int parsedID;
            String parsedPhoneN;
            try {
                parsedID = ParseUtils.parseId(id);
                parsedPhoneN = ParseUtils.parsePhoneNumber(phoneNumber);
                ProfessorValidation.validateProfessorFields(parsedID, name, email, parsedPhoneN);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Professor professor = new Professor(parsedID, name, email, parsedPhoneN);
            if (!model.updateProfessor(professor)) {
                view.showErrorMessage("Falha ao atualizar professor!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addDeactivateProfessorButtonListener() {
        view.addButtonDeleteActionListener(e -> {
            if (!view.confirmDeactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                ProfessorValidation.validateProfessorFields(parsedID, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.deactivateProfessor(parsedID)) {
                view.showErrorMessage("Falha ao desativar professor!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addReactivateProfessorButtonListener() {
        view.addButtonReactivateActionListener(e -> {

            if (!view.confirmReactivation()) {
                return;
            }

            String id = view.getIdText();
            int parsedID;
            try {
                parsedID = ParseUtils.parseId(id);
                ProfessorValidation.validateProfessorFields(parsedID, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!model.reactivateProfessor(parsedID)) {
                view.showErrorMessage("Falha ao reactivar professor!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
        });
    }

    private void addTableListener() {
        view.addTableProfessorListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedProfessorRowIndex();
                if (selectedRow >= 0) {
                    String professorId = view.getProfessorIdAt(selectedRow);

                    List<Professor> professor = getProfessor(professorId, view.isCheckBoxInactivesSelected());
                    if (professor == null) {
                        return;
                    }

                    if (!view.isButtonDoneEnabled()) {
                        view.switchButtons(true);
                        view.setButtonReactivate(false);
                    }

                    if (view.isCheckBoxInactivesSelected()) {
                        view.setButtonReactivate(true);
                    }

                    view.setFieldID(professorId);
                    view.setFieldTexts(convertProfessorsToDTO(professor).get(0));
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
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
            view.setTableProfessorModel(getAllProfessorsDTO(view.isCheckBoxInactivesSelected()));
            return;
        }
        view.setTableProfessorModel(getFilteredProfessorsDTO(searchBoxData, view.isCheckBoxInactivesSelected()));
    }

    private List<Professor> getProfessor(final String id, final boolean onlyInactive) {
        int parsedID;
        List<Professor> professor;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        professor = mapResultSetToProfessors(model.listProfessor(parsedID, onlyInactive));
        if (professor.isEmpty()) {
            view.showErrorMessage("Falha ao listar professor!");
            return null;
        }
        return professor;
    }

    private List<Professor> getFilteredProfessors(final String value, final boolean onlyInactive) {
        if (value == null) {
            view.showErrorMessage("Filtro inválido");
        }

        List<Professor> professors = mapResultSetToProfessors(model.listProfessorsByParam(value, onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // professors.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (professors == null) {
            view.showErrorMessage("Falha ao listar professores filtrados!");
        }
        return professors;
    }

    private List<Professor> mapResultSetToProfessors(ResultSet rs) {
        List<Professor> professorList = new ArrayList<>();
        try {
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt(1));
                professor.setName(rs.getString(2));
                professor.setEmail(rs.getString(3));
                professor.setPhoneNumber(rs.getString(4));
                professorList.add(professor);
            }
        } catch (SQLException e) {
            view.showErrorMessage("Falha ao mapear professores!");
        }
        return professorList;
    }

    public IProfessorView getView() {
        return view;
    }

    private List<ProfessorDTO> convertProfessorsToDTO(List<Professor> professors) {
        List<ProfessorDTO> professorDTOList = new ArrayList<>();
        for (Professor professor : professors) {
            professorDTOList.add(new ProfessorDTO("" + professor.getId(), professor.getName(), professor.getEmail(), professor.getPhoneNumber()));
        }
        return professorDTOList;
    }

    public List<ProfessorDTO> getAllProfessorsDTO(final boolean onlyInactive) {
        List<Professor> professors = mapResultSetToProfessors(model.listAllProfessors(onlyInactive));

        // NOTE: will never happen because the list may be empty but not null, and it's not a bug
        // professors.isEmpty() is a valid return because if there are no results for the query, the list will be empty
        // I left the check just because in future implementations it may be necessary
        if (professors == null) {
            view.showErrorMessage("Falha ao listar professores!");
        }
        return convertProfessorsToDTO(professors);
    }

    private List<ProfessorDTO> getFilteredProfessorsDTO(final String value, final boolean onlyInactive) {
        List<Professor> professors = getFilteredProfessors(value, onlyInactive);
        return convertProfessorsToDTO(professors);
    }
}

