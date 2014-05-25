package gui;

import javax.swing.JPanel;

/**
 * A felugró ablakokhoz használt panel.
 * @author Lorant
 *
 */
public abstract class ValidatablePanel extends JPanel  {
    private String validationMassage = "";

    protected void addToValidationMassage(String s) {
        validationMassage = validationMassage + "\n" + s;
    }

    public String getValidationMassage() {
        return validationMassage;
    }

    public void emptyValidationMassage() {
        validationMassage = "";
    }
    
    public abstract boolean doValidation() ;
	
}