package Graphics;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class NumberField extends JTextField {
    public static JFormattedTextField IntField() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false); // Remove comma from number greater than 4 digit
        NumberFormatter sleepFormatter = new NumberFormatter(format);
        sleepFormatter.setValueClass(Integer.class);
        sleepFormatter.setMinimum(0);
        // sleepFormatter.setMaximum(3600);
        sleepFormatter.setAllowsInvalid(false);

        sleepFormatter.setCommitsOnValidEdit(true); // Committ value on each keystroke instead of focus lost

        JFormattedTextField field = new JFormattedTextField(sleepFormatter);
        field.setText("0");

        return field;
    }
}
