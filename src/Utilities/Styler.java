package Utilities;

import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Toolkit;
import java.awt.Insets;

/**
 * Utility class that houses theme colors.
 */
public class Styler {
    // private final static Color LIGHTER_COLOR = new Color(101, 158, 208); // 7DADD7
    // private final static Color LIGHT_COLOR = new Color(101, 158, 208); //659ED0
    private final static Color THEME_COLOR = new Color(75, 141, 199); // 4B8DC7
    // private final static Color DARK_COLOR = new Color(55, 122, 181); // 377AB5
    // private final static Color DARKER_COLOR = new Color(47, 104, 154); // 2F689A
    public final static Color DARK_SHADE2_COLOR = new Color(40, 88, 130); // 285882
    // private final static Color CONTAINER_COLOR = new Color(252, 252, 252);

    public final static Color NAV_COLOR = new Color(47, 104, 154); // 2F689A
    public final static Color CONTAINER_BACKGROUND = new Color(248, 248, 248); // Background of the window.
    public final static Color APP_BG_COLOR = new Color(225, 231, 231); // Background of the window.
    private final static Color DANGER_COLOR = new Color(234, 108, 81);

    public final static Insets DEFAULT_MARGINS = new Insets(12, 30, 12, 30); // Top, left, bottom, right

    // private final static int LAYOUT_MARGIN  = 30; // Side padding for main components
    // private final static int LAYOUT_PADDING = 45; // Bottom/top spacing for main components
    private final static int NAV_HEIGHT = 35;
    
    // private final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final Color PrimaryColor() {
        return THEME_COLOR;
    }

    public static final int NavHeight() {
        return NAV_HEIGHT;
    }

    public static final Color DangerColor() {
        return DANGER_COLOR;
    }
}
