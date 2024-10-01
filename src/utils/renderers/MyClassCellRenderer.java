package utils.renderers;

import model.myClass.dto.MyClassDTO;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class MyClassCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null && value instanceof MyClassDTO) {
            setText(((MyClassDTO) value).getDisciplineName());
        }

        return cellComponent;
    }
}
