package Views;

import javax.swing.JComponent;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.List;

import Controllers.HomeController;
import Controllers.ViewActionInterface;
import Entities.Entity;
import Entities.Product;
import Interfaces.ControllerInterface;
import Interfaces.ViewActionObserver;
import Models.DailyStatsModel;
import Models.Model;
import Models.ProductModel;
import Views.Home.Dashboard;
import Views.Home.StandardTable;
import Views.Home.TMTopSales;
import Views.Home.TableModelSales;

public class HomeViewAction implements ViewActionInterface, ViewActionObserver {
    private final ProductModel model;
    private final HomeController controller;
    private HashMap<String, JComponent> views = new HashMap<>();
    private TMTopSales tableModel;
    private TableModelSales dailySalesTM;

    public HomeViewAction(ControllerInterface controller, Model productModel, DailyStatsModel statsModel) {
        this.model = (ProductModel)productModel;
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

        StandardTable form = new StandardTable(dailySalesTM);
        this.views.put("Form", form);

        return new Dashboard(this.controller, this.model);
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
    public void notifyNewEntity(Entity product) {
        this.tableModel.addRow(product);
        this.dailySalesTM.addRow(product);
    }

    @Override
    public void notifyRemovedEntity(Entity product) {
        this.tableModel.removeRow(product);
        this.dailySalesTM.removeRow(product);
    }

    @Override
    public void notifyModifiedEntity(Entity product) {
        this.tableModel.updateRow(product);
        this.dailySalesTM.updateRow(product);
    }
}