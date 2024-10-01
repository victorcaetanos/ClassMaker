package view.discipline;

import model.discipline.dto.DisciplineDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IDisciplineView {

    void setFieldId(final String id);

    void setTableDisciplineModel(final List<DisciplineDTO> disciplineList);

    void setButtonReactivate(final boolean b);

    void setFieldTexts(final DisciplineDTO discipline);

    String getIdText();

    String getNameText();

    String getCodeText();

    String getDescriptionText();

    String getFilterText();

    String getDisciplineIdAt(final int row);

    int getSelectedDisciplineRowIndex();

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

    void addTableDisciplineListSelectionListener(final ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
