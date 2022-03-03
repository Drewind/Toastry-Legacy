package Views.Home;

import java.util.ArrayList;
import Entities.Product;
import Graphics.Tables.EntityTableModel;
import Interfaces.EntityInterface;

public class TableModelProducts extends EntityTableModel {
    public TableModelProducts(ArrayList<Product> products) {
        super();
        for (Product entity : products) {
            super.addRow(entity);
        }
    }

    @Override
    public String getColumnName(int column) {
        String name = null;
        switch (column) {
            case 0:
                name = "Dish";
                break;
            case 1:
                name = "Price";
                break;
            case 2:
                name = "Cost";
                break;
            case 3:
                name = "Profit";
                break;
            case 4:
                name = "Sales";
                break;
            case 5:
                name = "Growth";
                break;
        }
        return name;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    public ArrayList<Product> getChangedProducts() {
        ArrayList<Product> entities = new ArrayList<>(super.getRowCount());
        for (EntityInterface entity : super.entities) {
            try {
                entities.add((Product)entity);
            } catch (Exception ex) {
                System.out.println("Warning: TableModelProducts couldn't convert entity to product, getChangedProducts.");
            }
        }
        return entities;
    }

    @Override
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
                    value = product.getProfit();
                    break;
                case 4:
                    value = product.getDailySales();
                    break;
                case 5:
                    value = "10%"; // wip
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Warning: TableModelProducts.java couldn't convert class, getValueAt.");
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
                        p.setDailySales(Integer.parseInt(aValue.toString()));
                        break;
                    case 4:
                        p.setDailySales(Integer.parseInt(aValue.toString()));
                        break;
                    case 5:
                        p.setDailySales(Integer.parseInt(aValue.toString()));
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Warning: TableModelProducts.java couldn't convert class, setValueAt.");
            }
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case 0:
                return false;
            default:
                return true;
        }
    }
}