package Views.Home;

import java.util.ArrayList;
import java.util.Collection;

import Entities.Product;
import Graphics.Tables.EntityTableModel;
import Interfaces.EntityInterface;

public class TableModelSales extends EntityTableModel {
    public TableModelSales(Collection<Product> collection) {
        super();
        for (Product entity : collection) {
            super.addRow(entity);
        }
    }

    @Override
    public String getColumnName(int column) {
        String name = null;
        switch (column) {
            case 0:
                name = "Menu Item";
                break;
            case 1:
                name = "QTY";
                break;
            case 2:
                name = "Net Sales";
                break;
            case 3:
                name = "Margin";
                break;
        }
        return name;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public ArrayList<Product> getChangedProducts() {
        ArrayList<Product> entities = new ArrayList<>(super.getRowCount());
        for (EntityInterface entity : super.entities.values()) {
            try {
                this.entities.put(entity.getID(), entity);
            } catch (Exception ex) {
                System.out.println("Warning: TableModelSales couldn't convert entity to product, getChangedProducts.");
            }
        }
        return entities;
    }

    @Override
    // wip
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;

        try {
            Product product = (Product)super.entities.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    value = product.getProductName();
                    break;
                case 1:
                    value = product.getPrice();
                    break;
                case 2:
                    value = product.getCost();
                    break;
                case 3:
                    value = product.getDailySales();
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Warning: TableModelSales couldn't convert class, getValueAt.");
        }
        
        return value;
    }

    @Override
    // wip
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue instanceof String) {
            try {
                Product p = (Product)super.entities.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        p.setProductName(aValue.toString());
                        break;
                    case 1:
                        p.setPrice(Double.parseDouble(aValue.toString()));
                        break;
                    case 2:
                        p.setCost(Double.parseDouble(aValue.toString()));
                        break;
                    case 3:
                        p.addSale(Integer.parseInt(aValue.toString()));
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Warning: TableModelSales couldn't convert class, setValueAt.");
            }
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}