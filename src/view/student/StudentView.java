package view.student;

import model.student.dto.StudentDTO;
import utils.renderers.CpfCellRenderer;
import utils.renderers.PhoneNumberCellRenderer;
import view.MyFrame;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import static utils.renderers.PhoneNumberCellRenderer.formatPhoneNumber;


public class StudentView extends MyFrame implements IStudentView {

    private final String ID = "ID";
    private final String CPF = "CPF";
    private final String PHONE_NUMBER = "Telefone";
    private final Vector<String> tableColumnNames = new Vector<>(List.of(ID, "Nome", CPF, "Email", PHONE_NUMBER, "Endereço"));
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel phoneNumberLabel;
    private JLabel emailLabel;
    private JLabel cpfLabel;
    private JLabel addressLabel;
    private JPanel tablePanel;
    private JPanel insertFieldsPanel;
    private JPanel actionsPanel;
    private JPanel leftFieldsPanel;
    private JPanel rightFieldsPanel;
    private JScrollPane studentTableScrollPanel;
    private JPanel panelMain;
    private JTextField idField;
    private JTextField nameField;
    private JTextField searchField;
    private JFormattedTextField phoneNumberField;
    private JFormattedTextField emailField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton doneButton;
    private JButton reactivateButton;
    private JCheckBox inactivesCheckBox;
    private JTable studentTable;
    private JFormattedTextField cpfField;
    private JFormattedTextField addressField;
    private JButton searchButton;

    public StudentView() {
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        studentTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {

        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Object obj : objectList) {
            Vector<Object> rowVector = new Vector<>();

            if (obj instanceof StudentDTO student) {
                rowVector.add(student.getId());
                rowVector.add(student.getName());
                rowVector.add(student.getCpf());
                rowVector.add(student.getEmail());
                rowVector.add(student.getPhoneNumber());
                rowVector.add(student.getAddress());
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
    public void setFieldTexts(StudentDTO student) {
        nameField.setText(student.getName());
        cpfField.setText(student.getCpf());
        emailField.setText(student.getEmail());
        phoneNumberField.setText(formatPhoneNumber(student.getPhoneNumber()));
        addressField.setText(student.getAddress());
    }

    @Override
    public void clearAllFields() {
        idField.setText("");
        nameField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        cpfField.setText("");
        addressField.setText("");
    }

    private void createUIComponents() {
        MaskFormatter phoneNumberFormatter = null;
        MaskFormatter cpfFormatter = null;
        String phoneNumberPattern = "(##)*####-####";
        String cpfPattern = "###.###.###-##";
        char placeholder = ' ';
        try {
            phoneNumberFormatter = new MaskFormatter(phoneNumberPattern);
            phoneNumberFormatter.setPlaceholderCharacter(placeholder);

            cpfFormatter = new MaskFormatter(cpfPattern);
            cpfFormatter.setPlaceholderCharacter(placeholder);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
            showErrorMessage("Um telefone pode não estar bem formatado!");
        }
        phoneNumberField = new JFormattedTextField(phoneNumberFormatter);
        cpfField = new JFormattedTextField(cpfFormatter);
        cpfField.setVerifyInputWhenFocusTarget(false);
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
    public void addFieldSearchActionListener(ActionListener actionListener) {
        searchField.addActionListener(actionListener);
    }

    @Override
    public void addSearchButtonActionListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    @Override
    public void addTableStudentListSelectionListener(ListSelectionListener listSelectionListener) {
        studentTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public int getColumnIndex(String columnName) {
        return tableColumnNames.indexOf(columnName);
    }

    public void setTableStudentModel(final List<StudentDTO> studentList) {
        if (studentList == null) return;

        studentTable.setModel(buildTableModel(studentList));
        studentTable.getColumnModel().getColumn(getColumnIndex(CPF)).setCellRenderer(new CpfCellRenderer());
        studentTable.getColumnModel().getColumn(getColumnIndex(PHONE_NUMBER)).setCellRenderer(new PhoneNumberCellRenderer());
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
    public String getNameText() {
        return nameField.getText();
    }

    @Override
    public String getPhoneNumberText() {
        return phoneNumberField.getText();
    }

    @Override
    public String getEmailText() {
        return emailField.getText();
    }

    @Override
    public String getFilterText() {
        return searchField.getText();
    }

    @Override
    public String getAddressText() {
        return addressField.getText();
    }

    @Override
    public String getCpfText() {
        return cpfField.getText();
    }

    @Override
    public boolean isCheckBoxInactivesSelected() {
        return inactivesCheckBox.isSelected();
    }

    @Override
    public int getSelectedStudentRowIndex() {
        return studentTable.getSelectedRow();
    }

    @Override
    public String getStudentIdAt(int row) {
        return (String) studentTable.getModel().getValueAt(row, getColumnIndex(ID));
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
                "Confirmação de Reativação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return deleteConfirmation == JOptionPane.YES_OPTION;
    }
}