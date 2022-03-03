package Services.Commands;

import Entities.Product;
import Interfaces.Command;
import Models.DailyStatsModel;
import Models.IModelInterface;

public class ComputeEndOfDay implements Command {
    final private DailyStatsModel statsModel;
    final private IModelInterface<Product> productModel;

    public ComputeEndOfDay(final DailyStatsModel statsModel, final IModelInterface<Product> productModel) {
        this.statsModel = statsModel;
        this.productModel = productModel;
    }

    @Override
    public void execute() {
        System.out.println("\nComputeEndOfDay: running command.");
        // Compute daily sales and expenses. Reset entities.
        for (Product getProduct : this.productModel.getEntities()) {
            this.statsModel.addSales(getProduct.getDailySales());
            this.statsModel.addRevenues((Double)(getProduct.getDailySales() * getProduct.getPrice()));
            this.statsModel.addExpenses((Double)(getProduct.getDailySales() * getProduct.getCost()));
            
            getProduct.computeEndOfDay();
        }

        // Call notifiers listening to the statsModel.
        this.statsModel.nextDay();
        this.statsModel.notifyObservers();
        System.out.println("\nComputeEndOfDay: finished executing.. execute().");
    }
}