package nsu.fit.osm.jaxb;

import nsu.fit.XMLProcessor;
import nsu.fit.osm.OSMReader;
import nsu.fit.osm.jaxb.generated.Node;
import nsu.fit.osm.jaxb.generated.Osm;
import nsu.fit.osm.jaxb.generated.Tag;

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

    private Unmarshaller unmarshaller;

    public JAXBProcessor(InputStream in) throws JAXBException, XMLStreamException {
        JAXBContext jc = JAXBContext.newInstance(Node.class);
        unmarshaller = jc.createUnmarshaller();
        reader = FACTORY.createXMLStreamReader(in);
    }

    public OSMReader.OSMContainer process() throws XMLStreamException, JAXBException {
        OSMReader.OSMContainer container = new OSMReader.OSMContainer();

        while (reader.hasNext()){

            reader.next();

            if(reader.getEventType() == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())){
                Node node = (Node) unmarshaller.unmarshal(reader);

                container.addChange(node.getUser());
                List<Tag> tags = node.getTag();
                tags.forEach(e -> container.addKey(e.getK()));
            }

        }

        System.out.println("Soooooka");

//        List<Node> nodes = osm.getNode();
//
//        nodes.forEach(e -> {
//            container.addChange(e.getUser());
//            List<Tag> tags = e.getTag();
//            tags.forEach(t -> container.addKey(t.getK()));
//        });
        return container;
    }

}
