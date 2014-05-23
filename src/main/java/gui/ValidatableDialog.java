package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ValidatableDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;
    private ValidatablePanel panel;
    private Frame owner;
    private boolean pressedYes;

    /**
     * Returns null if the typed string was invalid; otherwise, returns the
     * string as the user entered it.
     */
    public JPanel getValidatedPanel() {
        return panel;
    }

    /**
     * Creates the reusable dialog.
     */
    public ValidatableDialog(Frame owner, ValidatablePanel panel, String title) {
        super(owner, true);
        setTitle(title);
        this.panel = panel;
        this.owner = owner;

        //Create an array specifying the number of dialog buttons
        //and their text.
        String[] options = {"Save", "Cancel"};
        optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
        setContentPane(optionPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        optionPane.setValue("Save");
    }

    /**
     * This method reacts to state changes in the option pane.
     */
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

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

            if ("Save".equals(value)) {
                if (panel.doValidation()) {
                    //valid
                    pressedYes = true;
                    exit();
                } else {
                    JOptionPane.showMessageDialog(owner, panel.getValidationMassage());
                    panel.emptyValidationMassage();

                    //invalid
                }
            } else { //user closed dialog or clicked cancel
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