package Views;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import Controllers.ViewActionInterface;
import Graphics.TabButton;
import Utilities.GBC;
import Utilities.Styler;
import java.awt.Font;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * NavbarCreation
 * Seeing that this class is super small, just going to render everything inside this controller action.
 * Unless a need arises to render this as a partial that is.
 */
public class NavbarViewAction extends JPanel implements ViewActionInterface {
    private final int PANEL_HEIGHT = 50;
    private final Font DATE_FONT = new Font("Arial", Font.PLAIN, 16);
    private final Color TEXT_COLOR = new Color(250, 250, 250);
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public JPanel renderView() {
        super.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        int i = 0;

        super.setBackground(Styler.NAV_COLOR);
        super.setPreferredSize(new Dimension(0, this.PANEL_HEIGHT));

        // Load & display application logo.
        try {
            BufferedImage logoImage = ImageIO.read(new File("media/logos/Toastry-logos_transparent.png"));
            Image logo = logoImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logo));
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 40, 0, 120); // Top, left, bottom, right
            super.add(logoLabel, GBC.setGBC(gbc, i++, 0, 0.0));
        } catch (IOException ex) {
            System.out.println("WARNING: Couldn't load logo image.");
        }

        // super.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 0.05));

        // Tabs
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 0, 0, 0);

        TabButton dashboardTab = new TabButton("Dashboard", new Dimension(0, this.PANEL_HEIGHT - 10));
        dashboardTab.setAsActive();
        super.add(dashboardTab, GBC.setGBC(gbc, i++, 0, 1.0));

        TabButton productsTab = new TabButton("Products", new Dimension(0, this.PANEL_HEIGHT - 10));
        super.add(productsTab, GBC.setGBC(gbc, i++, 0, 1.0));

        super.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section
        

        // Date
        JLabel date = new JLabel(LocalDate.now().format(DATE_FORMAT));
        date.setFont(DATE_FONT);
        date.setOpaque(false);
        date.setForeground(TEXT_COLOR);

        gbc.insets = new Insets(0, 0, 0, 20);
        gbc.anchor = GridBagConstraints.EAST;
        super.add(date, GBC.setGBC(gbc, i++, 0, 0.0));

        // Anchor
        // gbc.anchor = GridBagConstraints.EAST;
        // gbc.weighty = 1.0;
        // super.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0));

        return this;
    }
}