package gui;

import javax.swing.JPanel;

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