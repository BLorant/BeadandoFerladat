package gui;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Item;

/**
 * Az Itemek létrehozásához és karbantartásához használt felugró ablak.
 * @author Lorant
 *
 */
public class ItemPanel extends ValidatablePanel {
	
	private JTextField nameTextField;
	private JLabel nameLabel;
	private JTextField priceTextField;
	private JLabel priceLabel;
	private JTextField quantityTextField;
	private JLabel quantityLabel;
	private JTextField descriptionTextField;
	private JLabel descriptionLabel;
	private Item originalItem;
	
	private GridLayout gridLayout = new GridLayout(0,2);
	
	public ItemPanel(){
		init();
	}
	
	public ItemPanel(Item item){
		init();
		setValues(item);
	}
	
	private void setValues(Item item) {
		nameTextField.setText(item.getName());
		priceTextField.setText(item.getPrice()+"");
		quantityTextField.setText(item.getQuantity()+"");
		descriptionTextField.setText(item.getDescription());
		originalItem=item;
	}

	private void init(){
		
		setLayout(gridLayout);
		nameLabel=new JLabel("name: ");
		priceLabel=new JLabel("price: ");
		quantityLabel=new JLabel("quantity: ");
		descriptionLabel=new JLabel("description: ");
		nameTextField=new JTextField();
		
		priceTextField=new JTextField();
		priceTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		quantityTextField=new JTextField();
		quantityTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		descriptionTextField=new JTextField();
		add(nameLabel);
		add(nameTextField);
		add(priceLabel);
		add(priceTextField);
		add(quantityLabel);
		add(quantityTextField);
		add(descriptionLabel);
		add(descriptionTextField);
		
	}

	@Override
	public boolean doValidation() {
		
		if(nameTextField.getText().isEmpty()){
			addToValidationMassage("name is empty!");
				return false;
				
		}
		if(priceTextField.getText().isEmpty()){
			addToValidationMassage("price is empty!");
			return false;
		}
				
		if(quantityTextField.getText().isEmpty()){
			addToValidationMassage("quantity is empty!");
				return false;
		}
		return true;
	}
	
	public Item getItem(){
		Item item;
		if (originalItem==null)
		item=new Item();
		else item=originalItem;
		item.setName(nameTextField.getText());
		item.setDescription(descriptionTextField.getText());
		item.setPrice(Integer.parseInt(priceTextField.getText()));
		item.setQuantity(Integer.parseInt(quantityTextField.getText()));
		return item;
	}

}
