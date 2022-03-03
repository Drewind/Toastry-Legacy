package Graphics.Tables;

import javax.swing.JTable;

import Views.ProductView.ProductsTable.TableModel;

/**
 * A simple table designed to display entities.
 */
public class EntityTable extends JTable {
    public EntityTable(TableModel tableModel) {
        super(tableModel);
        super.setAutoCreateRowSorter(true);
    }

    // a kludge to make the cells high enough to display properly
    @Override
    public int getRowHeight() {
        return 2 * super.getRowHeight();
    }
}