package de.claudioaltamura.jaxp.dom;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ModifyXmlDom {

	public static void main(String[] args) {

		try {

			String xml = "<?xml version=\"1.0\"?>" +
			"<company>" +
				"<staff id=\"1001\">" +
					"<firstname>jack</firstname>"+
					"<lastname>black</lastname>"+
					"<nickname>jb</nickname>"+
					"<salary>100000</salary>"+
				"</staff>"+
			"</company>";

		InputStream in = IOUtils.toInputStream(xml, "UTF-8");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(in);

		Node staff = doc.getElementsByTagName("staff").item(0);

		NamedNodeMap attr = staff.getAttributes();
		Node nodeAttr = attr.getNamedItem("id");
		nodeAttr.setTextContent("2");

		Element age = doc.createElement("age");
		age.appendChild(doc.createTextNode("28"));
		staff.appendChild(age);

		NodeList list = staff.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

		   // get the salary element, and update the value
		   if ("salary".equals(node.getNodeName())) {
			node.setTextContent("2000000");
		   }

                //remove firstname
		   if ("firstname".equals(node.getNodeName())) {
			staff.removeChild(node);
		   }

		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (TransformerException tfe) {
		tfe.printStackTrace();
	   } catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
	}

}
