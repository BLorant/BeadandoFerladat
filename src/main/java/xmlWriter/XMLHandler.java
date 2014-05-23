package xmlWriter;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import main.Main;
import model.Item;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHandler {
	private static int serial = 0;

	public static void writeBill(Object[][] tableData) {

		try {
			DocumentBuilderFactory dBfactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dBfactory.newDocumentBuilder();

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
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			Date date = new Date();
			File dir = new File("bills");
			  if (!dir.exists()) {
			    dir.mkdir();
			  }
			StreamResult result = new StreamResult(
					Main.billpath +
					(1900 + date.getYear()) + "," + date.getMonth() + ","
					+ date.getDate() + "_" + date.getHours() + "-"
					+ date.getMinutes() + "(" + serial++ + ")" + ".xml");
			transformer.transform(source, result);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

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
					data[i][4]="";
				else
				data[i][4] = ((item.getElementsByTagName("description").item(0)
						.getTextContent()));
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
