package view.myClass;

import model.classroom.dto.ClassroomDTO;
import model.discipline.dto.DisciplineDTO;
import model.myClass.dto.MyClassDTO;
import model.professor.dto.ProfessorDTO;
import utils.renderers.CpfCellRenderer;
import utils.renderers.ItemRenderer;
import utils.renderers.PhoneNumberCellRenderer;
import utils.renderers.TimeCellRenderer;
import view.MyFrame;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;


public class MyClassView extends MyFrame implements IMyClassView {

    private final String ID = "ID";
    private final String START_TIME = "Hora Início";
    private final String FINISH_TIME = "Hora Fim";
    private final Vector<String> tableColumnNames = new Vector<>(List.of(ID, "Professor", "Disciplina", "Sala", START_TIME, FINISH_TIME, "Semestre"));
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel semesterLabel;
    private JLabel startTimeLabel;
    private JLabel finishTimeLabel;
    private JLabel professorLabel;
    private JLabel disciplineLabel;
    private JLabel classroomLabel;
    private JPanel tablePanel;
    private JPanel insertFieldsPanel;
    private JPanel actionsPanel;
    private JPanel rightFieldsPanel;
    private JScrollPane myClassTableScrollPanel;
    private JPanel panelMain;
    private JTextField idField;
    private JTextField startTimeField;
    private JTextField searchField;
    private JFormattedTextField semesterField;
    private JFormattedTextField finishTimeField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton doneButton;
    private JButton reactivateButton;
    private JCheckBox inactivesCheckBox;
    private JTable myClassTable;
    private JComboBox<ProfessorDTO> professorComboBox;
    private JComboBox<ClassroomDTO> classroomComboBox;
    private JComboBox<DisciplineDTO> disciplineComboBox;
    private JButton searchButton;

