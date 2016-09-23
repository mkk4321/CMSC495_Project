
package CMSC495_TT;
/**
 *
 * @author Manoj
 */
/* 
    Tenacious Turtles Team
    Apparel Point of Sale (APOS) system
    Class for formatting JTable cells

*/
import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CurrencyTableCellRenderer extends DefaultTableCellRenderer   {
   private static final NumberFormat FORMAT = NumberFormat.getCurrencyInstance();

    // Overriding default formatter
    @Override
    public final Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        final Component result = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        if (value instanceof Number) {
            setHorizontalAlignment(JLabel.RIGHT);
            setText(FORMAT.format(value));
        } else {
            setText("");
        }
        return result;
    }  
    
    
    
}
