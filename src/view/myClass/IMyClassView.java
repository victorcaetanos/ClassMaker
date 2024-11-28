package view.myClass;

import model.classroom.dto.ClassroomDTO;
import model.discipline.dto.DisciplineDTO;
import model.myClass.dto.MyClassDTO;
import model.professor.dto.ProfessorDTO;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public interface IMyClassView {

    void setFieldID(String id);

    void setTableMyClassModel(List<MyClassDTO> myClassList);

    void setButtonReactivate(boolean b);

    void setFieldTexts(MyClassDTO myClass);

    String getIdText();

    String getStartTimeText();

    String getSemesterText();
    String getWeekDayText();

    String getFinishTimeText();

    String getSearchBoxText();

    String getMyClassIdAt(int row);

    int getSelectedMyClassRowIndex();

    String getComboBoxProfessorId();

    String getComboBoxDisciplineId();

    String getComboBoxClassroomId();

    void setProfessorComboBox(ProfessorDTO professorDTO);

    void setDisciplineComboBox(DisciplineDTO disciplineDTO);

    void setClassroomComboBox(ClassroomDTO classroomDTO);

    boolean isCheckBoxInactivesSelected();

    boolean isButtonDoneEnabled();

    boolean confirmDeactivation();

    boolean confirmReactivation();

    void clearAllFields();

    void populateProfessorComboBox(List<ProfessorDTO> professorList);

    void populateDisciplineComboBox(List<DisciplineDTO> disciplineList);

    void populateClassroomComboBox(List<ClassroomDTO> classroomList);

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

    void addTableMyClassListSelectionListener(ListSelectionListener listSelectionListener);

    int getColumnIndex(String columnName);
}
