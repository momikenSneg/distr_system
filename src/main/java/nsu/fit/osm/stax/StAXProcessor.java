package nsu.fit.osm.stax;

import nsu.fit.osm.XMLProcessor;
import nsu.fit.osm.OSMReader;
import nsu.fit.osm.jaxb.generated.Node;
import nsu.fit.osm.jaxb.generated.Relation;
import nsu.fit.osm.jaxb.generated.Way;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StAXProcessor implements AutoCloseable, XMLProcessor {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader reader;

    private static final Logger logger = LoggerFactory.getLogger(OSMReader.class);

    private static final QName user = new QName("user");
    private static final QName key = new QName("k");

    public StAXProcessor(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }

    public OSMReader.OSMContainer processCount() throws XMLStreamException {
        logger.info("Start processCount xml");
        OSMReader.OSMContainer container = new OSMReader.OSMContainer();
        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == XMLEvent.START_ELEMENT){
                if ("node".equals(reader.getLocalName())){
                    String value = getAttributeValue(user);
                    if (value != null)
                        container.addChange(value);
                    while (reader.hasNext()){
                        reader.next();
                        if (reader.getEventType() == XMLEvent.END_ELEMENT && "node".equals(reader.getLocalName())){
                            break;
                        }
                        if (reader.getEventType() == XMLEvent.START_ELEMENT){
                            if ("tag".equals(reader.getLocalName())){
                                value = getAttributeValue(key);
                                if (value != null)
                                    container.addKey(value);
                            }
                        }
                    }
                }
            }
        }
        logger.info("End processCount xml");
        return container;
    }

    @Override
    public Node getNode() throws XMLStreamException, JAXBException {
        return null;
    }

    @Override
    public Way getWay() throws XMLStreamException, JAXBException {
        return null;
    }

    @Override
    public Relation getRelation() throws XMLStreamException, JAXBException {
        return null;
    }

    private String getAttributeValue(QName name){
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            if (name.equals(reader.getAttributeName(i))) {
                return reader.getAttributeValue(i);
            }
        }
        return null;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) { // empty
            }
        }
    }
}
