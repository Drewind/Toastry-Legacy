package Graphics.Text;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NOTINUSE extends JPanel {
    private final Font FONT = new Font("Arial", Font.PLAIN, 18);
    private final int WIDTH = 160;
    private final int HEIGHT = 24;
    private JLabel label = new JLabel();

    public NOTINUSE(String text) {
        super(new GridBagLayout());

        this.label.setOpaque(false);
        this.label.setText(text);
        this.label.setForeground(new Color(10, 12, 14));
        super.add(this.label, setGBC(0, 1.0, 1.0));

        setDefaults();
    }

    public NOTINUSE(String text, Color bgColor) {
        super(new GridBagLayout());

        this.label.setBackground(bgColor);
        this.label.setOpaque(true);
        this.label.setText(text);
        this.label.setForeground(new Color(10, 12, 14));
        super.add(this.label, setGBC(0, 1.0, 1.0));

        setDefaults();
    }

    public void setDefaults() {
        super.setOpaque(false);

        this.label.setVisible(true);
        this.label.setFont(this.FONT);
        this.label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this.label.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }

    private GridBagConstraints setGBC(int x, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        return gbc;
    }

    public JLabel getLabel() {
        return this.label;
    }
}
