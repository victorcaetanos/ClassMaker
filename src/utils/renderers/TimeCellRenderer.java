package utils.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TimeCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            String formattetTime = formatTime(value.toString());
            setText(formattetTime);
        }

        return cellComponent;
    }

    private String formatTime(String time) {
        if (time.length() == 8) {
            return String.format("%s", time.substring(0, 5));
        }
        return time;
    }
}
