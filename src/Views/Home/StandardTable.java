package Views.Home;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

/**
 * Creates a JTable object mapped with the input AbstractTableModel.
 * 
 * Two ways to use this class: headless or with headers. To use this
 * partial without headers, simply instantiate SalesTable and add it
 * as usual. With headers, instantiate the class and obtain the
 * JScrollPane (panel) via getPanel().
 */
public class StandardTable extends JTable {
    private final AbstractTableModel tableModel;
    private final JScrollPane panel;

    /**
     * Creates a standard table view based of an array of column names and a AbstractTabelModel.
     * @param COLUMNS String[] of column names
     * @param tableModel AbstractTableModel; the model of this table
     */
    public StandardTable(AbstractTableModel tableModel) {
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
     * Returns the scroll panel. Use this method to display table headers.
     * @return JScrollPane
     */
    public JScrollPane getPanel() {
        return this.panel;
    }
}
