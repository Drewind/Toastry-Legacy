package Graphics.Text;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Color;

public class HeaderLabel extends JLabel {
    private final Color HEADER_COLOR = new Color(40, 88, 130); // 285882
    private final Color TEXT_COLOR = new Color(250, 250, 250); // Default text color

    /**
     * HeaderLabel
     * Default header label using the default background color.
     * @param text String
     */
    public HeaderLabel(String text) {
        super(text);
        this.setBackground(this.HEADER_COLOR);
        this.setDefaults();
    }

    public HeaderLabel(String text, Color bgColor) {
        super(text);
        this.setBackground(bgColor);
        this.setDefaults();
    }

    private void setDefaults() {
        this.setOpaque(true);
        this.setForeground(this.TEXT_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
    }
}