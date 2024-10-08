package view.classroom;

import model.classroom.dto.ClassroomDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IClassroomView {

    void setFieldID(String id);

    void setTableClassroomModel(List<ClassroomDTO> classroomList);

    void setButtonReactivate(boolean b);

    void setFieldTexts(ClassroomDTO classroom);

    String getIdText();

    String getNameText();

    String getCapacityText();

    String getLocationText();

    String getFilterText();

    String getClassroomIdAt(int row);

    int getSelectedClassroomRowIndex();

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

    void addTableClassroomListSelectionListener(ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
