package Graphics;

import javax.swing.JToolTip;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.FontMetrics;

public class CustomToolTip extends JToolTip {
    public void paintComponent(Graphics g) {
        // create a round rectangle
        Shape round = new RoundRectangle2D.Float(4,4,
            this.getWidth( )-1-8,
            this.getHeight( )-1-8,
            15,15);
        // draw the white background
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.white);
        g2.fill(round);
        // draw the gray border
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(5));
        g2.draw(round);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        // draw the text
        String text = this.getComponent().getToolTipText( );
        if(text != null) {
            FontMetrics fm = g2.getFontMetrics( );
            int h = fm.getAscent( );
            g2.setColor(Color.black);
            g2.drawString(text,10,(this.getHeight( )+h)/2);
        }
    }
}