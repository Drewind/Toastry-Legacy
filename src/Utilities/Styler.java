package Utilities;

import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Toolkit;
import java.awt.Insets;
import java.text.DecimalFormat;

/**
 * Utility class that houses theme colors.
 */
public class Styler {
    public final static Color THEME_COLOR = new Color(75, 141, 199); // 4B8DC7
    public final static Color DARK_SHADE2_COLOR = new Color(40, 88, 130); // 285882

    public final static Color NAV_COLOR = new Color(47, 104, 154); // 2F689A
    public final static Color CONTAINER_BACKGROUND = new Color(248, 248, 248);  // Background of the window.
    public final static Color APP_BG_COLOR = new Color(225, 231, 231);          // Background of the window.
    public final static Color DANGER_COLOR = new Color(234, 108, 81);

    // private final static int LAYOUT_MARGIN  = 30; // Side padding for main components
    // private final static int LAYOUT_PADDING = 45; // Bottom/top spacing for main components
    private final static DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.##");
    private final static DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,###");
    
    // private final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final String formatCurrency(String text, boolean withDollarSign) {
        return (withDollarSign ? "$" + CURRENCY_FORMAT.format(text) : CURRENCY_FORMAT.format(text));
    }

    public static final String formatInteger(String text) {
        return INTEGER_FORMAT.format(text);
    }
}
