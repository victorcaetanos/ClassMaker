package controller.professor;

import exceptions.ValidationException;
import model.professor.repository.IProfessorRepository;
import model.professor.entity.Professor;
import model.professor.dto.ProfessorDTO;
import utils.ParseUtils;
import utils.Validation.ProfessorValidation;
import utils.Validation.ValidationUtils;
import view.professor.IProfessorView;

import java.util.ArrayList;
import java.util.List;

public class ProfessorController {
    private final IProfessorView view;
    private final IProfessorRepository repository;

    public ProfessorController(IProfessorView view, IProfessorRepository repository) {
        this.view = view;
        this.repository = repository;
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
        setSearchFieldListener();
        setSearchButtonListener();
        view.setTableProfessorModel(getAllProfessorsDTOs());
    }

    private void addInsertProfessorButtonListener() {
        view.addButtonInsertActionListener(e -> {
            String name = view.getNameText();
            String phoneNumber = view.getPhoneNumberText();
            String cpf = view.getCpfText();
            String title = view.getTitleText();
            String email = view.getEmailText();

            String parsedPhoneN;
            String parsedCpf;
            try {
                parsedPhoneN = ParseUtils.parsePhoneNumber(phoneNumber);
                parsedCpf = ParseUtils.parseCpf(cpf);
                ProfessorValidation.validateProfessorFields(null, name, email, parsedPhoneN,parsedCpf,title);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Professor professor = new Professor(name, email, parsedPhoneN, parsedCpf,title);
            if (!repository.insertProfessor(professor)) {
                view.showErrorMessage("Falha ao inserir professor!");
                return;
            }

            view.clearAllFields();
            view.setTableProfessorModel(getAllProfessorsDTOs());
        });
    }

    private void addUpdateProfessorButtonListener() {
        view.addButtonUpdateActionListener(e -> {
            String id = view.getIdText();
            String name = view.getNameText();
            String phoneNumber = view.getPhoneNumberText();
            String cpf = view.getCpfText();
            String title = view.getTitleText();
            String email = view.getEmailText();

            int parsedID;
            String parsedPhoneN;
            String parsedCpf;
            try {
                parsedID = ParseUtils.parseId(id);
                parsedPhoneN = ParseUtils.parsePhoneNumber(phoneNumber);
                parsedCpf = ParseUtils.parseCpf(cpf);
                ProfessorValidation.validateProfessorFields(parsedID, name, email, parsedPhoneN,parsedCpf, title);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            Professor professor = new Professor(parsedID, name, email, parsedPhoneN, parsedCpf, title);
            if (!repository.updateProfessor(professor)) {
                view.showErrorMessage("Falha ao atualizar professor!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableProfessorModel(getAllProfessorsDTOs());
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
                ProfessorValidation.validateProfessorFields(parsedID, null, null, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!repository.deactivateProfessor(parsedID)) {
                view.showErrorMessage("Falha ao excluir professor!");
                return;
            }

            view.switchButtons(false);
            view.clearAllFields();
            view.setTableProfessorModel(getAllProfessorsDTOs());
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
                ProfessorValidation.validateProfessorFields(parsedID, null, null, null, null, null);
            } catch (ValidationException error) {
                view.showErrorMessage(error.getMessage());
                return;
            }

            if (!repository.reactivateProfessor(parsedID)) {
                view.showErrorMessage("Falha ao reactivar professor!");
                return;
            }

            view.clearAllFields();
            view.switchButtons(false);
            view.setButtonReactivate(false);
            view.setTableProfessorModel(getAllProfessorsDTOs());
        });
    }

    private void addTableListener() {
        view.addTableProfessorListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getSelectedProfessorRowIndex();
                if (selectedRow >= 0) {
                    String professorId = view.getProfessorIdAt(selectedRow);

                    ProfessorDTO professor = getProfessor(professorId, view.isCheckBoxInactivesSelected());
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
                    view.setFieldTexts(professor);
                }
            }
        });
    }

    private void addDoneButtonListener() {
        view.addButtonDoneActionListener(e -> {
            view.clearAllFields();
            view.switchButtons(false);
            view.setTableProfessorModel(getAllProfessorsDTOs());
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
        view.setTableProfessorModel(getAllProfessorsDTOs());
    }

    private ProfessorDTO getProfessor(final String id, final boolean onlyInactive) {
        int parsedID;
        ProfessorDTO professor;

        try {
            parsedID = ParseUtils.parseId(id);
            ValidationUtils.validateId(parsedID);
        } catch (ValidationException error) {
            view.showErrorMessage(error.getMessage());
            return null;
        }

        professor = mapEntityToDTO(repository.listProfessor(parsedID, onlyInactive));
        if (professor == null) {
            view.showErrorMessage("Falha ao listar professor!");
            return null;
        }
        return professor;
    }

    public List<ProfessorDTO> getAllProfessorsDTOs() {
        boolean onlyInactive = view.isCheckBoxInactivesSelected();
        String searchText = view.getFilterText();
        List<ProfessorDTO> professorList = mapEntityToDTO(repository.listProfessorsByParam(searchText, onlyInactive));

        if (professorList == null) {
            view.showErrorMessage("Falha ao listar professores!");
        }
        return professorList;
    }

    private ProfessorDTO mapEntityToDTO(Professor professor) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professor.getId() + "");
        professorDTO.setName(professor.getName());
        professorDTO.setEmail(professor.getEmail());
        professorDTO.setPhoneNumber(professor.getPhoneNumber());
        professorDTO.setCpf(professor.getCpf());
        professorDTO.setTitle(professor.getTitle());
        return professorDTO;
    }

    private List<ProfessorDTO> mapEntityToDTO(List<Professor> professorList) {
        List<ProfessorDTO> professorDTOList = new ArrayList<>();

        for (Professor professor : professorList) {
            professorDTOList.add(mapEntityToDTO(professor));
        }

        return professorDTOList;
    }

    public IProfessorView getView() {
        return view;
    }
}

