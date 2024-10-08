package view.student_class;

import model.myClass.dto.MyClassDTO;
import model.student.dto.StudentDTO;
import model.student_class.dto.StudentClassDTO;
import utils.renderers.MyClassCellRenderer;
import utils.renderers.PhoneNumberCellRenderer;
import utils.renderers.StudentCellRenderer;
import view.MyFrame;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class StudentClassView extends MyFrame implements IStudentClassView {

    private final String ID = "ID";
    private final String DISCIPLINE = "Disciplina";
    private final String STUDENT_NAME = "Nome Aluno";
    private final String PHONE_NUMBER = "Telefone";
    private final Map<Class<?>, Vector<String>> tableColumnNames =
            Map.of(
                    MyClassDTO.class, new Vector<>(List.of(ID, "Professor", "Disciplina", "Sala", "Semestre")),
                    StudentDTO.class, new Vector<>(List.of(ID, "Nome", "Email", PHONE_NUMBER)),
                    StudentClassDTO.class, new Vector<>(List.of(DISCIPLINE, STUDENT_NAME, "Semestre", "Email")
                    )
            );
    private JPanel panelMain;
    private JLabel titleLabel;
    private JPanel middlePanel;
    private JPanel topPanel;
    private JPanel topMiddlePanel;
    private JPanel bottomMiddlePanel;
    private JPanel leftTopMiddlePanel;
    private JPanel rightTopMiddlePanel;
    private JPanel leftBottomMiddlePanel;
    private JPanel rightBottomMiddlePanel;
    private JFormattedTextField myClassSearchBox;
    private JFormattedTextField studentSearchBox;
    private JFormattedTextField studentClassSearchBox;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton reactivateButton;
    private JCheckBox inactivesStudentClassCheckBox;
    private JTable myClassTable;
    private JTable studentTable;
    private JTable studentClassTable;
    private JScrollPane myClassTableScrollPanel;
    private JScrollPane studentTableScrollPanel;
    private JScrollPane studentClassTableScrollPanel;
    private JButton myClassSearchButton;
    private JButton studentSearchButton;
    private JButton studentClassSearchButton;

    public StudentClassView() {

        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1200, 900);
        this.setLocationRelativeTo(null);
        myClassTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        studentTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        studentClassTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {
        Vector<Vector<Object>> dataVector = new Vector<>();

        for (Object obj : objectList) {
            Vector<Object> rowVector = buildRow(obj);

            if (rowVector != null) {
                dataVector.add(rowVector);
            }
        }

        return new DefaultTableModel(dataVector, objectList.isEmpty() ? new Vector<>() : tableColumnNames.get(objectList.get(0).getClass())) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private Vector<Object> buildRow(Object obj) {
        if (obj instanceof MyClassDTO myClass) {
            setEnableFirstTableButtons(false);
            setEnableSecondTableButtons(false);
            setEnableThirdTableButtons(false);
            myClassTable.clearSelection(); // redundant but at least explicit
            studentTable.clearSelection();
            studentClassTable.clearSelection();
            return buildMyClassRow(myClass);
        } else if (obj instanceof StudentDTO studentDTO) {
            setEnableSecondTableButtons(false);
            setEnableThirdTableButtons(false);
            studentTable.clearSelection(); // redundant but at least explicit
            studentClassTable.clearSelection();
            return buildStudentRow(studentDTO);
        } else if (obj instanceof StudentClassDTO studentClassDTO) {
            setEnableThirdTableButtons(false);
            studentClassTable.clearSelection(); // redundant but at least explicit
            return buildStudentClassRow(studentClassDTO);
        }
        return null;
    }

    private Vector<Object> buildMyClassRow(MyClassDTO myClass) {
        Vector<Object> rowVector = new Vector<>();
        rowVector.add(myClass.getId());
        rowVector.add(myClass.getProfessorName());
        rowVector.add(myClass.getDisciplineName());
        rowVector.add(myClass.getClassroomName());
        rowVector.add(myClass.getSemester());
        return rowVector;
    }

    private Vector<Object> buildStudentRow(StudentDTO studentDTO) {
        Vector<Object> rowVector = new Vector<>();
        rowVector.add(studentDTO.getId());
        rowVector.add(studentDTO.getName());
        rowVector.add(studentDTO.getEmail());
        rowVector.add(studentDTO.getPhoneNumber());
        return rowVector;
    }

    private Vector<Object> buildStudentClassRow(StudentClassDTO studentClassDTO) {
        Vector<Object> rowVector = new Vector<>();
        rowVector.add(new MyClassDTO(studentClassDTO.getClassId(), studentClassDTO.getDisciplineCode() + " " + studentClassDTO.getDisciplineName()));
        rowVector.add(new StudentDTO(studentClassDTO.getStudentId(), studentClassDTO.getStudentName()));
        rowVector.add(studentClassDTO.getClassSemester());
        rowVector.add(studentClassDTO.getStudentEmail());
        return rowVector;
    }


    @Override
    public void setEnableFirstTableButtons(final boolean b) {
        // myClassSearchButton.setEnabled(!b);
        studentSearchBox.setEnabled(b);
        studentSearchButton.setEnabled(b);
        studentTable.setEnabled(b);
        studentClassSearchBox.setEnabled(b);
        studentClassSearchButton.setEnabled(b);
        studentClassTable.setEnabled(b);
        inactivesStudentClassCheckBox.setEnabled(b);
    }

    @Override
    public void setEnableSecondTableButtons(final boolean b) {
        insertButton.setEnabled(b);
    }

    @Override
    public void setEnableThirdTableButtons(final boolean b) {
        reactivateButton.setEnabled(b);
        deleteButton.setEnabled(b);
    }

    @Override
    public void setButtonReactivate(boolean b) {
        reactivateButton.setEnabled(b);
    }

    @Override
    public void addInsertButtonActionListener(ActionListener actionListener) {
        insertButton.addActionListener(actionListener);
    }

    @Override
    public void addDeleteButtonActionListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    @Override
    public void addReactivateButtonActionListener(ActionListener actionListener) {
        reactivateButton.addActionListener(actionListener);
    }

    @Override
    public void addCheckBoxInactivesActionListener(ActionListener actionListener) {
        inactivesStudentClassCheckBox.addActionListener(actionListener);
    }

    @Override
    public void addMyClassSearchFieldActionListener(ActionListener actionListener) {
        myClassSearchBox.addActionListener(actionListener);
    }

    @Override
    public void addStudentSearchFieldActionListener(ActionListener actionListener) {
        studentSearchBox.addActionListener(actionListener);
    }

    @Override
    public void addStudentClassSearchFieldActionListener(ActionListener actionListener) {
        studentClassSearchBox.addActionListener(actionListener);
    }

    @Override
    public void addMyClassSearchButtonActionListener(ActionListener actionListener) {
        myClassSearchButton.addActionListener(actionListener);
    }

    @Override
    public void addStudentSearchButtonActionListener(ActionListener actionListener) {
        studentSearchButton.addActionListener(actionListener);
    }

    @Override
    public void addStudentClassSearchButtonActionListener(ActionListener actionListener) {
        studentClassSearchButton.addActionListener(actionListener);
    }

    @Override
    public void addTableMyClassListSelectionListener(ListSelectionListener listSelectionListener) {
        myClassTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public void addTableStudentListSelectionListener(ListSelectionListener listSelectionListener) {
        studentTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public void addTableStudentClassListSelectionListener(ListSelectionListener listSelectionListener) {
        studentClassTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public int getColumnIndex(Class<?> aClass, String columnName) {
        return tableColumnNames.get(aClass).indexOf(columnName);
    }

    @Override
    public void setTableMyClassModel(final List<MyClassDTO> disciplineList) {
        if (disciplineList == null) return;
        myClassTable.setModel(buildTableModel(disciplineList));
    }

    @Override
    public void setTableStudentModel(final List<StudentDTO> studentList) {
        if (studentList == null) return;

        studentTable.setModel(buildTableModel(studentList));

        int indexPhoneN = getColumnIndex(StudentDTO.class, PHONE_NUMBER);
        int columnCount = studentTable.getColumnCount();
        if (indexPhoneN >= 0 && indexPhoneN < columnCount)
            studentTable.getColumnModel().getColumn(indexPhoneN).setCellRenderer(new PhoneNumberCellRenderer());
    }

    @Override
    public void setTableStudentMyClassModel(final List<StudentClassDTO> studentClassList) {
        if (studentClassList == null) return;
        studentClassTable.setModel(buildTableModel(studentClassList));

        int indexDiscipline = getColumnIndex(StudentClassDTO.class, DISCIPLINE);
        int indexStudent = getColumnIndex(StudentClassDTO.class, STUDENT_NAME);
        int columnCount = studentClassTable.getColumnCount();

        if (indexDiscipline >= 0 && indexDiscipline < columnCount)
            studentClassTable.getColumnModel().getColumn(indexDiscipline).setCellRenderer(new MyClassCellRenderer());
        if (indexStudent >= 0 && indexStudent < columnCount)
            studentClassTable.getColumnModel().getColumn(indexStudent).setCellRenderer(new StudentCellRenderer());
    }

    @Override
    public String getMyClassSearchFieldText() {
        return myClassSearchBox.getText();
    }

    @Override
    public String getStudentSearchFieldText() {
        return studentSearchBox.getText();
    }

    @Override
    public String getStudentClassSearchFieldText() {
        return studentClassSearchBox.getText();
    }

    @Override
    public boolean isCheckBoxInactivesSelected() {
        return inactivesStudentClassCheckBox.isSelected();
    }

    @Override
    public int getSelectedMyClassRowIndex() {
        return myClassTable.getSelectedRow();
    }

    @Override
    public int getSelectedStudentRowIndex() {
        return studentTable.getSelectedRow();
    }

    @Override
    public int getSelectedStudentClassRowIndex() {
        return studentClassTable.getSelectedRow();
    }

    @Override
    public String getMyClassIdAt(int row) {
        return (String) myClassTable.getModel().getValueAt(row, getColumnIndex(MyClassDTO.class, ID));
    }

    @Override
    public String getStudentIdAt(int row) {
        return (String) studentTable.getModel().getValueAt(row, getColumnIndex(StudentDTO.class, ID));
    }

    @Override
    public List<String> getStudentClassIdsAt(int row) {
        String studentId = ((StudentDTO) studentClassTable.getModel().getValueAt(row, getColumnIndex(StudentClassDTO.class, STUDENT_NAME))).getId();
        String myClassId = ((MyClassDTO) studentClassTable.getModel().getValueAt(row, getColumnIndex(StudentClassDTO.class, DISCIPLINE))).getId();

        return List.of(studentId, myClassId);
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
                "Confirmação de Reativação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return deleteConfirmation == JOptionPane.YES_OPTION;
    }
}