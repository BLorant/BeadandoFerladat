package main;

import java.util.List;

import gui.MainFrame;

import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Item;
import dbConnection.HibernateUtil;
import dbConnection.ItemDAO;
/**
 * A fő osztály amely egybentratja az összes többit.
 * @author Lorant
 *
 */
public class Main {

	/**
	 * A logger változó és annak inicializálása.
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * A tábla adatait tároló 2 dimenziós tömb.
	 */
	private static Object[][] tableData;
	/**
	 * A számlára felvehető itemek listája.
	 */
	private static List<Item> itemList;
	/**
	 * Az adatbázis hozzáféréséhez használatos objektum.
	 */
	private static ItemDAO itemDAO = ItemDAO.getInstance();
	
	public static String billpath="bills\\";
	
	private static int total=0;

	/**
	 * The main method 
	 * @param args paranccsori argumentumok
	 */
	public static void main(String[] args) {
		
		itemList = itemDAO.getAllItems();
		MainFrame frame = new MainFrame();
		logger.info("System started");
	}

	/**
	 * A tábla adatainak törlését végzi.
	 */
	public static void clearTable(){
		tableData=new Object[0][5];
		total=0;
	}
	
	/**
	 * A tábla adatainak bárhonnan való elérését biztosítja.
	 * @return visszaadja a tábla adatait.
	 */
	public static Object[][] getTableData() {
		return tableData;
	}

	/**
	 * A Lista adatainak bárhonnan való elérését biztosítja.
	 * @return visszaadja az aktuális itemlistát.
	 */
	public static List<Item> getItemList() {
		return itemList;
	}

	/**
	 * Egy item táblához hozzáadásáért felelős.
	 * @param item a hozzáadni kívánt item.
	 * @param quantity a számlához adni kívánt mennyiség.
	 * @throws NullPointerException Ha item paraméter null.
	 */
	public static void addToTable(Item item,int quantity) throws NullPointerException {

		if (item.getQuantity() >= quantity) {
		total = (getTotal() + item.getPrice() * quantity);
		item.changeQuantity(-quantity);
		int datalength = (tableData == null ? 0 : tableData.length);
		Object[][] temp = new Object[datalength + 1][5];
		if (tableData != null)
			for (int i = 0; i < datalength; i++)
				for (int j = 0; j < 5; j++)
					temp[i][j] = tableData[i][j];
		temp[datalength][0] = item.getId();
		temp[datalength][1] = item.getName();
		temp[datalength][2] = quantity;
		temp[datalength][3] = item.getPrice();
		temp[datalength][4] = item.getDescription();
		tableData = temp;
		}
	}

	/**
	 * Egy sor kivétele a táblából.
	 * @param index a kivenni kívánt sor indexe.
	 * @throws NullPointerException Ha item paraméter null.
	 */
	public static void removeFromTable(int index) throws NullPointerException {
		if (tableData!=null){
		int datalength = tableData.length -1;
		Object[][] temp = new Object[datalength][5];
		total -= (Integer) (tableData[index][2])
				* (Integer) (tableData[index][3]);
		Item tempitem=new Item();
		tempitem.setId((Integer)(tableData[index][0]));
		Item item = itemList.get(itemList.indexOf(tempitem));
		item.changeQuantity((Integer)(tableData[index][2]));
		modifyItem(item);
		if (tableData != null)
			for (int i = 0; i < datalength; i++)
				for (int j = 0; j < 5; j++){
					if (i < index){
						temp[i][j] = tableData[i][j];
					}
					else 
					{
						temp[i][j] = tableData[i+1][j];
					}
				}
		tableData = temp;
		}
	}
/**
 * Egy item felvétele az adatbázisba.
 * @param item a felvenni kívánt item.
 */
	public static void addNewItem(Item item) {
		itemDAO.addItem(item);
		itemList.add(item);
		logger.info("New item added to the database");
	}

	/**
	 * Egy item törlése az adatbázisból.
	 * @param item A törölni kívánt item.
	 * @throws NullPointerException Ha item paraméter null.
	 */
	public static void removeItem(Item item) throws NullPointerException {
		itemDAO.deleteItem(item);
		itemList.remove(item);
		logger.info("An item removed from the database");
	}

	/**
	 * Egy item módosítása az adatbázisban.
	 * @param item a módosítani kívánt item.
	 * @throws NullPointerException Ha item paraméter null.
	 */
	public static void modifyItem(Item item) throws NullPointerException {
		itemDAO.updateItem(item);
		logger.info("An item modified in the database");
	}
	
	/**
	 * Egy számla lementése xml dokumentumként.
	 */
	public static void saveBill(){
		xmlWriter.XMLHandler.writeBill(tableData);
		logger.info("A bill has been saved");
	}
	
	/**
	 * Egy számla betöltése.
	 * @param path a betülte kívánt számla elérési útvonala.
	 */
	public static void loadBill(String path){
		tableData=xmlWriter.XMLHandler.readXML(path);
		total=0;
		for (int i=0;i<tableData.length;i++){
			total+=(Integer)(tableData[i][2])*(Integer)(tableData[i][3]);
		}
		
	}

	public static int getTotal() {
		return total;
	}


}
