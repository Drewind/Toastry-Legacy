package Graphics.Containers;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.ContainerInterface;
import Utilities.GBC;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public class OverviewContainer extends JPanel implements ContainerInterface {
    private GridBagConstraints gbc = new GridBagConstraints();
    private HashMap<Double, String> content;
    private final Font NUMBER_FONT;
    private final Font LABEL_FONT;
    private final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.##");
    private final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,###");
    private final Insets MARGINS = new Insets(0, 10, 0, 10);
    private boolean useCurrencyFormat;

    public OverviewContainer(Color bgColor, HashMap<Double, String> content,
            final Font numberFont, final Font labelFont, boolean formatCurrency) {
        super(new GridBagLayout());
        super.setBackground(bgColor);
        super.setOpaque(true);
        super.setVisible(true);
        this.content = content;
        this.NUMBER_FONT = numberFont;
        this.LABEL_FONT = labelFont;
        this.useCurrencyFormat = formatCurrency;

        layoutView();
    }

    /**
     * Initializes the container's layout, called from the constructor.
     */
    @Override
    public void layoutView() {
        int x = 0;
        int y = 0;
        this.gbc.anchor = GridBagConstraints.CENTER;

        // Place the number first followed by the label.
        for (Map.Entry<Double, String> entry : this.content.entrySet()) {
            JLabel numberLabel = new JLabel(
                (this.useCurrencyFormat ? "$" + CURRENCY_FORMAT.format(entry.getKey()) : INTEGER_FORMAT.format(entry.getKey())));
            JLabel textLabel = new JLabel(entry.getValue());
            numberLabel.setFont(NUMBER_FONT);
            textLabel.setFont(LABEL_FONT);
            numberLabel.setName(entry.getValue());

            this.gbc.insets = MARGINS;
            super.add(numberLabel, GBC.setGBC(this.gbc, x, y, 0.0));
            super.add(textLabel, GBC.setGBC(this.gbc, x, ++y, 0.0));

            x++;
            y--;
        }
    }

    @Override
    public JComponent getContent() {
        return this;
    }
}
