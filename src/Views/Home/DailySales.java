package Views.Home;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Entities.Product;
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
public class DailySales extends JPanel implements ViewObserver {
    private JTable table;

    public DailySales(int height, ArrayList<Product> productData, ProductModel model, AbstractTableModel tableModel) {
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

        super.add(new TitleBox("DAILY SALES", Styler.DARK_SHADE2_COLOR), BorderLayout.NORTH);
        super.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void notifyObserver() {
    }
}