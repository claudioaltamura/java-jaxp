package de.claudioaltamura.jaxp.stax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.io.IOUtils;

public class StaxParser {
	static final String DATE = "date";
	static final String ITEM = "item";
	static final String UNIT = "unit";
	static final String CURRENT = "current";

	public List<Item> readConfig(InputStream is) {
		List<Item> items = new ArrayList<Item>();
		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader

			XMLEventReader eventReader = inputFactory.createXMLEventReader(is);
			// read the XML document
			Item item = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have an item element, we create a new item
					if (startElement.getName().getLocalPart() == (ITEM)) {
						item = new Item();
						// We read the attributes from this tag and add the date
						// attribute to our object
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(DATE)) {
								item.setDate(attribute.getValue());
							}

						}
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(UNIT)) {
						event = eventReader.nextEvent();
						item.setUnit(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(CURRENT)) {
						event = eventReader.nextEvent();
						item.setCurrent(event.asCharacters().getData());
						continue;
					}

				}
				// If we reach the end of an item element, we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (ITEM)) {
						items.add(item);
					}
				}

			}

		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return items;
	}

	public static void main(String args[]) throws IOException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<config>" +
					"<item date=\"January 2009\">" +
						"<unit>900</unit>" +
						"<current>1</current>" +
					"</item>" +
					"<item date=\"February 2009\">" +
				    	"<unit>400</unit>" +
				    	"<current>2</current>" +
				    "</item>" +
				"</config>";
		
		StaxParser read = new StaxParser();
		InputStream in = IOUtils.toInputStream(xml, "UTF-8");
		List<Item> readConfig = read.readConfig(in);
		for (Item item : readConfig) {
			System.out.println(item);
		}
	}

}
