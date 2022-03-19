package Views.Home.Partials;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Graphics.Text.RegularText;
import Graphics.Text.RegularBoldText;
import Graphics.Text.TitleBox;
import Utilities.Styler;
import Utilities.SwingObjectText;
import Utilities.FormatText;
import Interfaces.ModelObserver;
import Interfaces.ViewObserver;
import Models.DailyStatsModel;

public class DailyStats extends JPanel implements ViewObserver {
    private final ModelObserver modelObserver;
    private final DailyStatsModel model;
    private RegularBoldText currentDay = new RegularBoldText("0", JLabel.RIGHT) {{ setName("Day"); }};
    private RegularBoldText dailySales = new RegularBoldText("0", JLabel.RIGHT) {{ setName("Sales"); }};
    private RegularBoldText dailyRevenue = new RegularBoldText("0", JLabel.RIGHT) {{ setName("Revenue"); }};
    private RegularBoldText expenses = new RegularBoldText("0", JLabel.RIGHT) {{ setName("Expenses"); }};
    private RegularBoldText profit = new RegularBoldText("0", JLabel.RIGHT) {{ setName("Profit"); }};
    private final ArrayList<JComponent> FIELDS = new ArrayList<JComponent>() {{
        add(currentDay);
        add(dailySales);
        add(dailyRevenue);
        add(expenses);
        add(profit);
    }};
    
    public DailyStats(ModelObserver modelObserver) {
        super(new BorderLayout());
        super.setBackground(Styler.CONTAINER_BACKGROUND);
        super.setForeground(new Color(250, 250, 250));

        this.modelObserver = modelObserver;
        this.modelObserver.registerObserver((ViewObserver)this);
        this.model = ((DailyStatsModel)modelObserver);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.setOpaque(false);
        for (JComponent field : this.FIELDS) {
            centerPanel.add(new RegularText(field.getName()));
            centerPanel.add(field);
        }

        super.add(new TitleBox("Daily Stats", Styler.DARK_SHADE2_COLOR), BorderLayout.PAGE_START);
        super.add(centerPanel, BorderLayout.CENTER);

        notifyObserver(); // Call our notifier so we can initialize it with correct values.
    }

    @Override
    /**
     * Receives notifications from DailyStatsModel when changes are made.
     * This partial view will then update the fields according to the model.
     * @return void
     */
    public void notifyObserver() {
        SwingObjectText.setSwingObjectText(this.FIELDS.get(0), Integer.toString(this.model.getCurrentDay()));
        SwingObjectText.setSwingObjectText(this.FIELDS.get(1), FormatText.format(this.model.getDailySales(), 0, true));
        SwingObjectText.setSwingObjectText(this.FIELDS.get(2), FormatText.format(this.model.getRevenues(), 2, true));
        SwingObjectText.setSwingObjectText(this.FIELDS.get(3), FormatText.format(this.model.getExpenses(), 2, true));
        SwingObjectText.setSwingObjectText(this.FIELDS.get(4), FormatText.format(this.model.getProfit(), 2, true));

        if (Double.parseDouble(SwingObjectText.getSwingObjectText(this.FIELDS.get(3)).replaceAll("[^0-9]", "")) > 0.0)
            this.FIELDS.get(3).setForeground(Color.BLUE);
        else
            this.FIELDS.get(3).setForeground(Color.RED);

        // this.revalidate(); // this works. @todo uncomment this
    }
}
