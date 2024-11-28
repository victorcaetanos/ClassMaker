package view.professor;

import model.professor.dto.ProfessorDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IProfessorView {

    void setFieldID(String id);

    void setTableProfessorModel(List<ProfessorDTO> professorList);

    void setButtonReactivate(boolean b);

    void setFieldTexts(ProfessorDTO professor);

    String getIdText();

    String getNameText();

    String getPhoneNumberText();
    String getCpfText();
    String getTitleText();

    String getEmailText();

    String getFilterText();

    String getProfessorIdAt(int row);

    int getSelectedProfessorRowIndex();

    boolean isCheckBoxInactivesSelected();

    boolean isButtonDoneEnabled();

    boolean confirmDeactivation();

    boolean confirmReactivation();

    void clearAllFields();

    void showErrorMessage(String message);

    void switchButtons(boolean b);

    void addButtonInsertActionListener(ActionListener actionListener);

    void addButtonDeleteActionListener(ActionListener actionListener);

    void addButtonUpdateActionListener(ActionListener actionListener);

    void addButtonDoneActionListener(ActionListener actionListener);

    void addButtonReactivateActionListener(ActionListener actionListener);

    void addCheckBoxInactivesActionListener(ActionListener actionListener);

    void addSearchFieldActionListener(ActionListener actionListener);

    void addSearchButtonActionListener(ActionListener actionListener);

    void addTableProfessorListSelectionListener(ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
