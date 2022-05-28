package Views.Home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.HomeController;
import Entities.Entity;
import Graphics.ContainerBuilder;
import Graphics.OverviewBuilder;
import Graphics.TabButton;
import Interfaces.ViewActionObserver;
import Models.ProductModel;
import Services.CalculateTotals;
import Utilities.GBC;

public class Dashboard extends JPanel implements ViewActionObserver {
    private GridBagConstraints gbc = new GridBagConstraints();
    private final ProductModel model;
    private final TMTopSales tableModel;
    private final CalculateTotals calculateService;
    private final HomeController controller;

    private JLabel grossSalesLabel;
    private JLabel totalSalesLabel;
    private JLabel netSalesLabel;
    private JLabel emptyLabel;

    public Dashboard(HomeController controller, ProductModel model) {
        super(new GridBagLayout());
        super.setVisible(true);
        super.setOpaque(false);
        this.controller = controller;
        this.model = model;
        this.tableModel = new TMTopSales(this.model.getEntities());
        this.calculateService = new CalculateTotals(this.model);
        this.model.registerObserver(this);

        // this.grossSalesLabel = new JLabel(String.format("%.2f", 354873.3523));
        this.emptyLabel = new JLabel(String.format("%d", this.calculateService.computeTotalSales()));
        this.totalSalesLabel = new JLabel(String.format("%d", this.calculateService.computeTotalSales()));
        this.netSalesLabel = new JLabel("$" + String.format("%.2f", this.calculateService.computeNetSales()));
        this.grossSalesLabel = new JLabel("$" + String.format("%,.2f", this.calculateService.computeGrossSales()));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = GBC.getDefaultLayoutMargins();

        super.add(summarySection(), GBC.setGBC(gbc, 0, 0, 1.0));
        super.add(productSection(), GBC.setGBC(gbc, 0, 1, 1.0));
        super.add(testSection(), GBC.setGBC(gbc, 0, 2, 1.0));
        super.add(GBC.anchorPanel(), GBC.setToAnchorBottom(gbc, 0, 3, 2));
    }

    private JPanel summarySection() {
        JPanel section = new JPanel(new GridBagLayout());
        section.setOpaque(false);
        int i = 0;

        this.gbc.anchor = GridBagConstraints.NORTH;
        OverviewBuilder totalSalesBuilder = new OverviewBuilder();
        section.add(totalSalesBuilder.content(this.totalSalesLabel).description("Total Sales").buildContainer(), GBC.setGBC(gbc, i++, 0, 1.0));
        
        section.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section

        OverviewBuilder emptyBoxBuilder = new OverviewBuilder();
        section.add(emptyBoxBuilder.content(this.emptyLabel).description("Empty Label").buildContainer(), GBC.setGBC(gbc, i++, 0, 1.0));
        
        section.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section

        OverviewBuilder grossSalesBuilder = new OverviewBuilder();
        section.add(grossSalesBuilder.content(this.grossSalesLabel).description("Gross Sales").buildContainer(), GBC.setGBC(gbc, i++, 0, 1.0));
        section.add(GBC.anchorPanel(), GBC.setGBC(gbc, i++, 0, 1.0)); // Middle section

        OverviewBuilder netSalesBuilder = new OverviewBuilder();
        section.add(netSalesBuilder.content(this.netSalesLabel).description("Net Sales").buildContainer(), GBC.setGBC(gbc, i++, 0, 1.0));

        return section;
    }

    private JPanel testSection() {
        JPanel section = new JPanel();

        // Button to go back to home screen
        TabButton backButton = new TabButton("Run Test");
        backButton.setAsActive();
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.testMethod();
            }
        });
        section.add(backButton);

        return section;
    }

    private JPanel productSection() {
        ContainerBuilder builder = new ContainerBuilder().header("Best Sellers").description("Popular menu items.");
        JPanel section = new JPanel(new GridBagLayout());
        int i = 0;

        this.gbc.anchor = GridBagConstraints.NORTHWEST;
        this.gbc.fill = GridBagConstraints.BOTH;
        StandardTable table = new StandardTable(tableModel);
        table.getPanel().setMinimumSize(new Dimension(0, 100));
        table.getPanel().setPreferredSize(new Dimension(0, 150));
        table.getPanel().setMaximumSize(new Dimension(0, 300));
        section.add(table.getPanel(), GBC.setGBC(gbc, i++, 0, 1.0));

        return builder.content(section).buildContainer();
    }

    @Override
    public void notifyNewEntity(Entity entity) {
        this.totalSalesLabel.setText(String.format("%d", this.calculateService.computeTotalSales()));
    }

    @Override
    public void notifyRemovedEntity(Entity entity) {
        this.totalSalesLabel.setText(String.format("%d", this.calculateService.computeTotalSales()));
    }

    @Override
    public void notifyModifiedEntity(Entity entity) {
        this.totalSalesLabel.setText(String.format("%d", this.calculateService.computeTotalSales()));
        // this.totalSalesLabel.revalidate();
        this.revalidate();
    }
}
