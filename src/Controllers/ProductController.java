package Controllers;

import java.awt.CardLayout;

import javax.swing.JPanel;

import Interfaces.ControllerInterface;
import Constants.ActiveController;
import Models.Model;
import Views.ProductViewAction;

public class ProductController implements ControllerInterface {
    private Model model;
    private ProductViewAction creationView;
    private final JPanel contentPane;

    public ProductController(Model model, final JPanel contentPane) {
        this.model = model;
        this.contentPane = contentPane;

        // Views
        this.creationView = new ProductViewAction(this, model);
        this.creationView.renderView();
    }

    /**
     * Switches controller to HomeController. By default, will show the default view
     * assigned to HomeController.
     * @return void
     */
    public void switchToHomeCreation() {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, ActiveController.HOME.toString());
        this.creationView.resetForm();
    }

    /**
     * Controller action to update CreateForm.java based on an existing product ID.
     * @param productID Integer UID of the product
     * @return void
     */
    public void editProductScreen(String UID) {
        // Product product = (Product)this.model.getEntity(productID);
        // if (product != null) {
        //     this.creationView.editProductScreen(product);
        // }
    }

    public void submitProductForm() {
        // Product product = this.creationView.getProductFromForm();

        // if (this.model.entityExists(product.getID())) {
        //     if (this.model.editEntity(product)) {
        //         Popup.infoBox("Success!", "Product edited!");
        //     } else {
        //         Popup.infoBox("Error!", "Unfortunately, an exception occurred while saving.");
        //     }
        // } else {
        //     if (this.model.createEntity(product)) {
        //         Popup.infoBox("Success!", "Product created and saved!");
        //     } else {
        //         Popup.infoBox("Error!", "Unfortunately, an exception occurred while saving.");
        //     }
        // }
        
        // this.creationView.resetForm();
    }

    /**
     * Deletes a product from the model. Takes in a unique ID (UID) to
     * fetch the entity from the model.
     * @param UID int
     * @return void
     */
    public void deleteProduct(String UID) {
        // if (this.model.entityExists(UID)) {
        //     if (this.model.deleteEntity(this.model.getEntity(UID))) {
        //         Popup.infoBox("Success!", "Product was removed!");
        //         return;
        //     }
        // } else {
        //     System.out.println("Warning: ProductController could not delete entity "
        //         + "with ID of #" + UID + " becaues it doesn't exist.");
        // }

        // Popup.infoBox("Error!", "Unfortunately, an exception occurred while deleting entity.");
    }

    @Override
    public JPanel getDefaultView() {
        return creationView.renderView();
    }
}