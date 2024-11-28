package view.professor;

import model.professor.dto.ProfessorDTO;
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

import static utils.renderers.CpfCellRenderer.formatCpf;
import static utils.renderers.PhoneNumberCellRenderer.formatPhoneNumber;


public class ProfessorView extends MyFrame implements IProfessorView {

    private final String ID = "ID";
    private final String PHONE_NUMBER = "Telefone";
    private final String CPF = "Cpf";
    private final Vector<String> tableColumnNames = new Vector<>(List.of(ID, "Nome", "Email", PHONE_NUMBER, CPF, "Título"));
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel phoneNumberLabel;
    private JPanel tablePanel;
    private JPanel insertFieldsPanel;
    private JPanel actionsPanel;
    private JPanel leftFieldsPanel;
    private JPanel rightFieldsPanel;
    private JScrollPane professorTableScrollPanel;
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
    private JTable professorTable;
    private JButton searchButton;
    private JFormattedTextField cpfField;
    private JLabel cpfLabel;
    private JFormattedTextField title1Field;
    private JLabel title1Label;


    public ProfessorView() {
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        professorTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {

        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Object obj : objectList) {
            Vector<Object> rowVector = new Vector<>();

            if (obj instanceof ProfessorDTO professor) {
                rowVector.add(professor.getId());
                rowVector.add(professor.getName());
                rowVector.add(professor.getEmail());
                rowVector.add(professor.getPhoneNumber());
                rowVector.add(professor.getCpf());
                rowVector.add(professor.getTitle());
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
    public void setFieldTexts(ProfessorDTO professor) {
        nameField.setText(professor.getName());
        emailField.setText(professor.getEmail());
        phoneNumberField.setText(formatPhoneNumber(professor.getPhoneNumber()));
        cpfField.setText(formatCpf(professor.getCpf()));
        title1Field.setText(professor.getTitle());
    }

    @Override
    public void clearAllFields() {
        nameField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        idField.setText("");
        cpfField.setText("");
        title1Field.setText("");
    }

    private void createUIComponents() {
        MaskFormatter phoneNumberFormatter = null;
        String pattern = "(##)*####-####";
        char placeholder = ' ';
        try {
            phoneNumberFormatter = new MaskFormatter(pattern);
            phoneNumberFormatter.setPlaceholderCharacter(placeholder);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
            showErrorMessage("Um telefone pode não estar bem formatado!");
        }
        phoneNumberField = new JFormattedTextField(phoneNumberFormatter);
        MaskFormatter cpfFormatter = null;
        String pattern1 = "###.###.###-##";
        try {
            cpfFormatter = new MaskFormatter(pattern1);
            cpfFormatter.setPlaceholderCharacter(placeholder);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
            showErrorMessage("Um cpf pode não estar bem formatado!");
        }
        cpfField = new JFormattedTextField(cpfFormatter);
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
    public void addTableProfessorListSelectionListener(ListSelectionListener listSelectionListener) {
        professorTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public int getColumnIndex(String columnName) {
        return tableColumnNames.indexOf(columnName);
    }

    public void setTableProfessorModel(final List<ProfessorDTO> professorList) {
        if (professorList == null) return;

        professorTable.setModel(buildTableModel(professorList));
        professorTable.getColumnModel().getColumn(getColumnIndex(PHONE_NUMBER)).setCellRenderer(new PhoneNumberCellRenderer());
        professorTable.getColumnModel().getColumn(getColumnIndex(CPF)).setCellRenderer(new CpfCellRenderer());
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
    public String getCpfText() {
        return cpfField.getText();
    }
    @Override
    public String getTitleText() {
        return title1Field.getText();
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
    public boolean isCheckBoxInactivesSelected() {
        return inactivesCheckBox.isSelected();
    }

    @Override
    public int getSelectedProfessorRowIndex() {
        return professorTable.getSelectedRow();
    }

    @Override
    public String getProfessorIdAt(int row) {
        return (String) professorTable.getModel().getValueAt(row, getColumnIndex(ID));
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