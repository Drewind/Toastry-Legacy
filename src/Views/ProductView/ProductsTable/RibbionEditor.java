package Views.ProductView.ProductsTable;

import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import Controllers.ProductController;
import Entities.Product;
import Graphics.TabButton;
import Utilities.Debugger;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class RibbionEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private TabButton viewProduct = new TabButton("View");
    private TabButton compareProduct = new TabButton("Compare");
    private TabButton deleteProduct = new TabButton("Delete");
    private Product currentData;

    public RibbionEditor(final ProductController controller) {
        mainPanel.add(viewProduct);
        mainPanel.add(compareProduct);
        mainPanel.add(deleteProduct);

        viewProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.editProductScreen(currentData.getID());
            }
        });

        compareProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //controller.editProductScreen(currentData.getID());
            }
        });

        deleteProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.deleteProduct(currentData.getID());
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return currentData;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        try {
            currentData = (Product) value;
        } catch (Exception ex) {
            Debugger.output(ex, "RibbionEditor");
        }
        return mainPanel;
    }
}