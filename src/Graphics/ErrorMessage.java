package Graphics;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorMessage extends JDialog {
    public ErrorMessage(JFrame frame, String message) {
        super(frame, "Alert");
        this.add(new JLabel(message));
        this.pack();
    }
}