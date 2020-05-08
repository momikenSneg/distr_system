package nsu.fit.osm;

import nsu.fit.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OSMReader {

    private OSMContainer container;
    private static final Logger logger = LoggerFactory.getLogger(OSMReader.class);

    public void read(InputStream in) throws XMLStreamException {
        logger.info("Create processor");
        StAXProcessor processor = new StAXProcessor(in);
        container = processor.process();
    }

    public void printChanges(){
        container.getChanges().entrySet().stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .forEach(el -> System.out.println(el.getKey() + " " + el.getValue() + " times"));
    }

    public void printKeys(){
        container.getKeys().entrySet().stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .forEach(el -> System.out.println(el.getKey() + " " + el.getValue() + " times"));
    }


    static class OSMContainer {
        private Map<String, Integer> changes = new HashMap<>();
        private Map<String, Integer> keys = new HashMap<>();


        void addChange(String value){
            changes.compute(value, (k, v) -> v == null ? 1 : v + 1);
        }

        void addKey(String value){
            keys.compute(value, (k, v) -> v == null ? 1 : v + 1);
        }

        public Map<String, Integer> getChanges() {
            return changes;
        }

        public Map<String, Integer> getKeys() {
            return keys;
        }
    }
}
