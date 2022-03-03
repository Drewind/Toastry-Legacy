package Views.ProductView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Controllers.ProductController;
import Graphics.TabButton;
import Graphics.Text.TitleBox;
import Models.ProductModel;
import Utilities.Styler;

public class Create extends JPanel {
    public Create(ProductModel model, ProductController controller, JPanel form, TabButton finishButton) {
        super(new BorderLayout());
        super.setVisible(true);

        // Header
        TitleBox title = new TitleBox("ADD PRODUCT", Styler.DARK_SHADE2_COLOR);
        super.add(title, BorderLayout.NORTH);

        // Form
        super.add(form, BorderLayout.CENTER);

        // Finish button
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.submitProductForm();
            }
        });

        TabButton cancelButton = new TabButton("Cancel", Styler.APP_BG_COLOR);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.switchToHomeCreation();
            }
        });

        JPanel buttonRibbion = new JPanel(new GridLayout(0, 2)) {{
            add(finishButton);
            add(cancelButton);
        }};
        super.add(buttonRibbion, BorderLayout.SOUTH);
    }

    /**
     * getWindow
     * Returns the parent of this JPanel.
     * @return Window
     */
    public Window getWindow() {
        return (Window) SwingUtilities.windowForComponent(this);
    }
}
