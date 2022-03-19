package Graphics.Tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import Utilities.Styler;

import java.awt.Component;
import java.awt.Color;

public class DefaultRowStyle extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected)
            // setBackground( table.getSelectionBackground() );
            setBackground( Styler.DARK_SHADE2_COLOR );
        else
        {
            setBackground( Styler.CONTAINER_BACKGROUND );
            // setBackground( table.getBackground() );

            try
            {
                int number = Integer.parseInt( value.toString() );

                if (number > 24)
                    setBackground( Color.RED );
            }
            catch(Exception e) {}
        }

        return this;
    }
}