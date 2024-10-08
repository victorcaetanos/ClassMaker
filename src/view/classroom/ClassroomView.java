package view.classroom;

import model.classroom.dto.ClassroomDTO;
import utils.renderers.CapacityCellRenderer;
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
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;


public class ClassroomView extends MyFrame implements IClassroomView {

    private final String ID = "ID";
    private final Vector<String> tableColumnNames = new Vector<>(List.of(ID, "Nome", "Localização", "Capacidade"));
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel capacityLabel;
    private JPanel tablePanel;
    private JPanel insertFieldsPanel;
    private JPanel actionsPanel;
    private JPanel leftFieldsPanel;
    private JPanel rightFieldsPanel;
    private JScrollPane classroomTableScrollPanel;
    private JPanel panelMain;
    private JTextField idField;
    private JTextField nameField;
    private JTextField seachField;
    private JFormattedTextField capacityField;
    private JFormattedTextField locationField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton doneButton;
    private JButton reactivateButton;
    private JCheckBox inactivesCheckBox;
    private JTable classroomTable;
    private JButton searchButton;

    public ClassroomView() {

        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        classroomTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {

        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Object obj : objectList) {
            Vector<Object> rowVector = new Vector<>();

            if (obj instanceof ClassroomDTO classroom) {
                rowVector.add(classroom.getId());
                rowVector.add(classroom.getName());
                rowVector.add(classroom.getLocation());
                rowVector.add(classroom.getCapacity());
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
        seachField.setEnabled(!b);
        seachField.setEditable(!b);
        updateButton.setEnabled(b);
        deleteButton.setEnabled(b);
        doneButton.setEnabled(b);
    }

    @Override
    public void setButtonReactivate(boolean b) {
        reactivateButton.setEnabled(b);
    }

    @Override
    public void setFieldTexts(final ClassroomDTO classroom) {
        nameField.setText(classroom.getName());
        capacityField.setText(classroom.getCapacity());
        locationField.setText(classroom.getLocation());
    }

    @Override
    public void clearAllFields() {
        nameField.setText("");
        locationField.setText("");
        capacityField.setText("");
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
    public void addSearchFieldActionListener(ActionListener actionListener) {
        seachField.addActionListener(actionListener);
    }

    @Override
    public void addSearchButtonActionListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    @Override
    public void addTableClassroomListSelectionListener(ListSelectionListener listSelectionListener) {
        classroomTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    @Override
    public int getColumnIndex(String columnName) {
        return tableColumnNames.indexOf(columnName);
    }

    @Override
    public void setTableClassroomModel(final List<ClassroomDTO> classroomList) {
        if (classroomList == null) return;

        classroomTable.setModel(buildTableModel(classroomList));
        classroomTable.getColumnModel().getColumn(getColumnIndex("Capacidade")).setCellRenderer(new CapacityCellRenderer());
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
    public String getCapacityText() {
        return capacityField.getText();
    }

    @Override
    public String getLocationText() {
        return locationField.getText();
    }

    @Override
    public String getFilterText() {
        return seachField.getText();
    }

    @Override
    public boolean isCheckBoxInactivesSelected() {
        return inactivesCheckBox.isSelected();
    }

    @Override
    public int getSelectedClassroomRowIndex() {
        return classroomTable.getSelectedRow();
    }

    @Override
    public String getClassroomIdAt(int row) {
        return (String) classroomTable.getModel().getValueAt(row, getColumnIndex(ID));
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