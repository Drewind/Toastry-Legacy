package Views.ProductView.ProductsTable;
import javax.swing.table.DefaultTableModel;

import Entities.Entity;
import Entities.Product;
import Utilities.Debugger;

public class TableModel extends DefaultTableModel {
    private static final Object[] COLUMN_NAMES = new Object[] { "Name", "Options" };

    public TableModel() {
        super(COLUMN_NAMES, 0);
    }

    public void addRow(Entity rowData) {
        if (!rowExists(rowData)) {
            super.addRow(new Object[] {
                ((Product)rowData).getProductName(),
                rowData
            });
        }
    }

    public void removeRow(Entity rowData) {
        try {
            // super.removeRow(rowData.getID());
        } catch (Exception ex) {
            System.out.println("Warning: TableModel couldn't delete a row from table model.");
        }
    }

    public void updateRow(Entity rowData) {
        if (rowExists(rowData)) {
            try {
                if (rowData != null) {
                    // super.setValueAt(rowData.getProductName(), rowData.getID(), 0);
                    // super.setValueAt(rowData, rowData.getID(), 1);
                } else {
                    System.out.println("Warning: TableModel couldn't update a row from table model.");
                }
            } catch (Exception ex) {
                Debugger.output(ex, "TableModel");
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return String.class;
        } else {
            return Product.class;
        }
    }

    private boolean rowExists(Entity product) {
        for (int i = 0; i < super.getRowCount(); i++) {
            if (super.getValueAt(i, 1).equals(product)) {
                return true;
            }
        }
        
        return false;
    }
}