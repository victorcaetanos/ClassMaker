package view.menu;

import view.MyFrame;


import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuView extends MyFrame implements IMenuView {
    private JPanel panelMain;
    private JLabel labelTitle;
    private JButton classroomsButton;
    private JButton professorsButton;
    private JButton classesButton;
    private JButton disciplinesButton;
    private JButton studentsButton;
    private JButton studentsClassesButton;

    public MenuView() {
        this.setContentPane(panelMain);
        this.setVisible(true);
    }

    @Override
    public TableModel buildTableModel(List<?> objectList) {
        return null;
    }

    @Override
    public void addProfessorButtonActionListener(ActionListener actionListener) {
        professorsButton.addActionListener(actionListener);
    }

    @Override
    public void addClassroomsButtonActionListener(final ActionListener actionListener) {
        classroomsButton.addActionListener(actionListener);
    }

    @Override
    public void addClassesButtonActionListener(final ActionListener actionListener) {
        classesButton.addActionListener(actionListener);

    }

    @Override
    public void addDisciplinesButtonActionListener(final ActionListener actionListener) {
        disciplinesButton.addActionListener(actionListener);
    }

    @Override
    public void addStudentsButtonActionListener(ActionListener actionListener) {
        studentsButton.addActionListener(actionListener);
    }

    @Override
    public void addStudentsClassesButtonActionListener(ActionListener actionListener) {
        studentsClassesButton.addActionListener(actionListener);
    }

    @Override
    public void setProfessorsButtonEnabled(boolean enabled) {
        professorsButton.setEnabled(enabled);
    }

    @Override
    public void setClassroomsButtonEnabled(boolean enabled) {
        classroomsButton.setEnabled(enabled);
    }

    @Override
    public void setClassesButtonEnabled(boolean enabled) {
        classesButton.setEnabled(enabled);
    }

    @Override
    public void setDisciplinesButtonEnabled(boolean enabled) {
        disciplinesButton.setEnabled(enabled);
    }

    @Override
    public void setStudentsButtonEnabled(boolean enabled) {
        studentsButton.setEnabled(enabled);
    }

    @Override
    public void setStudentsClassesButtonEnabled(boolean enabled) {
        studentsClassesButton.setEnabled(enabled);
    }
}

