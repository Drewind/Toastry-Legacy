package Views.Home;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import Graphics.Text.RegularText;
import Graphics.Text.TitleBox;
import Utilities.Styler;

@SuppressWarnings("serial")
public class RecordExpenses extends JPanel {
    public RecordExpenses() {
        super(new BorderLayout());
        super.setVisible(true);
        super.setOpaque(true);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 2));
        controlPanel.setVisible(true);
        controlPanel.setBackground(Styler.APP_BG_COLOR);
        controlPanel.add(new RegularText("Cost of Ingredients"));
        controlPanel.add(new JSpinner());
        controlPanel.add(new RegularText("Salaries & Wages"));
        controlPanel.add(new JSpinner());

        controlPanel.add(new RegularText("Cost of Ingredients"));
        controlPanel.add(new JSpinner());
        controlPanel.add(new RegularText("Salaries & Wages"));
        controlPanel.add(new JSpinner());

        super.add(new TitleBox("Record Expenses", Styler.DARK_SHADE2_COLOR), BorderLayout.PAGE_START);
        super.add(controlPanel, BorderLayout.CENTER);
    }
}
