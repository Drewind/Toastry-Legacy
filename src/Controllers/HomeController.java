package Controllers;

import javax.swing.JPanel;

import Constants.ActiveController;
import Entities.Product;
import Entities.Transaction;
import Interfaces.Command;
import Interfaces.ControllerInterface;
import Models.DailyStatsModel;
import Models.IModelInterface;
import Models.ProductModel;
import Services.Commands.ComputeEndOfDay;
import Views.HomeViewAction;
import Views.Popups.Popup;

import java.awt.CardLayout;
import java.util.ArrayList;

/**
 * Handles the home screen. Implements the standard ControllerInterface.
 */
public class HomeController implements ControllerInterface {
    private HomeViewAction creationView;
    private final JPanel contentPane;
    private final DailyStatsModel stats;
    private final ProductModel model;
    private final IModelInterface<Transaction> transModel;

    /**
     * Constructs a new HomeController controller.
     * @param model a reference to a IModelInterface with class <Product>
     * @param transModel a reference to a IModelInterface with class <Transaction>
     * @param stats a reference a DailyStatsModel object
     * @param contentPane a reference to the central content pane
     */
    public HomeController(
        IModelInterface<Product> model,
        IModelInterface<Transaction> transModel,
        DailyStatsModel stats,
        JPanel contentPane) {
        
        this.contentPane = contentPane;
        this.stats = stats;
        this.model = (ProductModel)model;
        this.transModel = transModel;

        // Views
        this.creationView = new HomeViewAction(this, model, stats);
        this.creationView.renderView();
    }

    /**
     * Switches the view to the product creation view.
     * @return void
     */
    public void switchToProductCreation() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, ActiveController.PRODUCT.toString());
    }

    /**
     * Ends the current day, saving all data entered.
     * @return void
     */
    public void endDay() {
        try {
            Command endOfDay = new ComputeEndOfDay(this.stats, this.model);
            endOfDay.execute();
        } catch (Exception ex) {
            System.out.println("Warning: HomeController's endDay failed.\n" + ex);
        }

        Popup.infoBox("End of Day", "This day has been recorded and all data saved.");
    }

    /**
     * Saves changes from the product table form to the database. Will persist those changes
     * using the model's interface.
     * 
     * Fetches the modified products from the view by calling the controller action's
     * .getProductChanges method.
     * @return void
     */
    public void persistSaleChanges() {
        ArrayList<String> errors = new ArrayList<>(10);
        int newID;

        for (Product product : this.creationView.getProductChanges()) {
            if (!this.model.editEntity(product))
                errors.add("Couldn't save changes to product (#" + product.getID() + " " + product.getProductName() + ").");
            else {
                newID = this.transModel.generateID();
                if (!this.transModel.createEntity(new Transaction(newID, stats.getCurrentDay(), product)))
                    errors.add("Exception thrown when saving transactions.");
            }
        }

        if (errors.size() > 0)
            Popup.infoBox("Error!", "Unfortunately, an exception occurred while trying to save data.");
        else
            Popup.infoBox("Success!", "Products saved!");
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}