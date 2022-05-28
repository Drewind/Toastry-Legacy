package Views.Home.Partials;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Entities.Product;
import Graphics.Text.RegularDescription;
import Graphics.Text.TitleBox;
import Interfaces.ViewObserver;
import Models.ProductModel;
import Utilities.Styler;

/**
 * dailySales
 * Panel that shows daily sales made.
 * @param height int
 * @param productData ArrayList<Product>
 * @return JPanel
 * @todo add in daily sales and growth percentage
*/
public class TopSales extends JPanel implements ViewObserver {
    private JTable table;

    public TopSales(int height, ArrayList<Product> productData, ProductModel model, AbstractTableModel tableModel) {
        super(new BorderLayout());
        super.setMinimumSize(new Dimension(0, height));
        super.setPreferredSize(new Dimension(0, height));
        // this.model.registerObserver((ViewObserver)this);

        this.table = new JTable(tableModel);
        this.table.setRowHeight(24);
        this.table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(new TitleBox("STORE FAVORITES", Styler.DARK_SHADE2_COLOR));
        topPanel.add(new RegularDescription("Best selling dishes from your restaurant."));

        super.add(topPanel, BorderLayout.NORTH);
        super.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void notifyObserver() {
    }
}