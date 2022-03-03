package Graphics.Text;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

public class RegularText extends JLabel {
    private final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 12);

    public RegularText(String text) {
        super(text);
        this.setDefaults();
    }

    public RegularText(String text, int alignment) {
        super(text);
        this.setDefaults();
        this.setHorizontalAlignment(alignment);
    }

    public RegularText(String text, Color bgColor) {
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