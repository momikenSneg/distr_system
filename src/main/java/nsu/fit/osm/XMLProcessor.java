package nsu.fit.osm;

import nsu.fit.osm.jaxb.generated.Node;
import nsu.fit.osm.jaxb.generated.Relation;
import nsu.fit.osm.jaxb.generated.Way;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public interface XMLProcessor {
    OSMReader.OSMContainer processCount() throws XMLStreamException, JAXBException;

    Node getNode() throws XMLStreamException, JAXBException;

    Way getWay() throws XMLStreamException, JAXBException;

    Relation getRelation() throws XMLStreamException, JAXBException;
}
