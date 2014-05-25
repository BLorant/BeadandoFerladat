package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * felugró ablakokhoz használt dialog.
 * @author Lorant
 *
 */
public class ValidatableDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;
    private ValidatablePanel panel;
    private Frame owner;
    private boolean pressedYes;

    public JPanel getValidatedPanel() {
        return panel;
    }

  
    public ValidatableDialog(Frame owner, ValidatablePanel panel, String title) {
        super(owner, true);
        setTitle(title);
        this.panel = panel;
        this.owner = owner;

        String[] options = {"Save", "Cancel"};
        optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
        setContentPane(optionPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

       
        optionPane.addPropertyChangeListener(this);
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        optionPane.setValue("Save");
    }


    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop)
                || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();
            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                return;
            }

   
            optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

            if ("Save".equals(value)) {
                if (panel.doValidation()) {

                    pressedYes = true;
                    exit();
                } else {
                    JOptionPane.showMessageDialog(owner, panel.getValidationMassage());
                    panel.emptyValidationMassage();

                }
            } else { 
                    pressedYes = false;
                    exit();
            }
        }
    }

    public static boolean show(Frame owner, ValidatablePanel panel, String title) {
        ValidatableDialog dialog = new ValidatableDialog(owner, panel, title);
        dialog.setVisible(true);
        return dialog.pressedYes;
    }

    public void exit() {
        dispose();
    }
}