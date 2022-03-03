package Views;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import Controllers.ViewActionInterface;
import Entities.Location;
import Graphics.TabButton;
import Models.IModelInterface;
import Models.LocationModel;
import Utilities.GBC;
import Utilities.Styler;

@SuppressWarnings("serial")
/**
 * NavbarCreation
 * Seeing that this class is super small, just going to render everything inside this controller action.
 * Unless a need arises to render this as a partial that is.
 */
public class NavbarViewAction extends JPanel implements ViewActionInterface {
    private final LocationModel model;
    private final int TAB_WIDTH = 160;
    private final int TAB_HEIGHT = 50;
    private final Dimension TAB_SIZE = new Dimension(this.TAB_WIDTH, this.TAB_HEIGHT - 18);

    public NavbarViewAction(IModelInterface<?> model) {
        this.model = (LocationModel)model;
    }

    @Override
    public JPanel renderView() {
        super.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        
        //super.setLayout(new FlowLayout(FlowLayout.LEFT));

        super.setBackground(Styler.NAV_COLOR);
        super.setPreferredSize(new Dimension(0, this.TAB_HEIGHT));

        int i = 0;
        for (Location locations : model.getEntities()) {
            TabButton newButton = new TabButton(locations.getLocationName(), this.TAB_SIZE);
            if (i == 0)
                newButton.setAsActive();

            GBC.setGBC(gbc, i, 0, 0);
            super.add(newButton, gbc);
            i++;
        }

        TabButton addButton = new TabButton("+", new Dimension(60, this.TAB_HEIGHT - 18));
        GBC.setGBC(gbc, i++, 0, 0);
        super.add(addButton, gbc);

        JPanel anchorPanel = new JPanel();
        anchorPanel.setOpaque(false);
        GBC.setGBC(gbc, i++, 0, 1.0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 1.0;
        super.add(anchorPanel, gbc);

        return this;
    }
}