package Views;

import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;

import Controllers.HomeController;
import Controllers.ViewActionInterface;
import Entities.Product;
import Interfaces.ControllerInterface;
import Interfaces.ViewActionObserver;
import Models.DailyStatsModel;
import Models.IModelInterface;
import Models.ProductModel;
import Views.Home.Dashboard;
import Views.Home.SalesTable;
import Views.Home.TMTopSales;
import Views.Home.TableModelSales;
import Utilities.GBC;

public class HomeViewAction implements ViewActionInterface, ViewActionObserver<Product> {
    private final ProductModel model;
    private final DailyStatsModel stats;
    private final HomeController controller;
    private HashMap<String, JComponent> views = new HashMap<>();
    private TMTopSales tableModel;
    private TableModelSales dailySalesTM;

    public HomeViewAction(ControllerInterface controller, IModelInterface<?> model, DailyStatsModel stats) {
        this.model = (ProductModel)model;
        this.stats = (DailyStatsModel)stats;
        this.controller = (HomeController)controller;

        this.model.registerObserver(this);
    }

    @Override
    public JPanel renderView() {
        System.out.println("HomeViewAction: renderView called.");

        // ______________________
        // Table model generation
        TMTopSales tableModel = new TMTopSales(this.model.getEntities());
        this.tableModel = tableModel;

        dailySalesTM = new TableModelSales(this.model.getEntities());

        SalesTable form = new SalesTable(dailySalesTM);
        this.views.put("Form", form);

        return new Dashboard(this.stats, this.model);
    }

    /**
     * Retrieves all of the products that were modified in the form.
     * @return List<Product>
     */
    public List<Product> getProductChanges() {
        // return this.tableModel.getChangedproducts();
        return this.tableModel.getChangedProducts();
    }

    @Override
    public void notifyNewEntity(Product product) {
        this.tableModel.addRow(product);
        this.dailySalesTM.addRow(product);
    }

    @Override
    public void notifyRemovedEntity(Product product) {
        this.tableModel.removeRow(product);
        this.dailySalesTM.removeRow(product);
    }

    @Override
    public void notifyModifiedEntity(Product product) {
        this.tableModel.updateRow(product);
        this.dailySalesTM.updateRow(product);
    }
}