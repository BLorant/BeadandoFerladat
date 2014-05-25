package xmlHandler;

import gui.MainFrame;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import main.Main;
import model.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Az xml dokumentumok mentésére és betöltésére használatos osztály.
 * @author Lorant
 *
 */
public class XMLHandler {
	/**
	 * a program egyszeri elindulása alatt méri ,hogy hány fájlt mentettünk ezzel az egyediséget biztosítja a timestamp mellett.
	 */
	private static int serial = 0;
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * Elmenti a megkapott adatokat egy szabványos xml dokumentumba.
	 * fájlnévenek az aktuális dátum óra perc van megadva és egy sorszám hozzácsapva a végére 
	 * ,hogy egy perben ha esetleg több mentés is van akkor ne okozzon problémát.
	 * @param tableData a kimenteni kívánt adatok.
	 * @return visszaadja az elérési útvonalát a lementett file-nak.
	 */
	public static String writeBill(Object[][] tableData) {
		try {

			DocumentBuilderFactory dBfactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder;

			dBuilder = dBfactory.newDocumentBuilder();

			Document doc = dBuilder.newDocument();

			Element rootElement = doc.createElement("Bill");
			doc.appendChild(rootElement);

			int datalength = (tableData == null ? 0 : tableData.length);
			if (tableData != null)
				for (int i = 0; i < datalength; i++) {
					Element item = doc.createElement("item");
					rootElement.appendChild(item);

					Element id = doc.createElement("id");
					id.appendChild(doc.createTextNode("" + tableData[i][0]));
					item.appendChild(id);

					Element name = doc.createElement("name");
					name.appendChild(doc
							.createTextNode((String) tableData[i][1]));
					item.appendChild(name);

					Element quantity = doc.createElement("quantity");
					quantity.appendChild(doc.createTextNode(""
							+ tableData[i][2]));
					item.appendChild(quantity);

					Element price = doc.createElement("price");
					price.appendChild(doc.createTextNode("" + tableData[i][3]));
					item.appendChild(price);

					Element description = doc.createElement("description");
					description.appendChild(doc.createTextNode(""
							+ (String) tableData[i][4]));
					item.appendChild(description);

				}

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;

				transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			Date date = new Date();
			File dir = new File(Main.billpath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String path = (Main.billpath + (1900 + date.getYear()) + ","
					+ date.getMonth() + "," + date.getDate() + "_"
					+ date.getHours() + "-" + date.getMinutes() + "("
					+ serial++ + ")" + ".xml");
			StreamResult result = new StreamResult(path);
			transformer.transform(source, result);
			return path;
		} catch (ParserConfigurationException e) {
			Main.getFrame().showError("Sorry can't something went wrong with the parser configuration \n"+e.getMessage());
			logger.error("Failed to Parse file");
		}
		catch (TransformerConfigurationException e) {
			Main.getFrame().showError("Sorry can't something went wrong with the transformer configuration \n"+e.getMessage());
			logger.error("faild to set up transformer");
		} catch (TransformerException e) {
			Main.getFrame().showError("Sorry cant transform or create file \n"+e.getMessage());
			logger.error("faild to transform file");
		}
		return null;

	}

	/**
	 * A megadott útvonalon elérhető xml file feldolgozása.
	 * @param path az olvasni kívánt fájl elérési útja
	 * @return visszaadja a beolvasott adatokat egy 2 dimenziós tömbben.
	 */
	public static Object[][] readXML(String path) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document doc = documentBuilder.parse(path);
			Element item = null;
			NodeList itemNodeList = doc.getElementsByTagName("item");
			Object[][] data = new Object[itemNodeList.getLength()][5];
			for (int i = 0; i < itemNodeList.getLength(); i++) {
				Node itemNode = itemNodeList.item(i);
				if (itemNode.getNodeType() == Node.ELEMENT_NODE)
					item = (Element) itemNode;
				data[i][0] = Integer.parseInt((item.getElementsByTagName("id")
						.item(0).getTextContent()));
				data[i][1] = ((item.getElementsByTagName("name").item(0)
						.getTextContent()));
				data[i][2] = Integer.parseInt((item.getElementsByTagName(
						"quantity").item(0).getTextContent()));
				data[i][3] = Integer.parseInt((item.getElementsByTagName(
						"price").item(0).getTextContent()));
				if (item.getElementsByTagName("description").item(0)
						.getTextContent().equals("null"))
					data[i][4] = "";
				else
					data[i][4] = ((item.getElementsByTagName("description")
							.item(0).getTextContent()));
			}
			return data;
		} catch (ParserConfigurationException e) {
			
			Main.getFrame().showError("Sorry can't something went wrong with the parser configuration \n"+e.getMessage());
			logger.error("Failed to Parse file");
		} catch (SAXException e) {
			Main.getFrame().showError("Sorry can't parse file \n"+e.getMessage());
		} catch (IOException e) {
			Main.getFrame().showError("Sorry can't open file \n"+e.getMessage());
			logger.error("cant reach requested file: "+path);
		}
		return null;
	}

}
