package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dbConnection.ItemDAO;

public class MainTest{
	public static ArrayList<Integer> quantityList;
	public static ArrayList<Item> originalItemList;
	

	@Before
	public void dbPreserve(){
		originalItemList=(ArrayList<Item>) ItemDAO.getInstance().getAllItems();
		quantityList=new ArrayList<Integer>();
		for (Item item : originalItemList){
			quantityList.add(item.getQuantity());
		}
		
		Main.clearTable();
	}
	
	@Test
	public void TestAddToTable() {
		
		Item testItem = originalItemList.get(1);
		Main.addToTable(testItem, 3);
		assertEquals(1, Main.getTableData().length);
		int c = Main.getTableData().length;
		Item testItem2 = originalItemList.get(2);
		Item testItem3 = originalItemList.get(3);
		Main.addToTable(testItem3, 2);
		Main.addToTable(testItem2, 1);
		assertEquals(c+2, Main.getTableData().length);

	}
	
	@Test
	public void TestremoveFromTable() {
		Item testItem = originalItemList.get(0);
		Main.addToTable(testItem, 3);
		assertEquals(1, Main.getTableData().length);
		Main.removeFromTable(0);
		assertEquals(0, Main.getTableData().length);
		int c = Main.getTableData().length;
		Item testItem2 = originalItemList.get(2);
		Item testItem3 =originalItemList.get(1);
		Main.addToTable(testItem3, 2);
		Main.addToTable(testItem2, 1);
		Main.removeFromTable(1);
		assertEquals(c+1, Main.getTableData().length);
	}
	
	@Test
	public void testTotalwithOneItem(){
		
		Item testItem =originalItemList.get(0);
		Main.addToTable(testItem, 3);
		assertEquals(3*testItem.getPrice(), Main.getTotal());
		
	}
	
	@Test
	public void testTotalwithRemoveOneItem(){
		Item testItem =originalItemList.get(0);
		Main.addToTable(testItem, 3);
		Item testItem2 =originalItemList.get(2);
		Main.addToTable(testItem2, 3);
		Main.removeFromTable(1);
		assertEquals(3*testItem.getPrice(), Main.getTotal());
	}
	
	@Test
	public void testTotalwithRemoveMoreItem(){
		Item testItem =originalItemList.get(0);
		Main.addToTable(testItem, 3);
		Item testItem2 =originalItemList.get(2);
		Main.addToTable(testItem2, 1);
		Item testItem3 =originalItemList.get(1);
		Main.addToTable(testItem3, 2);
		Main.removeFromTable(2);
		Main.removeFromTable(1);
		assertEquals(3*testItem.getPrice(), Main.getTotal());
	}
	
	@Test
	public void testTotalwithRemoveEveryThing(){
		for (int i=0;i<3;i++){
			Main.addToTable(originalItemList.get(i), 2);
		}
		for (int i=0;i<3;i++){
			Main.removeFromTable(0);
		}
		assertEquals(0, Main.getTotal());
	}
	
	@Test
	public void testTotalwithMoreItem(){
		
		Item testItem = originalItemList.get(2);
		Item testItem2 = originalItemList.get(0);
		Item testItem3 = originalItemList.get(1);
		Main.addToTable(testItem, 3);
		Main.addToTable(testItem2, 1);
		Main.addToTable(testItem3, 2);
		assertEquals((3 * testItem.getPrice()+testItem2.getPrice()+2*testItem3.getPrice()), Main.getTotal());		
	}
	
	@Test
	public void testSaveAndLoad(){
		
		Item testItem = originalItemList.get(2);
		Item testItem2 = originalItemList.get(0);
		Item testItem3 = originalItemList.get(1);
		Main.addToTable(testItem, 3);
		Main.addToTable(testItem2, 1);
		Main.addToTable(testItem3, 2);
		int sum = Main.getTotal();
		int count = Main.getTableData().length;
		String path = Main.saveBill();
		Main.loadBill(path);
		assertEquals(count,Main.getTableData().length);
		assertEquals(sum, Main.getTotal());

	}
	
@After
public void dbRestore(){
	Main.clearTable();
	int c=0;
	for (Item i : originalItemList){
		i.setQuantity(quantityList.get(c));
		Main.modifyItem(i);
		c++;
	}
}
	

}