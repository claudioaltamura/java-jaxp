package de.claudioaltamura.jaxp.xpath;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryExample {

	public static void main(String[] args)
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		String xml = "<?xml version=\"1.0\"?>" + "<company>" + "<staff id=\"1001\">" + "<firstname>jack</firstname>"
				+ "<lastname>black</lastname>" + "<nickname>jb</nickname>" + "<salary>100000</salary>" + "</staff>"
				+ "<staff id=\"1002\">" + "<firstname>peter</firstname>" + "<lastname>piper</lastname>"
				+ "<nickname>pp</nickname>" + "<salary>100000</salary>" + "</staff>" + "</company>";

		InputStream in = IOUtils.toInputStream(xml, "UTF-8");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(in);

		query(doc);
	}

	private static void query(Document doc) throws XPathExpressionException {
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();

		// print lastname
		XPathExpression expr = xpath.compile("//staff[firstname='jack']/lastname/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		System.out.print("lastname ");
		for (int i = 0; i < nodes.getLength(); i++) {
			System.out.println(nodes.item(i).getNodeValue());
		}

		// count staff member
		expr = xpath.compile("count(//staff)");
		Double number = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
		System.out.println("staff member " + number.intValue());

	}

}