    public MyClassView() {

        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        professorComboBox.setRenderer(new ItemRenderer());
        disciplineComboBox.setRenderer(new ItemRenderer());
        classroomComboBox.setRenderer(new ItemRenderer());
        myClassTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {

        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Object obj : objectList) {
            Vector<Object> rowVector = new Vector<>();

            if (obj instanceof MyClassDTO myClass) {
                rowVector.add(myClass.getId());
                rowVector.add(myClass.getProfessorName());
                rowVector.add(myClass.getDisciplineName());
                rowVector.add(myClass.getClassroomName());
                rowVector.add(myClass.getStartTime());
                rowVector.add(myClass.getFinishTime());
                rowVector.add(myClass.getSemester());
                dataVector.add(rowVector);
            }
        }

        return new DefaultTableModel(dataVector, tableColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    @Override
    public void switchButtons(final boolean b) {
        insertButton.setEnabled(!b);
        inactivesCheckBox.setEnabled(!b);
        searchButton.setEnabled(!b);
        searchField.setEnabled(!b);
        searchField.setEditable(!b);
        updateButton.setEnabled(b);
        deleteButton.setEnabled(b);
        doneButton.setEnabled(b);
    }

    @Override
    public void setButtonReactivate(boolean b) {
        reactivateButton.setEnabled(b);
    }

    @Override
    public void setFieldTexts(MyClassDTO myClass) {
        semesterField.setText(myClass.getSemester());
        startTimeField.setText(myClass.getStartTime());
        finishTimeField.setText(myClass.getFinishTime());

        for (int i = 0; i < professorComboBox.getItemCount(); i++) {
            if (professorComboBox.getItemAt(i).getId().equals(myClass.getProfessorId())) {
                professorComboBox.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < disciplineComboBox.getItemCount(); i++) {
            if (disciplineComboBox.getItemAt(i).getId().equals(myClass.getDisciplineId())) {
                disciplineComboBox.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < classroomComboBox.getItemCount(); i++) {
            if (classroomComboBox.getItemAt(i).getId().equals(myClass.getClassroomId())) {
                classroomComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public void clearAllFields() {
        idField.setText("");
        semesterField.setText("");
        startTimeField.setText("");
        finishTimeField.setText("");
        professorComboBox.setSelectedIndex(0);
        disciplineComboBox.setSelectedIndex(0);
        classroomComboBox.setSelectedIndex(0);
    }

    @Override
    public void addButtonInsertActionListener(ActionListener actionListener) {
        insertButton.addActionListener(actionListener);
    }

    @Override
    public void addButtonUpdateActionListener(ActionListener actionListener) {
        updateButton.addActionListener(actionListener);
    }

    @Override
    public void addButtonDeleteActionListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    @Override
    public void addButtonReactivateActionListener(ActionListener actionListener) {
        reactivateButton.addActionListener(actionListener);
    }

    @Override
    public void addButtonDoneActionListener(ActionListener actionListener) {
        doneButton.addActionListener(actionListener);
    }

    @Override
    public void addCheckBoxInactivesActionListener(ActionListener actionListener) {
        inactivesCheckBox.addActionListener(actionListener);
    }

    @Override
    public void addSearchFieldActionListener(ActionListener actionListener) {
        searchField.addActionListener(actionListener);
    }

    @Override
    public void addSearchButtonActionListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    @Override
    public void addTableMyClassListSelectionListener(ListSelectionListener listSelectionListener) {
        myClassTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public int getColumnIndex(String columnName) {
        return tableColumnNames.indexOf(columnName);
    }

    public void populateProfessorComboBox(final List<ProfessorDTO> professorList) {
        for (ProfessorDTO professor : professorList) {
            professorComboBox.addItem(professor);
        }
    }

    public void populateDisciplineComboBox(final List<DisciplineDTO> disciplineList) {
        for (DisciplineDTO discipline : disciplineList) {
            disciplineComboBox.addItem(discipline);
        }
    }

    public void populateClassroomComboBox(final List<ClassroomDTO> classroomList) {
        for (ClassroomDTO classroom : classroomList) {
            classroomComboBox.addItem(classroom);
        }
    }

    public void setTableMyClassModel(final List<MyClassDTO> myClassList) {
        if (myClassList == null) return;

        myClassTable.setModel(buildTableModel(myClassList));
        myClassTable.getColumnModel().getColumn(getColumnIndex(START_TIME)).setCellRenderer(new TimeCellRenderer());
        myClassTable.getColumnModel().getColumn(getColumnIndex(FINISH_TIME)).setCellRenderer(new TimeCellRenderer());
    }

    @Override
    public void setFieldID(String id) {
        idField.setText(id);
    }

    @Override
    public String getIdText() {
        return idField.getText();
    }

    @Override
    public String getStartTimeText() {
        return startTimeField.getText();
    }

    @Override
    public String getSemesterText() {
        return semesterField.getText();
    }

    @Override
    public String getFinishTimeText() {
        return finishTimeField.getText();
    }

    @Override
    public String getSearchBoxText() {
        return searchField.getText();
    }

    @Override
    public String getComboBoxProfessorId() {
        return ((ProfessorDTO) professorComboBox.getSelectedItem()).getId();
    }

    @Override
    public String getComboBoxDisciplineId() {
        return ((DisciplineDTO) disciplineComboBox.getSelectedItem()).getId();
    }

    @Override
    public String getComboBoxClassroomId() {
        return ((ClassroomDTO) classroomComboBox.getSelectedItem()).getId();
    }

    @Override
    public void setProfessorComboBox(ProfessorDTO professorDTO) {
        professorComboBox.setSelectedItem(professorDTO);
    }

    @Override
    public void setDisciplineComboBox(DisciplineDTO disciplineDTO) {
        disciplineComboBox.setSelectedItem(disciplineDTO);
    }

    @Override
    public void setClassroomComboBox(ClassroomDTO classroomDTO) {
        classroomComboBox.setSelectedItem(classroomDTO);
    }

    @Override
    public boolean isCheckBoxInactivesSelected() {
        return inactivesCheckBox.isSelected();
    }

    @Override
    public int getSelectedMyClassRowIndex() {
        return myClassTable.getSelectedRow();
    }

    @Override
    public String getMyClassIdAt(int row) {
        return (String) myClassTable.getModel().getValueAt(row, getColumnIndex(ID));
    }

    @Override
    public boolean isButtonDoneEnabled() {
        return doneButton.isEnabled();
    }

    @Override
    public boolean confirmDeactivation() {
        int deleteConfirmation = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esse item?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return deleteConfirmation == JOptionPane.YES_OPTION;
    }

    @Override
    public boolean confirmReactivation() {
        int deleteConfirmation = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja ativar esse item?",
                "Confirmação de Reativação", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return deleteConfirmation == JOptionPane.YES_OPTION;
    }

    private void createUIComponents() {
        MaskFormatter startTimeFormatter = null;
        MaskFormatter finishTimeFormatter = null;
        MaskFormatter semesterFormatter = null;
        String startTimePattern = "##:##";
        String finishTimePattern = "##:##";
        String semesterPattern = "####/##";
        char placeholder = ' ';
        try {
            startTimeFormatter = new MaskFormatter(startTimePattern);
            startTimeFormatter.setPlaceholderCharacter(placeholder);

            finishTimeFormatter = new MaskFormatter(finishTimePattern);
            finishTimeFormatter.setPlaceholderCharacter(placeholder);

            semesterFormatter = new MaskFormatter(semesterPattern);
            semesterFormatter.setPlaceholderCharacter(placeholder);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
            showErrorMessage("Um horário ou semestre pode não estar bem formatado!");
        }
        startTimeField = new JFormattedTextField(startTimeFormatter);
        finishTimeField = new JFormattedTextField(finishTimeFormatter);
        semesterField = new JFormattedTextField(semesterFormatter);
    }
}