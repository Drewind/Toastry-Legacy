package Graphics;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import Graphics.Containers.OverviewContainer;
import Utilities.Styler;

/**
 * Container factory using the builder pattern.
 */
public class OverviewBuilder {
    private Color _containerColor = Styler.CONTAINER_BACKGROUND; // Defaults to the standard container bg.
    private HashMap<Double, String> _content;
    private Font _numberFont = new Font("Arial", Font.BOLD, 28);
    private Font _labelFont = new Font("Arial", Font.PLAIN, 16);
    private boolean _formatCurrency = true;
    
    public OverviewBuilder() {}

    public OverviewContainer buildContainer() {
        return new OverviewContainer(_containerColor, _content, _numberFont, _labelFont, _formatCurrency);
    }

    public OverviewBuilder bgColor(Color _containerColor) {
        this._containerColor = _containerColor;
        return this;
    }

    public OverviewBuilder content(HashMap<Double, String> _content) {
        this._content = _content;
        return this;
    }

    public OverviewBuilder numberFont(Font _font) {
        this._numberFont = _font;
        return this;
    }

    public OverviewBuilder labelFont(Font _font) {
        this._labelFont = _font;
        return this;
    }

    public OverviewBuilder useCurrencyFormat(boolean _formatCurrency) {
        this._formatCurrency = _formatCurrency;
        return this;
    }
}