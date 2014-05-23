package main;

import static org.junit.Assert.*;
import model.Item;

import org.junit.Test;

public class MainTest{

	@Test
	public void TestAddToTable() {
		Item testItem = new Item("alma",5,120,"ez egy alma");
		Main.addToTable(testItem, 3);
		assertEquals(1, Main.getTableData().length);
		
	}

}
