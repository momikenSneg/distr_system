package nsu.fit.osm;

import nsu.fit.db.model.MNode;
import nsu.fit.db.model.MRelation;
import nsu.fit.db.model.MTag;
import nsu.fit.db.model.MWay;
import nsu.fit.osm.jaxb.generated.Node;
import nsu.fit.osm.jaxb.generated.Relation;
import nsu.fit.osm.jaxb.generated.Tag;
import nsu.fit.osm.jaxb.generated.Way;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class OSMReader {

    private OSMContainer container;
    private static final Logger logger = LoggerFactory.getLogger(OSMReader.class);
    private XMLProcessor processor;

    public OSMReader(XMLProcessor processor){
        this.processor = processor;
    }

    public void read(XMLProcessor processor) throws XMLStreamException, JAXBException {
        logger.info("Create processor");
        container = processor.processCount();
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

    public ElementTagContainer<MNode> getNode() throws JAXBException, XMLStreamException {
        ElementTagContainer<MNode> nodes = new ElementTagContainer<>();
        Node node = processor.getNode();
        if (node != null){
            nodes.addElement(convertNode(node));
            for (Tag tag: node.getTag()){
                nodes.addTag(convertTag(tag), node.getId().longValue());
            }
            return nodes;
        }
        return nodes;
    }

    public ElementTagContainer<MNode> getNodes(int amount) throws JAXBException, XMLStreamException {
        ElementTagContainer<MNode> nodes = new ElementTagContainer<>();
        for (int i = 0; i < amount; i++){
            Node node = processor.getNode();
            if (node != null){
                if (node.getUser().contains("'")){
                    String s = node.getUser().replaceAll("'", "");
                    node.setUser(s);
                }
                nodes.addElement(convertNode(node));
                for (Tag tag: node.getTag()){
                    if (tag.getV().contains("'")){
                        String s = tag.getV().replaceAll("'", "");
                        tag.setV(s);
                    }
                    nodes.addTag(convertTag(tag), node.getId().longValue());
                }
            } else {
                break;
            }
        }
        return nodes;
    }

    public ElementTagContainer<MWay> getWay() throws JAXBException, XMLStreamException {
        ElementTagContainer<MWay> ways = new ElementTagContainer<>();
        Way way = processor.getWay();
        if (way != null){
            ways.addElement(convertWay(way));
            for (Tag tag: way.getTag()){
                ways.addTag(convertTag(tag), way.getId().longValue());
            }
            return ways;
        }
        return ways;
    }

    public ElementTagContainer<MWay> getWays(int amount) throws JAXBException, XMLStreamException {
        ElementTagContainer<MWay> ways = new ElementTagContainer<>();
        for (int i = 0; i < amount; i++){
            Way way = processor.getWay();
            if (way != null){
                if (way.getUser().contains("'")){
                    String s = way.getUser().replaceAll("'", "");
                    way.setUser(s);
                }
                ways.addElement(convertWay(way));
                for (Tag tag: way.getTag()){
                    if (tag.getV().contains("'")){
                        String s = tag.getV().replaceAll("'", "");
                        tag.setV(s);
                    }
                    ways.addTag(convertTag(tag), way.getId().longValue());
                }
            } else {
                break;
            }
        }
        return ways;
    }

    public ElementTagContainer<MRelation> getRelation() throws JAXBException, XMLStreamException {
        ElementTagContainer<MRelation> relations = new ElementTagContainer<>();
        Relation relation = processor.getRelation();
        if (relation != null){
            relations.addElement(convertRelation(relation));
            for (Tag tag: relation.getTag()){
                relations.addTag(convertTag(tag), relation.getId().longValue());
            }
            return relations;
        }
        return relations;
    }

    public ElementTagContainer<MRelation> getRelations(int amount) throws JAXBException, XMLStreamException {
        ElementTagContainer<MRelation> relations = new ElementTagContainer<>();
        for (int i = 0; i < amount; i++){
            Relation relation = processor.getRelation();
            if (relation != null){
                if (relation.getUser().contains("'")){
                    String s = relation.getUser().replaceAll("'", "");
                    relation.setUser(s);
                }
                relations.addElement(convertRelation(relation));
                for (Tag tag: relation.getTag()){
                    if (tag.getV().contains("'")){
                        String s = tag.getV().replaceAll("'", "");
                        tag.setV(s);
                    }
                    relations.addTag(convertTag(tag), relation.getId().longValue());
                }
            } else {
                break;
            }
        }
        return relations;
    }

    private MNode convertNode(Node node){
        return new MNode(node.getId().longValue(), node.getLon(), node.getLat(), node.getUser());
    }

    private MTag convertTag(Tag tag){
        return new MTag(-1, tag.getK(), tag.getV());
    }

    private MWay convertWay(Way way){
        return new MWay(way.getId().longValue(), way.getUser());
    }

    private MRelation convertRelation(Relation relation){
        return new MRelation(relation.getId().longValue(), relation.getUser());
    }

    public static class OSMContainer {
        private Map<String, Integer> changes = new HashMap<>();
        private Map<String, Integer> keys = new HashMap<>();


        public void addChange(String value){
            changes.compute(value, (k, v) -> v == null ? 1 : v + 1);
        }

        public void addKey(String value){
            keys.compute(value, (k, v) -> v == null ? 1 : v + 1);
        }

        public Map<String, Integer> getChanges() {
            return changes;
        }

        public Map<String, Integer> getKeys() {
            return keys;
        }
    }

    public static class ElementTagContainer<T> {
        private List<T> elements = new ArrayList<>();
        private Map<MTag, Long> tags = new HashMap<>();

        public void addElement(T element){
            elements.add(element);
        }

        public  void addTag(MTag tag, long element_id){
            tags.put(tag, element_id);
        }

        public List<T> getElements() {
            return elements;
        }

        public Map<MTag, Long> getTags() {
            return tags;
        }
    }
}
