package Graphics;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

public class TabButton extends JButton {
    private final Font FONT = new Font("Arial", Font.BOLD, 14);
    private final Color TEXT_COLOR = new Color(250, 250, 250); // Default text color
    private final Color ACTIVE_TEXT_COLOR = new Color(55, 122, 181); // 377AB5
    private final Color ACTIVE_BG_COLOR = new Color(240, 240, 240);
    private final Color BG_COLOR = new Color(40, 88, 130); // 285882

    private boolean isActive = false;

    /**
     * TabButton
     * Creates default button with template colors.
     * @param text String
     * @return TabButton
     */
    public TabButton(String text) {
        super(text);
        this.setDefaults();
    }

    public TabButton(String text, Color bgColor) {
        super(text);
        this.setDefaults();
        this.setBackground(bgColor);
    }

    /**
     * TabButton
     * Creates new tab button based on known dimensions. Defaults to inactive status.
     * @param text String
     * @param size Dimension of tab
     * @return TabButton
     */
    public TabButton(String text, Dimension size) {
        super(text);
        this.setDefaults();
        this.setPreferredSize(size);
        this.setMaximumSize(size);
    }

    private void setDefaults() {
        this.setBackground(this.BG_COLOR);
        this.setForeground(this.TEXT_COLOR);
        this.setFont(this.FONT);
        this.setVisible(true);
        this.setOpaque(true);
        this.setBorderPainted(false);
    }

    public void setAsActive() {
        this.isActive = true;
        this.setBackground(this.ACTIVE_BG_COLOR);
        this.setForeground(this.ACTIVE_TEXT_COLOR);
    }
}