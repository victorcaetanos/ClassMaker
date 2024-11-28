package view.discipline;

import model.discipline.dto.DisciplineDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IDisciplineView {

    void setFieldId(String id);

    void setTableDisciplineModel(List<DisciplineDTO> disciplineList);

    void setButtonReactivate(boolean b);

    void setFieldTexts(DisciplineDTO discipline);

    String getIdText();

    String getNameText();

    String getCodeText();

    String getDescriptionText();

    String getPeriodoText();

    String getFilterText();

    String getDisciplineIdAt(int row);

    int getSelectedDisciplineRowIndex();

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

    void addTableDisciplineListSelectionListener(ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
