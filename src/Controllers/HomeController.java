package Controllers;

import javax.swing.JPanel;

import Constants.ActiveController;
import Interfaces.Command;
import Interfaces.ControllerInterface;
import Models.DailyStatsModel;
import Models.ProductModel;
import Services.Commands.ComputeEndOfDay;
import Views.HomeViewAction;
import Views.Popups.Popup;

import java.awt.CardLayout;

/**
 * Handles the home screen. Implements the standard ControllerInterface.
 */
public class HomeController implements ControllerInterface {
    private HomeViewAction creationView;
    private final JPanel contentPane;
    private final DailyStatsModel stats;
    private final ProductModel productModel;

    /**
     * Constructs a new HomeController controller.
     * @param model a reference to a IModelInterface with class <Product>
     * @param transModel a reference to a IModelInterface with class <Transaction>
     * @param statsModel a reference a DailyStatsModel object
     * @param contentPane a reference to the central content pane
     */
    public HomeController(
        ProductModel productModel,
        DailyStatsModel statsModel,
        JPanel contentPane) {
        
        this.contentPane = contentPane;
        this.stats = statsModel;
        this.productModel = productModel;

        // Views
        this.creationView = new HomeViewAction(this, productModel, statsModel);
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
     * Method to test functionality from views.
     */
    public void testMethod() {
        System.out.println("Test method called from controller.");

        // Product x = this.productModel.getEntity(0);
        // x.setDailySales(5);
        // x.computeEndOfDay();
        // this.model.editEntity(x);
    }

    /**
     * Ends the current day, saving all data entered.
     * @return void
     */
    public void endDay() {
        try {
            Command endOfDay = new ComputeEndOfDay(this.stats, this.productModel);
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
        // ArrayList<String> errors = new ArrayList<>(10);
        // int newID;

        // for (Product product : this.creationView.getProductChanges()) {
        //     if (!this.productModel.editEntity((Entity)product))
        //         errors.add("Couldn't save changes to product (#" + product.getID() + " " + product.getProductName() + ").");
        //     else {
        //         newID = this.transModel.generateID();
        //         if (!this.transModel.createEntity(new Transaction(newID, stats.getCurrentDay(), product)))
        //             errors.add("Exception thrown when saving transactions.");
        //     }
        // }

        // if (errors.size() > 0)
        //     Popup.infoBox("Error!", "Unfortunately, an exception occurred while trying to save data.");
        // else
        //     Popup.infoBox("Success!", "Products saved!");
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}