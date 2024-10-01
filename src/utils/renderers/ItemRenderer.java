package utils.renderers;

import model.classroom.dto.ClassroomDTO;
import model.discipline.dto.DisciplineDTO;
import model.professor.dto.ProfessorDTO;

import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;

public class ItemRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof ProfessorDTO professor) {
            setText(professor.getName() + " (ID: " + professor.getId() + ")");
            if (professor.getId().equals("0")) {
                setText(professor.getName());
            }
        }
        if (value instanceof DisciplineDTO discipline) {
            setText(discipline.getCode() + " " + discipline.getName());
            if (discipline.getId().equals("0")) {
                setText(discipline.getName());
            }
        }
        if (value instanceof ClassroomDTO classroom) {
            setText(classroom.getLocation() + " (" + classroom.getName() + ")");
            if (classroom.getId().equals("0")) {
                setText(classroom.getName());
            }
        }
        return this;
    }
}
