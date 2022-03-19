package Views;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import Controllers.ViewActionInterface;
import Utilities.GBC;
import Utilities.Styler;
import java.awt.Font;
import java.awt.Insets;

/**
 * NavbarCreation
 * Seeing that this class is super small, just going to render everything inside this controller action.
 * Unless a need arises to render this as a partial that is.
 */
public class NavbarViewAction extends JPanel implements ViewActionInterface {
    private final int PANEL_HEIGHT = 50;
    private final Font LOGO_FONT = new Font("Arial", Font.BOLD, 28);
    private final Font DATE_FONT = new Font("Arial", Font.PLAIN, 20);
    private final Color TEXT_COLOR = new Color(250, 250, 250);

    @Override
    public JPanel renderView() {
        super.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;

        super.setBackground(Styler.NAV_COLOR);
        super.setPreferredSize(new Dimension(0, this.PANEL_HEIGHT));

        // Logo
        JLabel logoText = new JLabel("Toastry");
        logoText.setFont(LOGO_FONT);
        logoText.setOpaque(false);
        logoText.setForeground(TEXT_COLOR);

        gbc.insets = new Insets(0, 40, 0, 40);
        gbc.anchor = GridBagConstraints.WEST;
        super.add(logoText, GBC.setGBC(gbc, 0, 0, 0.5));

        // Date
        JLabel date = new JLabel("3/17/2022");
        date.setFont(DATE_FONT);
        date.setOpaque(false);
        date.setForeground(TEXT_COLOR);

        gbc.insets = new Insets(0, 0, 0, 20);
        gbc.anchor = GridBagConstraints.EAST;
        super.add(date, GBC.setGBC(gbc, 1, 0, 0.0));

        // Anchor
        // gbc.anchor = GridBagConstraints.EAST;
        // gbc.weighty = 1.0;
        // super.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0));

        return this;
    }
}