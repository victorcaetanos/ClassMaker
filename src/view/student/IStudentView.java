package view.student;

import model.student.dto.StudentDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IStudentView {

    void setFieldID(final String id);

    void setTableStudentModel(final List<StudentDTO> studentList);

    void setButtonReactivate(final boolean b);

    void setFieldTexts(final StudentDTO student);

    String getIdText();

    String getNameText();

    String getPhoneNumberText();

    String getEmailText();

    String getFilterText();

    String getStudentIdAt(final int row);

    String getAddressText();

    String getCpfText();

    int getSelectedStudentRowIndex();

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

    void addTableStudentListSelectionListener(final ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
