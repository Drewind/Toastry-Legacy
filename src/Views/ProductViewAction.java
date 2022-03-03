package Views;

import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

import Controllers.ProductController;
import Controllers.ViewActionInterface;
import Entities.Product;
import Entities.ProductCategory;
import Graphics.TabButton;
import Interfaces.ControllerInterface;
import Interfaces.FormInterface;
import Interfaces.ViewActionObserver;
import Models.IModelInterface;
import Models.ProductModel;
import Views.ProductView.Create;
import Views.ProductView.CreateForm;
import Views.ProductView.ProductsTable.ProductView;
import Views.ProductView.ProductsTable.TableModel;
import Utilities.GBC;
import Utilities.Styler;

@SuppressWarnings("unchecked")
public class ProductViewAction implements ViewActionInterface, ViewActionObserver<Product> {
    private final ProductModel model;
    private final ProductController controller;
    private FormInterface<Product> createForm;
    private HashMap<String, JComponent> views = new HashMap<>();
    private TableModel tableModel = new TableModel();

    public ProductViewAction(ControllerInterface controller, IModelInterface<?> model) {
        this.model = (ProductModel)model;
        this.controller = (ProductController)controller;

        this.model.registerObserver(this);
    }

    @Override
    public JPanel renderView() {
        JPanel container = new JPanel(new GridBagLayout());
        JPanel form = new CreateForm(); // Creating the form here so we can attach an observer to it
        this.createForm = (FormInterface<Product>)form;

        TabButton formButton = new TabButton("Add Product");
        this.views.put("FormButton", formButton);

        // Calls the create view, passing in the creation form.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();
        container.add(new Create(this.model, this.controller, form, formButton), GBC.setGBC(gbc, 0, 0, 1.0));

        // under dev
        ProductView hopingforthebest = new ProductView(this.controller, this.tableModel);
        for (Product products : this.model.getEntities())
            hopingforthebest.addRow(products);
        container.add(hopingforthebest, GBC.setGBC(gbc, 0, 1, 1.0));
        
        // Button to go back to home screen
        TabButton backButton = new TabButton("Go Back", Styler.APP_BG_COLOR);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.switchToHomeCreation();
                resetForm();
            }
        });

        gbc.anchor = GridBagConstraints.SOUTH;
        container.add(backButton, GBC.setGBC(gbc, 0, 2, 0.5));

        // Anchor
        GBC.setToAnchorBottom(gbc, 0, 3, 2);
        JPanel anchorPanel = new JPanel();
        anchorPanel.setOpaque(false);
        container.add(anchorPanel, gbc);

        return container;
    }

    /**
     * Controller Action which handles updating the form to modify an existing product.
     */
    public void editProductScreen(Product product) {
        this.createForm.updateView(product);
    }

    public void resetForm() {
        this.createForm.resetView();
    }

    public Product getProductFromForm() {
        HashMap<String, Object> formInputs = this.createForm.getInputs();
        Product product;

        if ((formInputs.get("UID") != null) && this.model.entityExists(
            Integer.parseInt(formInputs.get("UID").toString()))) {
                System.out.println("\nViewAction: modifying product.");
                final int UID = Integer.parseInt(formInputs.get("UID").toString());
                product = this.model.getEntity(UID);
        } else {
            System.out.println("\nViewAction: creating new product.");
            product = new Product(this.model.generateID());
        }

        // If using the consumer approach
        // int i = 0;
        // for (Consumer<Object> consumer : product.getLoadOrder()) {
        //     consumer.accept(formInputs.get(i));
        //     i++;
        // }

        product.setProductName(formInputs.containsKey("Name") ? formInputs.get("Name").toString() : "undefined");
        product.setCost(formInputs.containsKey("Cost") ? Double.parseDouble(formInputs.get("Cost").toString()) : 0.0);
        product.setPrice(formInputs.containsKey("Price") ? Double.parseDouble(formInputs.get("Price").toString()) : 0.0);
        product.setCategory(formInputs.containsKey("Category") ? ProductCategory.getEnum(formInputs.get("Category").toString()) : ProductCategory.MAIN_DISH);

        return product;
    }

    @Override
    public void notifyNewEntity(Product product) {
        this.tableModel.addRow(product);
    }

    @Override
    public void notifyRemovedEntity(Product product) {
        this.tableModel.removeRow(product.getID());
    }

    @Override
    public void notifyModifiedEntity(Product product) {
        this.tableModel.updateRow(product);
    }
}