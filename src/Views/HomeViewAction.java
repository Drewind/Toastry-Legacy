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
import Views.Home.DailySales;
import Views.Home.DailyStats;
import Views.Home.Options;
import Views.Home.RecordExpenses;
import Views.Home.RecordSales;
import Views.Home.SalesTable;
import Views.Home.TableModelProducts;
import Views.Home.TableModelSales;
import Utilities.GBC;

public class HomeViewAction implements ViewActionInterface, ViewActionObserver<Product> {
    private final ProductModel model;
    private final DailyStatsModel stats;
    private final HomeController controller;
    private HashMap<String, JComponent> views = new HashMap<>();
    private TableModelProducts tableModel;
    private TableModelSales dailySalesTM;

    public HomeViewAction(ControllerInterface controller, IModelInterface<?> model, DailyStatsModel stats) {
        this.model = (ProductModel)model;
        this.stats = (DailyStatsModel)stats;
        this.controller = (HomeController)controller;

        this.model.registerObserver(this);
    }

    // init method

    @Override
    public JPanel renderView() {
        System.out.println("HomeViewAction: renderView called.");
        // Container
        JPanel container = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();

        // Table Model generation
        TableModelProducts tableModel = new TableModelProducts(this.model.getEntities());
        this.tableModel = tableModel;

        dailySalesTM = new TableModelSales(this.model.getEntities());

        SalesTable form = new SalesTable(dailySalesTM);
        this.views.put("Form", form);
        
        // Inserting views into container
        GBC.setGBC(gbc, 0, 0, 0.9);
        container.add(new DailySales(180, this.model.getEntities(), this.model, tableModel), gbc);

        // Inject StatsModel because this view will register itself as an observer with it.
        GBC.setGBC(gbc, 1, 0, 0.1);
        container.add(new DailyStats(this.stats), gbc);
        
        GBC.setGBC(gbc, 0, 1, 0.9);
        container.add(new RecordSales(this.controller, this.model, form), gbc);

        GBC.setGBC(gbc, 1, 1, 0.1);
        container.add(new RecordExpenses(), gbc);

        GBC.setGBC(gbc, 1, 2, 0.1);
        container.add(new Options(this.controller), gbc);

        GBC.setToAnchorBottom(gbc, 0, 2, 2);
        JPanel anchorPanel = new JPanel();
        anchorPanel.setOpaque(false);
        container.add(anchorPanel, gbc);

        return container;
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