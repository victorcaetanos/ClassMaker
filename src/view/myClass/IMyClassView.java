package view.myClass;

import model.classroom.dto.ClassroomDTO;
import model.discipline.dto.DisciplineDTO;
import model.myClass.dto.MyClassDTO;
import model.professor.dto.ProfessorDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IMyClassView {

    void setFieldID(final String id);

    void setTableMyClassModel(final List<MyClassDTO> myClassList);

    void setButtonReactivate(final boolean b);

    void setFieldTexts(MyClassDTO myClass);

    String getIdText();

    String getStartTimeText();

    String getSemesterText();

    String getFinishTimeText();

    String getFilterText();

    String getMyClassIdAt(final int row);

    int getSelectedMyClassRowIndex();

    String getComboBoxProfessorId();

    String getComboBoxDisciplineId();

    String getComboBoxClassroomId();

    boolean isCheckBoxInactivesSelected();

    boolean isButtonDoneEnabled();

    boolean confirmDeactivation();

    boolean confirmReactivation();

    void clearAllFields();

    void populateProfessorComboBox(final List<ProfessorDTO> professorList);

    void populateDisciplineComboBox(final List<DisciplineDTO> disciplineList);

    void populateClassroomComboBox(final List<ClassroomDTO> classroomList);

    void showErrorMessage(final String message);

    void switchButtons(final boolean b);

    void addButtonInsertActionListener(final ActionListener actionListener);

    void addButtonDeleteActionListener(final ActionListener actionListener);

    void addButtonUpdateActionListener(final ActionListener actionListener);

    void addButtonDoneActionListener(final ActionListener actionListener);

    void addButtonReactivateActionListener(final ActionListener actionListener);

    void addCheckBoxInactivesActionListener(final ActionListener actionListener);

    void addFieldSearchActionListener(final ActionListener actionListener);

    void addTableMyClassListSelectionListener(final ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
