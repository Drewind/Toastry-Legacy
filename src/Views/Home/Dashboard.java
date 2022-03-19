package Views.Home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import Graphics.ContainerBuilder;
import Graphics.OverviewBuilder;
import Models.DailyStatsModel;
import Models.IModelInterface;
import Models.ProductModel;
import Services.CalculateTotals;
import Utilities.GBC;
import Views.Home.Partials.DailyStats;
import Views.Home.Partials.TopSales;

public class Dashboard extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private final DailyStatsModel stats;
    private final ProductModel model;
    private final TMTopSales tableModel;
    private final CalculateTotals calculateService;

    public Dashboard(DailyStatsModel stats, IModelInterface<?> model) {
        super(new GridBagLayout());
        super.setVisible(true);
        super.setOpaque(false);
        this.stats = stats;
        this.model = (ProductModel)model;
        this.tableModel = new TMTopSales(this.model.getEntities());
        this.calculateService = new CalculateTotals(this.model);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();

        super.add(summarySection(), GBC.setGBC(gbc, 0, 0, 1.0));
        super.add(productSection(), GBC.setGBC(gbc, 0, 1, 1.0));
        super.add(GBC.anchorPanel(), GBC.setToAnchorBottom(gbc, 0, 2, 2));
    }

    private JPanel summarySection() {
        JPanel section = new JPanel(new GridBagLayout());
        section.setOpaque(false);
        int i = 0;

        this.gbc.anchor = GridBagConstraints.NORTHWEST;
        OverviewBuilder builder = new OverviewBuilder();
        section.add(builder.content(new HashMap<Double, String>() {{
            put(calculateService.computeGrossSales(), "Gross Sales");
            put(calculateService.computeNetSales(), "Net Sales");
        }}).buildContainer());

        section.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section

        section.add(builder.content(new HashMap<Double, String>() {{
            put(Double.parseDouble(Integer.toString(calculateService.computeTotalSales())), "Total Orders");
        }}).useCurrencyFormat(false).buildContainer());

        section.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section

        section.add(builder.content(new HashMap<Double, String>() {{
            put(75.0, "Total Visitors"); // @todo revisit this
        }}).useCurrencyFormat(false).buildContainer());

        section.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section

        this.gbc.anchor = GridBagConstraints.CENTER;
        section.add(builder.content(new HashMap<Double, String>() {{
            put(78.32, "AVG Order"); // @todo revisit this
        }}).buildContainer());

        return section;
    }

    private JPanel productSection() {
        ContainerBuilder builder = new ContainerBuilder().header("Best Sellers").description("Popular menu items.");
        JPanel section = new JPanel(new GridBagLayout());
        int i = 0;

        this.gbc.anchor = GridBagConstraints.NORTHWEST;
        section.add(new SalesTable(tableModel), GBC.setGBC(gbc, i++, 0, 1.0));

        return builder.content(section).buildContainer();
    }
}
