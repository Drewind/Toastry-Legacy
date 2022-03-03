package Views.ProductView.ProductsTable;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import Graphics.TabButton;

import java.awt.Component;
import java.awt.FlowLayout;

class RibbionRenderer extends JPanel implements TableCellRenderer {
    private TabButton viewProduct = new TabButton("View");
    private TabButton compareProduct = new TabButton("Compare");
    private TabButton deleteProduct = new TabButton("Delete");

    public RibbionRenderer() {
        super(new FlowLayout(FlowLayout.RIGHT));
        add(viewProduct);
        add(compareProduct);
        add(deleteProduct);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        return this;
    }

}