package Utilities;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

public class GBC {
    private static final int LAYOUT_MARGIN_X = 22;
    private static final int LAYOUT_MARGIN_Y = 16;

    /**
     * Used to set the layout constraints for an element.
     * Defaults to weighty = 0.0 with gridwidth and gridheight = 1.
     * @param gbc GridBagConstraints
     * @param int x
     * @param int y
     * @param double weightx
     * @return GridBagConstraints
     */
    public static GridBagConstraints setGBC(final GridBagConstraints gbc, final int x, final int y, final double weight) {
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = weight;
        gbc.weighty = 0.0;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
    
    /**
     * Adjusts the default layout margins to account for padding.
     * @param PADDING_X
     * @param PADDING_Y
     * @return Insets
     */
    public static Insets setInsetsWithPadding(final int PADDING_X, final int PADDING_Y) {
        return new Insets(
            LAYOUT_MARGIN_Y + PADDING_Y, // Top
            LAYOUT_MARGIN_X + PADDING_X, // Left
            LAYOUT_MARGIN_Y + PADDING_Y, // Bottom
            LAYOUT_MARGIN_X + PADDING_X // Right
        );
    }

    /**
     * Retrieves the application's main layout margins.
     * @return Insets
     */
    public static Insets getDefaultLayoutMargins() {
        return new Insets(LAYOUT_MARGIN_Y, LAYOUT_MARGIN_X, LAYOUT_MARGIN_Y, LAYOUT_MARGIN_X);
    }

    /**
     * Used to anchor a component to the bottom of the layout via GBC.SOUTH constraints. Be sure to set gridwidth
     * to something larger than the rest of the layout elements (value of two should work most of the time).
     * @param GridBagConstraints gbc
     * @param int x
     * @param int y
     * @param int gridwidth
     * @return GridBagConstraints
     */
    public static GridBagConstraints setToAnchorBottom(final GridBagConstraints gbc, final int x, final int y, final int gridwidth) {
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridheight = 1;
        gbc.gridwidth = gridwidth;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    /**
     * Simple method to return a blank panel to anchor to the bottom of a gridbag layout.
     * @return
     */
    public static JPanel anchorPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
}
