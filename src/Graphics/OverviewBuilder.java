package Graphics;

import java.awt.Color;

import javax.swing.JLabel;

import Graphics.Containers.OverviewContainer;
import Utilities.Styler;

/**
 * Container factory using the builder pattern.
 */
public class OverviewBuilder {
    private Color _containerColor = Styler.CONTAINER_BACKGROUND;    // Defaults to the standard container bg.
    private Color _borderColor = new Color(                         // Defaults to a lighter shade of the app background.
            Styler.APP_BG_COLOR.getRed() + 20, Styler.APP_BG_COLOR.getGreen() + 20, Styler.APP_BG_COLOR.getBlue() + 20);
    private JLabel _content;
    private String _description;
    private boolean _formatCurrency = true;
    
    public OverviewBuilder() {}

    public OverviewContainer buildContainer() {
        return new OverviewContainer(_containerColor, _borderColor, _content, _description, _formatCurrency);
    }

    public OverviewBuilder bgColor(Color _containerColor) {
        this._containerColor = _containerColor;
        return this;
    }

    public OverviewBuilder borderColor(Color _borderColor) {
        this._borderColor = _borderColor;
        return this;
    }

    public OverviewBuilder content(JLabel _content) {
        this._content = _content;
        return this;
    }

    public OverviewBuilder description(String text) {
        this._description = text;
        return this;
    }

    public OverviewBuilder useCurrencyFormat(boolean _formatCurrency) {
        this._formatCurrency = _formatCurrency;
        return this;
    }
}