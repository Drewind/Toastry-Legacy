package Services;

import Entities.Product;
import Models.ProductModel;

/**
 * Service to be injected into views/controllers.
 * Computes the grand totals for the Dashboard.
 */
public class CalculateTotals {
    private final ProductModel model;

    public CalculateTotals(ProductModel model) {
        this.model = model;
    }

    public Double computeGrossSales() {
        Double result = 0.0;
        for (Product product : this.model.getEntities()) {
            result += product.getTotalSales();
        }
        return result;
    }

    public Double computeNetSales() {
        Double result = 0.0;
        for (Product product : this.model.getEntities()) {
            result += (product.getTotalSales() - product.getTotalExpenses());
        }
        return result;
    }

    public int computeTotalSales() {
        int result = 0;
        for (Product product : this.model.getEntities()) {
            result += product.getTotalSales();
        }
        return result;
    }
}
