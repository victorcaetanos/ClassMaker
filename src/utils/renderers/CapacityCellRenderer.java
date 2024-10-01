package utils.renderers;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.text.NumberFormat;

public class CapacityCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            String formattedCapacity = formatCapacity(value.toString());
            setText(formattedCapacity);
        }

        return cellComponent;
    }

    private String formatCapacity(String capacityString) {
        try {
            double capacity = Integer.parseInt(capacityString);
            return NumberFormat.getIntegerInstance().format(capacity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return capacityString;
        }
    }
}
