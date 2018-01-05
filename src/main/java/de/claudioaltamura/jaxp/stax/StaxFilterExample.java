package de.claudioaltamura.jaxp.stax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.io.IOUtils;

public class StaxFilterExample {

	public static void main(String args[]) throws IOException,
			XMLStreamException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<company>"
				+ "<employee id=\"01\">" + "<firstname>Peter</firstname>"
				+ "<lastname>Smith</lastname>" + "</employee>"
				+ "<employee id=\"02\">" + "<firstname>Jade</firstname>"
				+ "<lastname>Collins</lastname>" + "</employee>" + "</company>";

		StaxFilterExample staxFilterExample = new StaxFilterExample();
		InputStream in = IOUtils.toInputStream(xml, "UTF-8");
		List<String> firstNames = staxFilterExample.read(in);
		for (String firstName : firstNames) {
			System.out.println(firstName);
		}
	}

	public List<String> read(InputStream is) throws XMLStreamException {
		List<String> list = new ArrayList<>();
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createFilteredReader(
				inputFactory.createXMLEventReader(is), new EventFilter() {

					@Override
					public boolean accept(XMLEvent event) {
						if (!event.isStartElement() && !event.isEndElement()
								&& !event.isCharacters()) {
							return false;
						} else {
							return true;
						}
					}
				});

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			
			if (event.isStartElement() && 
				event.asStartElement().getName().getLocalPart().equals("firstname")) {
					event = eventReader.nextEvent();
					list.add(event.asCharacters().getData());
					continue;
			}
		}

		return list;
	}
}
