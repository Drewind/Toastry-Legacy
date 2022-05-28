package Views.ProductView;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entities.Product;
import Entities.ProductCategory;
import Graphics.NumberField;
import Graphics.Text.RegularBoldText;
import Graphics.Text.RegularText;
import Interfaces.FormInterface;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.GridBagConstraints;

import Utilities.GBC;
import Utilities.Styler;
import Utilities.SwingObjectText;

public class CreateForm extends JPanel implements FormInterface<Product> {
    RegularText idField = new RegularText("") {{
        setName("UID");
    }};

    JTextField nameField = new JTextField() {{
        setName("Name");
    }};

    NumberField priceField = new NumberField() {{
        setName("Price");
    }};

    NumberField costField = new NumberField() {{
        setName("Cost");
    }};

    JComboBox<ProductCategory> categoryField = new JComboBox<>(ProductCategory.values()) {{
        setName("Category");
    }};
    private final ArrayList<JComponent> FIELDS = new ArrayList<JComponent>() {{
        add(idField);
        add(nameField);
        add(priceField);
        add(costField);
        add(categoryField);
    }};

    public CreateForm() {
        super(new GridBagLayout());
        super.setBackground(Styler.CONTAINER_BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Short description
        GBC.setGBC(gbc, 0, 0, 1.0);
        gbc.gridwidth = 2;
        RegularText paragraph = new RegularText("Create a new product and set its properties.");
        super.add(paragraph, gbc);

        for (int i = 0; i < this.FIELDS.size(); i++) {
            // Component label
            GBC.setGBC(gbc, 0, (i + 1), 0.0);
            super.add(new RegularBoldText(this.FIELDS.get(i).getName()), gbc);

            GBC.setGBC(gbc, 1, (i + 1), 0.3);
            super.add(this.FIELDS.get(i), gbc);

            GBC.setGBC(gbc, 2, (i + 1), 1.0);
            super.add(new RegularText(""), gbc);
        }
    }

    @Override
    /**
     * Retrieves the form's input values and stores them as objects inside a HashMap.
     * If a value is null or empty, excludes that value from the returned results.
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> getInputs() {
        HashMap<String, Object> inputs = new HashMap<String, Object>();

        for (JComponent component : this.FIELDS) {
            if (SwingObjectText.getSwingObjectText(component) != null && SwingObjectText.getSwingObjectText(component) != "") {
                inputs.put(component.getName(), SwingObjectText.getSwingObjectText(component));
            } else {
                if (!component.getName().equals("UID"))
                    System.out.println("\n[ WARNING ] Form input field '" + component.getName() + "' was null.");
            }
        }

        return inputs;
    }

    @Override
    public void updateView(final Product product) {
        this.idField.setText(product.getID());
        this.nameField.setText(product.getProductName());
        this.priceField.setText(Double.toString(product.getPrice()));
        this.costField.setText(Double.toString(product.getCost()));
        this.categoryField.setSelectedItem(product.getCategory());

        this.revalidate();
    }

    @Override
    public void resetView() {
        for (JComponent component : this.FIELDS) {
            SwingObjectText.setSwingObjectText(component, "");
        }
    }
}