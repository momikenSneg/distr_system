package nsu.fit.osm;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StAXProcessor implements AutoCloseable {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    private Map<String, Integer> changes = new HashMap<>();
    private Map<String, Integer> keys = new HashMap<>();

    public StAXProcessor(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }

    public void process() throws XMLStreamException {
        QName user = new QName("user");
        QName key = new QName("k");
        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == XMLEvent.START_ELEMENT){
                if ("node".equals(reader.getLocalName())){
                    String value = getAttributeValue(user);
                    if (value != null)
                        changes.compute(value, (k, v) -> v == null ? 1 : v + 1);

                    while (reader.hasNext()){
                        reader.next();
                        if (reader.getEventType() == XMLEvent.END_ELEMENT && "node".equals(reader.getLocalName())){
                            break;
                        }
                        if (reader.getEventType() == XMLEvent.START_ELEMENT){
                            if ("tag".equals(reader.getLocalName())){
                                value = getAttributeValue(key);
                                if (value != null)
                                    keys.compute(value, (k, v) -> v == null ? 1 : v + 1);
                            }
                        }
                    }
                }
            }
        }
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
