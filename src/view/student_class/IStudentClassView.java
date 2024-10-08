package view.student_class;

import model.myClass.dto.MyClassDTO;
import model.student.dto.StudentDTO;
import model.student_class.dto.StudentClassDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IStudentClassView {

    void setTableMyClassModel(List<MyClassDTO> myClassList);

    void setTableStudentModel(List<StudentDTO> studentList);

    void setTableStudentMyClassModel(List<StudentClassDTO> studentClassList);

    String getMyClassIdAt(int row);

    String getStudentIdAt(int row);

    List<String> getStudentClassIdsAt(int row);

    void setButtonReactivate(boolean b);

    String getMyClassSearchFieldText();

    String getStudentSearchFieldText();

    String getStudentClassSearchFieldText();

    int getSelectedMyClassRowIndex();

    int getSelectedStudentRowIndex();

    int getSelectedStudentClassRowIndex();

    boolean isCheckBoxInactivesSelected();

    boolean confirmDeactivation();

    boolean confirmReactivation();

    void showErrorMessage(String message);

    void setEnableFirstTableButtons(boolean b);

    void setEnableSecondTableButtons(boolean b);

    void setEnableThirdTableButtons(boolean b);

    void addInsertButtonActionListener(ActionListener actionListener);

    void addDeleteButtonActionListener(ActionListener actionListener);

    void addReactivateButtonActionListener(ActionListener actionListener);

    void addCheckBoxInactivesActionListener(ActionListener actionListener);

    void addMyClassSearchFieldActionListener(ActionListener actionListener);

    void addStudentSearchFieldActionListener(ActionListener actionListener);

    void addStudentClassSearchFieldActionListener(ActionListener actionListener);

    void addMyClassSearchButtonActionListener(ActionListener actionListener);

    void addStudentSearchButtonActionListener(ActionListener actionListener);

    void addStudentClassSearchButtonActionListener(ActionListener actionListener);

    void addTableMyClassListSelectionListener(ListSelectionListener listSelectionListener);

    void addTableStudentListSelectionListener(ListSelectionListener listSelectionListener);

    void addTableStudentClassListSelectionListener(ListSelectionListener listSelectionListener);

    int getColumnIndex(Class<?> aClass, String columnName);
}
