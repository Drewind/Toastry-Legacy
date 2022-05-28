package Views.Home.Partials;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import Controllers.HomeController;
import Entities.Product;
import Graphics.Text.RegularDescription;
import Graphics.Text.TitleBox;
import Interfaces.ControllerInterface;
import Interfaces.ViewObserver;
import Models.IModelInterface;
import Models.ProductModel;
import Utilities.Styler;
import Views.Home.StandardTable;

public class RecordSales extends JPanel implements ViewObserver {
    private final ProductModel model;

    public RecordSales(ControllerInterface controller, IModelInterface<Product> model, StandardTable table) {
        super(new BorderLayout());
        super.setMinimumSize(new Dimension(0, 140));
        super.setVisible(true);
        super.setOpaque(true);
        this.model = (ProductModel)model;
        this.populateTable();

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit.setText("Save Changes");
                ((HomeController)controller).persistSaleChanges();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(new TitleBox("RECORD SALES", Styler.DARK_SHADE2_COLOR));
        topPanel.add(new RegularDescription("Enter sales for today and click 'End Day'."));

        super.add(topPanel, BorderLayout.NORTH);
        super.add(table.getPanel(), BorderLayout.CENTER); // testing, new concept
        super.add(submit, BorderLayout.SOUTH);
    }

    /**
     * populateTable
     * Loads in the data to the view.
     * @return void
     */
    public void populateTable() {
    }

    @Override
    public void notifyObserver() {
    }
}
