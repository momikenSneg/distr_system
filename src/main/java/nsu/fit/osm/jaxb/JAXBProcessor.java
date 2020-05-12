package nsu.fit.osm.jaxb;

import nsu.fit.osm.XMLProcessor;
import nsu.fit.osm.OSMReader;
import nsu.fit.osm.jaxb.generated.Node;
import nsu.fit.osm.jaxb.generated.Relation;
import nsu.fit.osm.jaxb.generated.Tag;
import nsu.fit.osm.jaxb.generated.Way;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.List;

public class JAXBProcessor implements XMLProcessor {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader reader;

    private Unmarshaller unmarshallerNode;
    private Unmarshaller unmarshallerWay;
    private Unmarshaller unmarshallerRelation;

    public JAXBProcessor(InputStream in) throws JAXBException, XMLStreamException {
        JAXBContext jcNode = JAXBContext.newInstance(Node.class);
        JAXBContext jcWay = JAXBContext.newInstance(Way.class);
        JAXBContext jcRelation = JAXBContext.newInstance(Relation.class);
        unmarshallerNode = jcNode.createUnmarshaller();
        unmarshallerWay = jcWay.createUnmarshaller();
        unmarshallerRelation = jcRelation.createUnmarshaller();
        reader = FACTORY.createXMLStreamReader(in);
    }

    public OSMReader.OSMContainer processCount() throws XMLStreamException, JAXBException {
        OSMReader.OSMContainer container = new OSMReader.OSMContainer();

        while (reader.hasNext()){

            reader.next();

            if(reader.getEventType() == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())){
                Node node = (Node) unmarshallerNode.unmarshal(reader);

                container.addChange(node.getUser());
                List<Tag> tags = node.getTag();
                tags.forEach(e -> container.addKey(e.getK()));
            }

        }
        return container;
    }

    public Node getNode() throws XMLStreamException, JAXBException {
        while (reader.hasNext()){
            if(reader.getEventType() == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())){
                return (Node) unmarshallerNode.unmarshal(reader);
            }
            if(reader.getEventType() == XMLEvent.START_ELEMENT && "way".equals(reader.getLocalName())){
                return null;
            }
            reader.next();
        }
        return null;
    }

    public Way getWay() throws XMLStreamException, JAXBException {
        while (reader.hasNext()){
            if(reader.getEventType() == XMLEvent.START_ELEMENT && "way".equals(reader.getLocalName())){
                return (Way) unmarshallerWay.unmarshal(reader);
            }
            if(reader.getEventType() == XMLEvent.START_ELEMENT && "relation".equals(reader.getLocalName())){
                return null;
            }
            reader.next();
        }
        return null;
    }

    public Relation getRelation() throws XMLStreamException, JAXBException {
        while (reader.hasNext()){
            if(reader.getEventType() == XMLEvent.START_ELEMENT && "relation".equals(reader.getLocalName())){
                return (Relation) unmarshallerRelation.unmarshal(reader);
            }
        }
        return null;
    }


}
