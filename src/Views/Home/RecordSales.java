package Views.Home;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controllers.HomeController;
import Entities.Product;
import Graphics.Text.TitleBox;
import Interfaces.ControllerInterface;
import Interfaces.ViewObserver;
import Models.IModelInterface;
import Models.ProductModel;
import Utilities.Styler;

public class RecordSales extends JPanel implements ViewObserver {
    private final ProductModel model;

    public RecordSales(ControllerInterface controller, IModelInterface<Product> model, SalesTable table) {
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

        super.add(new TitleBox("RECORD SALES", Styler.DARK_SHADE2_COLOR), BorderLayout.NORTH);

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
