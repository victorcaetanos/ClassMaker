package view.student;

import model.student.dto.StudentDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IStudentView {

    void setFieldID(String id);

    void setTableStudentModel(List<StudentDTO> studentList);

    void setButtonReactivate(boolean b);

    void setFieldTexts(StudentDTO student);

    String getIdText();

    String getNameText();

    String getPhoneNumberText();

    String getEmailText();

    String getFilterText();

    String getStudentIdAt(int row);

    String getAddressText();

    String getCpfText();

    int getSelectedStudentRowIndex();

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

    void addFieldSearchActionListener(ActionListener actionListener);

    void addSearchButtonActionListener(ActionListener actionListener);

    void addTableStudentListSelectionListener(ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
