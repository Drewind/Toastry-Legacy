package Graphics.Text;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

public class RegularDescription extends JLabel {
    private final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 16);

    public RegularDescription(String text) {
        super(text);
        this.setDefaults();
    }

    public RegularDescription(String text, int alignment) {
        super(text);
        this.setDefaults();
        this.setHorizontalAlignment(alignment);
    }

    public RegularDescription(String text, Color bgColor) {
        super(text);
        this.setBackground(bgColor);
    }

    private void setDefaults() {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this.setForeground(new Color(6, 6, 6));
        this.setFont(this.REGULAR_FONT);
    }
}