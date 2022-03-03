package Views.Home;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

public class SalesTable extends JTable {
    private final AbstractTableModel tableModel;
    private final JScrollPane panel;

    /**
     * Creates a SalesTable based of an array of column names and a AbstractTabelModel.
     * @param COLUMNS String[] of column names
     * @param tableModel AbstractTableModel; the model of this table
     */
    public SalesTable(AbstractTableModel tableModel) {
        this.tableModel = tableModel;
        super.setModel(this.tableModel);
        super.setRowHeight(24);
        super.setAutoCreateRowSorter(true);

        // Cell padding
        int gapWidth = 10;
        int gapHeight = 4;
        super.setIntercellSpacing(new Dimension(gapWidth, gapHeight));

        // Creation
        this.panel = new JScrollPane(this);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    /**
     * getPanel
     * Returns the scroll panel.
     * @return JScrollPane
     */
    public JScrollPane getPanel() {
        return this.panel;
    }
}
