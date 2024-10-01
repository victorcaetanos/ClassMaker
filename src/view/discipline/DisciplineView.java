package view.discipline;

import model.discipline.dto.DisciplineDTO;
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
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;


public class DisciplineView extends MyFrame implements IDisciplineView {

    private final String ID = "ID";
    private final Vector<String> tableColumnNames = new Vector<>(List.of(ID, "Nome", "Código", "Descrição"));
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel codeLabel;
    private JPanel tablePanel;
    private JPanel insertFieldsPanel;
    private JPanel actionsPanel;
    private JPanel leftFieldsPanel;
    private JPanel rightFieldsPanel;
    private JScrollPane disciplineTableScrollPanel;
    private JPanel panelMain;
    private JTextField idField;
    private JTextField nameField;
    private JTextField filterField;
    private JFormattedTextField codeField;
    private JFormattedTextField descriptionField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton doneButton;
    private JButton reactivateButton;
    private JCheckBox inactivesCheckBox;
    private JTable disciplineTable;

    public DisciplineView() {

        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {

        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Object obj : objectList) {
            Vector<Object> rowVector = new Vector<>();

            if (obj instanceof DisciplineDTO discipline) {
                rowVector.add(discipline.getId());
                rowVector.add(discipline.getName());
                rowVector.add(discipline.getCode());
                rowVector.add(discipline.getDescription());
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
        filterField.setEnabled(!b);
        filterField.setEditable(!b);
        updateButton.setEnabled(b);
        deleteButton.setEnabled(b);
        doneButton.setEnabled(b);
    }

    @Override
    public void setButtonReactivate(boolean b) {
        reactivateButton.setEnabled(b);
    }

    @Override
    public void setFieldTexts(DisciplineDTO discipline) {
        nameField.setText(discipline.getName());
        descriptionField.setText(discipline.getDescription());
        codeField.setText(discipline.getCode());
    }

    @Override
    public void clearAllFields() {
        nameField.setText("");
        codeField.setText("");
        descriptionField.setText("");
        idField.setText("");
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
    public void addFieldFilterActionListener(ActionListener actionListener) {
        filterField.addActionListener(actionListener);
    }

    @Override
    public void addTableDisciplineListSelectionListener(ListSelectionListener listSelectionListener) {
        disciplineTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public int getColumnIndex(String columnName) {
        return tableColumnNames.indexOf(columnName);
    }

    @Override
    public void setTableDisciplineModel(final List<DisciplineDTO> disciplineList) {
        if (disciplineList == null) return;

        disciplineTable.setModel(buildTableModel(disciplineList));
    }

    @Override
    public void setFieldId(String id) {
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
    public String getCodeText() {
        return codeField.getText();
    }

    @Override
    public String getDescriptionText() {
        return descriptionField.getText();
    }

    @Override
    public String getFilterText() {
        return filterField.getText();
    }

    @Override
    public boolean isCheckBoxInactivesSelected() {
        return inactivesCheckBox.isSelected();
    }

    @Override
    public int getSelectedDisciplineRowIndex() {
        return disciplineTable.getSelectedRow();
    }

    @Override
    public String getDisciplineIdAt(int row) {
        return (String) disciplineTable.getModel().getValueAt(row, getColumnIndex(ID));
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
                "Tem certeza que deseja reativar esse item?",
                "Confirmação de Reativação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return deleteConfirmation == JOptionPane.YES_OPTION;
    }
}