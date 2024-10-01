package view.professor;

import model.professor.dto.ProfessorDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IProfessorView {

    void setFieldID(final String id);

    void setTableProfessorModel(final List<ProfessorDTO> professorList);

    void setButtonReactivate(final boolean b);

    void setFieldTexts(final ProfessorDTO professor);

    String getIdText();

    String getNameText();

    String getPhoneNumberText();

    String getEmailText();

    String getFilterText();

    String getProfessorIdAt(final int row);

    int getSelectedProfessorRowIndex();

    boolean isCheckBoxInactivesSelected();

    boolean isButtonDoneEnabled();

    boolean confirmDeactivation();

    boolean confirmReactivation();

    void clearAllFields();

    void showErrorMessage(final String message);

    void switchButtons(final boolean b);

    void addButtonInsertActionListener(final ActionListener actionListener);

    void addButtonDeleteActionListener(final ActionListener actionListener);

    void addButtonUpdateActionListener(final ActionListener actionListener);

    void addButtonDoneActionListener(final ActionListener actionListener);

    void addButtonReactivateActionListener(final ActionListener actionListener);

    void addCheckBoxInactivesActionListener(final ActionListener actionListener);

    void addFieldFilterActionListener(final ActionListener actionListener);

    void addTableProfessorListSelectionListener(final ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
