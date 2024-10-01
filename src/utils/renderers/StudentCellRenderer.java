package utils.renderers;

import model.student.dto.StudentDTO;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class StudentCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        if (value != null && value instanceof StudentDTO) {
            setText(((StudentDTO) value).getName());
        }

        return cellComponent;
    }
}
