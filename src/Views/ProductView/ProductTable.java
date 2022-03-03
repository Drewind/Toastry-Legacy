package Views.ProductView;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controllers.ProductController;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import Entities.Product;
import Graphics.TabButton;
import Graphics.Text.RegularBoldText;
import Graphics.Text.RegularText;
import Graphics.Text.TitleBox;
import Utilities.GBC;
import Utilities.Styler;

public class ProductTable extends JScrollPane {
    private final String[] COLUMNS = {"Dish", "Category", "Options"};
    private final ArrayList<Product> data; // View data only
    private final ProductController controller;
    
    public ProductTable(ArrayList<Product> productData, ProductController controller) {
        JPanel table = new JPanel(new BorderLayout());
        table.setVisible(true);
        table.setOpaque(true);
        this.data = productData;
        this.controller = controller;

        // Display the data
        table.add(new TitleBox("PRODUCTS", Styler.DARK_SHADE2_COLOR), BorderLayout.NORTH);
        table.add(this.populateTable(), BorderLayout.CENTER);

        // Add the table to the ScrollPane
        super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        super.setMinimumSize(new Dimension(0, 300));
        super.setPreferredSize(new Dimension(0, 300));
        super.setViewportView(table);
    }

    private JPanel populateTable() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Styler.APP_BG_COLOR);
        int rows = 0;

        // GBC
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = Styler.DEFAULT_MARGINS;

        // Headers
        for (int i = 0; i < this.COLUMNS.length; i++) {
            GBC.setGBC(gbc, i, 0, 0.0);
            // Anchor to right if last column
            if (i == this.COLUMNS.length - 1) {
                GBC.setGBC(gbc, i, 0, 1.0);
                gbc.fill = GridBagConstraints.NONE;
                gbc.anchor = GridBagConstraints.EAST;
            }

            container.add(new RegularBoldText(this.COLUMNS[i]), gbc);
        }

        // Generate rows
        for (int i = 0; i < this.data.size(); i++) {
            TabButton viewProduct = new TabButton("View");
            viewProduct.setName(String.valueOf(i));
            viewProduct.setSize(new Dimension(0, 150));
            viewProduct.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.editProductScreen(Integer.parseInt(viewProduct.getName()));
                }
            });

            TabButton deleteProduct = new TabButton("Delete", Styler.DangerColor());
            deleteProduct.setForeground(Color.WHITE);
            deleteProduct.setName(String.valueOf(i));
            deleteProduct.setSize(new Dimension(0, 150));
            deleteProduct.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.deleteProduct(Integer.parseInt(deleteProduct.getName()));
                }
            });

            // Name
            GBC.setGBC(gbc, 0, (i + 1), 0.0);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            container.add(new RegularText(this.data.get(i).getProductName()), gbc);

            // Category
            GBC.setGBC(gbc, 1, (i + 1), 0.1);
            gbc.fill = GridBagConstraints.NONE;
            container.add(new RegularText((this.data.get(i).getCategory() != null ? this.data.get(i).getCategory().toString() : "not set")), gbc);

            // Buttons
            GBC.setGBC(gbc, 2, (i + 1), 1.0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;

            GBC.setGBC(gbc, 3, (i + 1), 1.0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;

            JPanel buttonRibbion = new JPanel(new FlowLayout(FlowLayout.RIGHT)) {{
                add(viewProduct);
                add(deleteProduct);
            }};
            container.add(buttonRibbion, gbc);

            rows++;
        }

        // Anchor
        GBC.setToAnchorBottom(gbc, 0, (rows + 2), 2); // +2, header column and one for next y space
        JPanel anchorPanel = new JPanel();
        anchorPanel.setOpaque(false);
        container.add(anchorPanel, gbc);
        
        return container;
    }
}