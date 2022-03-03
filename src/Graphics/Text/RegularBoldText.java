package Graphics.Text;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class RegularBoldText extends JLabel {
    private final Font REGULAR_FONT = new Font("Arial", Font.BOLD, 12);

    public RegularBoldText(String message) {
        super(message);
        this.setDefaults();
    }

    public RegularBoldText(String text, int alignment) {
        super(text);
        this.setDefaults();
        this.setHorizontalAlignment(alignment);
    }

    public void setDefaults() {
        this.setOpaque(false);
        this.setFont(this.REGULAR_FONT);
        this.setForeground(new Color(0, 0, 0));
    }

    public void setText(String text) {
        super.setText(text);
    }
}