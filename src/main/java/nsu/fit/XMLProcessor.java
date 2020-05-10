package nsu.fit;

import nsu.fit.osm.OSMReader;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public interface XMLProcessor {
    OSMReader.OSMContainer process() throws XMLStreamException, JAXBException;
}
