package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import dbConnection.HibernateUtil;
import dbConnection.ItemDAO;
import main.Main;
import model.Item;

import javax.swing.SwingConstants;

import java.awt.Component;
import java.io.File;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JSplitPane splitPane;
	private JTable table;
	private JButton removeFromBill;
	private JButton addToBillbtn;
	private JButton addNewItembtn;
	private JList<Item> list;
	private JLabel sum;
	private Integer summa = new Integer(0);
	private JButton removeItembtn;
	private JButton modifyItembtn;
	private int quantity;
	private JLabel quantityLabel;
	private JTextField quantityTextField;
	private JPanel quantityPanel;
	private JButton saveBill;
	private JButton loadBill;
	private JLabel sumLabel;

	private void SearchList(String s) {
		DefaultListModel<Item> listModel = new DefaultListModel<Item>();
		ArrayList<Item> items = (ArrayList<Item>) Main.getItemList();
		for (Item item : items) {
			if (item.getName().startsWith(s)
					|| item.getName().contains(" " + s))
				listModel.addElement(item);
		}
		if (list == null)
			list = new JList<Item>(listModel);
		else
			list.setModel(listModel);
	}

	private void refreshList() {
		DefaultListModel<Item> listModel = new DefaultListModel<Item>();
		ArrayList<Item> items = (ArrayList<Item>) Main.getItemList();
		for (Item item : items) {
			listModel.addElement(item);
		}
		if (list == null)
			list = new JList<Item>(listModel);
		else
			list.setModel(listModel);
	}

	private void refreshTable() throws NullPointerException {
		String[] columnNames = new String[] { "Id", "Name", "Quantity",
				"Price", "Description" };
		Object[][] temp = Main.getTableData();
		DefaultTableModel tableModel = new DefaultTableModel(
				temp, columnNames);
		summa=Main.getTotal();
		sum.setText(summa.toString());
		if (table == null)
			table = new JTable(tableModel);
		else
			table.setModel(tableModel);
	}

	/**
	 * A MainFrame konstruktora amire minden ráépül.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		JPanel listPanel = new JPanel();

		textField = new JTextField();
		textField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {

			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				SearchList(textField.getText());
			}
		});
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		listPanel.add(Box.createHorizontalGlue());
		listPanel.add(textField);
		textField.setColumns(10);
		textField.setMaximumSize(new Dimension(100000, 60));

		listPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		refreshList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(list);

		listPanel.add(listScroller);

		removeItembtn = new JButton("Remove Selected Item");
		removeItembtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeItembtn.addActionListener(this);
		removeItembtn.setMaximumSize(new Dimension(100000, 80));
		addToBillbtn = new JButton("Add to Bill");
		addToBillbtn.addActionListener(this);
		addToBillbtn.setMaximumSize(new Dimension(100000, 80));
		addNewItembtn = new JButton("Add Item to List");
		addNewItembtn.setMaximumSize(new Dimension(100000, 80));
		addNewItembtn.addActionListener(this);
		addNewItembtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		modifyItembtn = new JButton("Modify Item");
		modifyItembtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		modifyItembtn.addActionListener(this);
		modifyItembtn.setMaximumSize(new Dimension(100000, 80));
		listPanel.add(removeItembtn);
		listPanel.add(modifyItembtn);
		listPanel.add(addNewItembtn);
		quantityPanel = new JPanel();
		quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.X_AXIS));
		quantityPanel.setMaximumSize(new Dimension(100000, 80));
		quantityLabel = new JLabel("Quantity");
		quantityTextField = new JTextField();
		quantityTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();
				if (!(Character.isDigit(vChar)
						|| (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		quantityPanel.add(quantityLabel);
		quantityPanel.add(quantityTextField);
		quantityPanel.add(addToBillbtn);

		listPanel.add(quantityPanel);
		table = new JTable();
		JPanel billPanel = new JPanel();
		billPanel.add(new JScrollPane(table));
		billPanel.setLayout(new BoxLayout(billPanel, BoxLayout.Y_AXIS));
		billPanel.add(Box.createHorizontalGlue());
		billPanel.setPreferredSize(new Dimension(400, 500));

		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.X_AXIS));
		removeFromBill = new JButton("Remove from bill");
		removeFromBill.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeFromBill.addActionListener(this);
		removeFromBill.setMaximumSize(new Dimension(100000, 60));
		toolPanel.add(removeFromBill);
		saveBill = new JButton("Save Bill");
		saveBill.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveBill.addActionListener(this);
		saveBill.setMaximumSize(new Dimension(100000, 60));
		toolPanel.add(saveBill);
		loadBill = new JButton("Load Bill");
		loadBill.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadBill.addActionListener(this);
		loadBill.setMaximumSize(new Dimension(100000, 60));
		toolPanel.add(loadBill);
		sumLabel = new JLabel("SUM:");
		sumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sumLabel.setMaximumSize(new Dimension(100000, 60));
		toolPanel.add(sumLabel);
		sum = new JLabel("" + 0);
		sum.setAlignmentX(Component.CENTER_ALIGNMENT);
		toolPanel.add(sum);
		billPanel.add(toolPanel);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel,
				billPanel);
		contentPane.add(splitPane, BorderLayout.CENTER);
		splitPane.setResizeWeight(0.3);
		splitPane.setContinuousLayout(true);
		setContentPane(contentPane);
		pack();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addNewItembtn) {
			ItemPanel itemPanel = new ItemPanel();
			if (ValidatableDialog.show(null, itemPanel, "Add new Item")) {
				Main.addNewItem(itemPanel.getItem());
				refreshList();
			}
		}

		if (e.getSource() == addToBillbtn) {
			try {
				Item item = list.getSelectedValue();
				if (quantityTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null,
							"Quantity can't be empty", "Warning",
							JOptionPane.WARNING_MESSAGE);
				else {
					quantity = Integer.parseInt(quantityTextField.getText());
					if (item.getQuantity() >= quantity) {
						
						Main.modifyItem(item);
						Main.addToTable(item, quantity);
						summa = Main.getTotal();
						refreshTable();
					} else
						JOptionPane.showMessageDialog(null,
								"We dont't have enaugh of this!", "Warning",
								JOptionPane.WARNING_MESSAGE);
				}
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "No Selected Item",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getSource() == removeFromBill) {
			try {

				int index = table.getSelectedRow();
				if (index == -1)
					JOptionPane.showMessageDialog(null, "No Selected Item",
							"Warning", JOptionPane.WARNING_MESSAGE);
				else {
					Object[][] temp = Main.getTableData();
					
					Main.removeFromTable(index);
					summa=Main.getTotal();
					refreshTable();
				}
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "No Selected Item",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
		}

		if (e.getSource() == removeItembtn) {
			try {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
						null, "Are you sure you want to delete the following Item \n" + list.getSelectedValue().toString2(), "Removing Item",
						JOptionPane.YES_NO_OPTION)) {
					Main.removeItem(list.getSelectedValue());
					refreshList();
				}
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "No Selected Item",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}

		}

		if (e.getSource() == loadBill) {
			
			if(!loadBill.getText().equals("OK")){
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
					null, "Are you sure you want to load another bill? \n The current data will be lost", "Removing Item",
					JOptionPane.YES_NO_OPTION)) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("bills"));
			FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
					"xml files (*.xml)", "xml");
			fc.setFileFilter(xmlfilter);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				Main.loadBill(path);
				refreshTable();
				loadBill.setText("OK");
				saveBill.setEnabled(false);
				removeFromBill.setEnabled(false);
				addToBillbtn.setEnabled(false);
			}
		}}else{
			loadBill.setText("Load Bill");
			saveBill.setEnabled(true);
			removeFromBill.setEnabled(true);
			addToBillbtn.setEnabled(true);
			Main.clearTable();
			refreshTable();
		}
			}
			

		if (e.getSource() == modifyItembtn) {
			try {
				ItemPanel itemPanel = new ItemPanel(list.getSelectedValue());
				if (ValidatableDialog.show(null, itemPanel, "Modify an Item")) {
					Main.modifyItem(itemPanel.getItem());
					refreshList();
				}
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "No Selected Item",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
		}

		if (e.getSource() == saveBill) {
			Main.saveBill();
			Main.clearTable();
			refreshTable();
		}

	}
}