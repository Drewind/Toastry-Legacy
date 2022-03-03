package Views.ProductView.ProductsTable;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import Controllers.ProductController;
import Entities.Product;
import Graphics.Tables.DefaultRowStyle;
import Graphics.Tables.EntityTable;

public class ProductView extends JPanel {
    private final TableModel tableModel;
    private EntityTable table;

    public ProductView(final ProductController controller, TableModel tableModel) {
        this.tableModel = tableModel;
        this.table = new EntityTable(tableModel);

        // Spacing
        table.setIntercellSpacing(new Dimension(10, 3)); // Width, height

        // Renderers and editors
        RibbionRenderer rendererEditor = new RibbionRenderer();
        table.setDefaultRenderer(String.class, new DefaultRowStyle());
        table.getColumnModel().getColumn(1).setCellRenderer(rendererEditor);
        table.getColumnModel().getColumn(1).setCellEditor(new RibbionEditor(controller));

        // testing column resizing
        TableColumn c1 = table.getColumnModel().getColumn(1);
        c1.setPreferredWidth(300);
        c1.setMaxWidth(300);
        
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);
    }

    public void addRow(Product rowData) {
        tableModel.addRow(rowData);
    }
}