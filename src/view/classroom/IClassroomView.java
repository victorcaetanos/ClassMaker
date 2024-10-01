package view.classroom;

import model.classroom.dto.ClassroomDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IClassroomView {

    void setFieldID(final String id);

    void setTableClassroomModel(final List<ClassroomDTO> classroomList);

    void setButtonReactivate(final boolean b);

    void setFieldTexts(final ClassroomDTO classroom);

    String getIdText();

    String getNameText();

    String getCapacityText();

    String getLocationText();

    String getFilterText();

    String getClassroomIdAt(final int row);

    int getSelectedClassroomRowIndex();

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

    void addTableClassroomListSelectionListener(final ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
