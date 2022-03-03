package Graphics.Events;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import java.util.EventObject;
import java.awt.event.*;
import javax.swing.DefaultCellEditor;
import javax.swing.SwingUtilities;
import java.awt.Component;

/**
 * @see https://stackoverflow.com/questions/1882400/is-there-a-convenient-way-to-use-a-spinner-as-an-editor-in-a-swing-jtable
 */
@SuppressWarnings("serial")
public class SpinnerEditor extends DefaultCellEditor {
    JSpinner spinner;
    JSpinner.DefaultEditor editor;
    JTextField textField;
    boolean valueSet;

    // Initializes the spinner.
    public SpinnerEditor() {
        super(new JTextField());
        spinner = new JSpinner();
        editor = ((JSpinner.DefaultEditor)spinner.getEditor());
        textField = editor.getTextField();
        textField.addFocusListener( new FocusListener() {
            public void focusGained( FocusEvent fe ) {
                //textField.setSelectionStart(0);
                //textField.setSelectionEnd(1);
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        if ( valueSet ) {
                            textField.setCaretPosition(1);
                        }
                    }
                });
            }
            public void focusLost( FocusEvent fe ) {
            }
        });
        textField.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent ae ) {
                stopCellEditing();
            }
        });
    }

    // Prepares the spinner component and returns it.
    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
        if ( !valueSet ) {
            spinner.setValue(value);
        }

        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                textField.requestFocus();
            }
        });
        return spinner;
    }

    public boolean isCellEditable( EventObject eo ) {
        if ( eo instanceof KeyEvent ) {
            KeyEvent ke = (KeyEvent)eo;
            textField.setText(String.valueOf(ke.getKeyChar()));
            //textField.select(1,1);
            //textField.setCaretPosition(1);
            //textField.moveCaretPosition(1);
            valueSet = true;
        } else {
            valueSet = false;
        }
        return true;
    }

    // Returns the spinners current value.
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    public boolean stopCellEditing() {
        try {
            editor.commitEdit();
            spinner.commitEdit();
        } catch ( java.text.ParseException e ) {
            JOptionPane.showMessageDialog(null,
                "Invalid value, discarding.");
        }
        return super.stopCellEditing();
    }
}