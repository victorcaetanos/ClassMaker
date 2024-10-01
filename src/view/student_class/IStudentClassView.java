package view.student_class;

import model.myClass.dto.MyClassDTO;
import model.student.dto.StudentDTO;
import model.student_class.dto.StudentClassDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IStudentClassView {

    void setTableMyClassModel(final List<MyClassDTO> myClassList);

    void setTableStudentModel(final List<StudentDTO> studentList);

    void setTableStudentClassModel(final List<StudentClassDTO> studentClassList);

    String getMyClassIdAt(final int row);

    String getStudentIdAt(final int row);

    List<String> getStudentClassIdsAt(final int row);

    void setButtonReactivate(final boolean b);

    String getMyClassSearchFieldText();

    String getStudentSearchFieldText();

    String getStudentClassSearchFieldText();

    int getSelectedMyClassRowIndex();

    int getSelectedStudentRowIndex();

    int getSelectedStudentClassRowIndex();

    boolean isCheckBoxInactivesSelected();

    boolean confirmDeactivation();

    boolean confirmReactivation();

    void showErrorMessage(final String message);

    void setEnableFirstTableButtons(final boolean b);

    void setEnableSecondTableButtons(final boolean b);

    void setEnableThirdTableButtons(final boolean b);

    void addInsertButtonActionListener(final ActionListener actionListener);

    void addDeleteButtonActionListener(final ActionListener actionListener);

    void addReactivateButtonActionListener(final ActionListener actionListener);

    void addCheckBoxInactivesActionListener(final ActionListener actionListener);

    void addMyClassSearchFieldActionListener(final ActionListener actionListener);

    void addStudentSearchFieldActionListener(final ActionListener actionListener);

    void addStudentClassSearchFieldActionListener(final ActionListener actionListener);

    void addTableMyClassListSelectionListener(final ListSelectionListener listSelectionListener);

    void addTableStudentListSelectionListener(final ListSelectionListener listSelectionListener);

    void addTableStudentClassListSelectionListener(final ListSelectionListener listSelectionListener);

    int getColumnIndex(Class<?> aClass, String columnName);
}
