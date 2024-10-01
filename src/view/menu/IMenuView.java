package view.menu;

import java.awt.event.ActionListener;

public interface IMenuView {

    void addProfessorButtonActionListener(ActionListener actionListener);

    void addClassroomsButtonActionListener(ActionListener actionListener);

    void addClassesButtonActionListener(ActionListener actionListener);

    void addDisciplinesButtonActionListener(ActionListener actionListener);

    void addStudentsButtonActionListener(ActionListener actionListener);

    void addStudentsClassesButtonActionListener(ActionListener actionListener);

    void setProfessorsButtonEnabled(boolean enabled);

    void setClassroomsButtonEnabled(boolean enabled);

    void setClassesButtonEnabled(boolean enabled);

    void setDisciplinesButtonEnabled(boolean enabled);

    void setStudentsButtonEnabled(boolean enabled);

    void setStudentsClassesButtonEnabled(boolean enabled);
}
