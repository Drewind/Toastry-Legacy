package Views.Home.Partials;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import Controllers.HomeController;
import Graphics.TabButton;
import Graphics.Text.TitleBox;
import Utilities.Styler;

public class Options extends JPanel {
    public Options(HomeController controller) {
        super(new BorderLayout());
        super.setBackground(Styler.CONTAINER_BACKGROUND);
        super.add(new TitleBox("Quick Options", Styler.DARK_SHADE2_COLOR), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        
        TabButton quickAddProduct = new TabButton("Add Product");
        quickAddProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.switchToProductCreation();
            }
        });

        TabButton endOfDayButton = new TabButton("End Day");
        endOfDayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.endDay();
            }
        });

        contentPanel.add(quickAddProduct);
        contentPanel.add(endOfDayButton);
        super.add(contentPanel, BorderLayout.CENTER);
    }
}
