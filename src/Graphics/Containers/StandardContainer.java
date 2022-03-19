package Graphics.Containers;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.ContainerInterface;
import Utilities.GBC;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;

public class StandardContainer extends JPanel implements ContainerInterface {
    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel header;
    private JLabel description;
    private JComponent content;

    public StandardContainer(Color bgColor, JLabel header, JLabel description, JComponent content) {
        super(new GridBagLayout());
        super.setBackground(bgColor);
        super.setOpaque(true);
        super.setVisible(true);
        this.header = (JLabel)header;
        this.description = (JLabel)description;
        this.content = (JComponent)content;

        layoutView();
    }

    /**
     * Initializes the container's layout, called from the constructor.
     */
    @Override
    public void layoutView() {
        int i = 0;
        this.gbc.anchor = GridBagConstraints.SOUTHWEST;
        this.gbc.fill = GridBagConstraints.HORIZONTAL;

        // If the header object is set, go ahead and lay it out first.
        if (this.header != null) {
            super.add(this.header, GBC.setGBC(this.gbc, 0, i++, 1.0));
        }

        // If the description is not null, place it next in the layout order.
        if (this.description != null) {
            super.add(this.description, GBC.setGBC(this.gbc, 0, i++, 1.0));
        }

        // Finally place the main content below.
        this.gbc.anchor = GridBagConstraints.NORTHWEST;
        this.gbc.fill = GridBagConstraints.BOTH;
        super.add(content, GBC.setGBC(this.gbc, 0, i++, 1.0));

        // And add a anchor panel to push the layout north.
        super.add(GBC.anchorPanel(), GBC.setToAnchorBottom(this.gbc, 0, i++, 2));
    }

    @Override
    public JComponent getContent() {
        return this.content;
    }
}
