package nsu.fit.db;

import nsu.fit.db.dao.DAONode;
import nsu.fit.db.dao.DAORelation;
import nsu.fit.db.dao.DAOTag;
import nsu.fit.db.dao.DAOWay;
import nsu.fit.db.model.MNode;
import nsu.fit.db.model.MRelation;
import nsu.fit.db.model.MTag;
import nsu.fit.db.model.MWay;
import nsu.fit.osm.OSMReader;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.sql.SQLException;
import java.util.Map;

public class Service {
    private OSMReader osmReader;
    private static final int AMOUNT = 50000;
    private DAONode daoNode = new DAONode();
    private DAOWay daoWay = new DAOWay();
    private DAORelation daoRelation = new DAORelation();
    private DAOTag daoTag = new DAOTag();

    public Service(OSMReader osmReader){
        this.osmReader = osmReader;
    }

    public void run(){

    }

    public void put() throws JAXBException, XMLStreamException, SQLException {
        OSMReader.ElementTagContainer<MNode> nodes = osmReader.getNodes(AMOUNT);
        while (nodes.getElements().size() != 0){
            long timeStart = System.currentTimeMillis();
            for (MNode node: nodes.getElements()){
                daoNode.insert(node);
            }
            for (Map.Entry<MTag, Long> tag: nodes.getTags().entrySet()){
                daoTag.insertNodeTag(tag.getKey(), tag.getValue());
            }
            JDBCPostgreSQL.commit();
            printTime(nodes.getElements().size() + nodes.getTags().size(), timeStart);
            nodes = osmReader.getNodes(AMOUNT);
        }

        OSMReader.ElementTagContainer<MWay> ways = osmReader.getWays(AMOUNT);
        while (ways.getElements().size() != 0){
            for (MWay way: ways.getElements()){
                 daoWay.insert(way);
            }
            for (Map.Entry<MTag, Long> tag: ways.getTags().entrySet()){
                daoTag.insertWayTag(tag.getKey(), tag.getValue());
            }
            JDBCPostgreSQL.commit();
            ways = osmReader.getWays(AMOUNT);
        }

        OSMReader.ElementTagContainer<MRelation> relations = osmReader.getRelations(AMOUNT);
        while (relations.getElements().size() != 0){
            for (MRelation relation: relations.getElements()){
                daoRelation.insert(relation);
            }
            for (Map.Entry<MTag, Long> tag: relations.getTags().entrySet()){
                daoTag.insertRelationTag(tag.getKey(), tag.getValue());
            }
            JDBCPostgreSQL.commit();
            relations = osmReader.getRelations(AMOUNT);
        }
    }

    public void putPrepared() throws JAXBException, XMLStreamException, SQLException {
        OSMReader.ElementTagContainer<MNode> nodes = osmReader.getNodes(AMOUNT);
        while (nodes.getElements().size() != 0){
            long timeStart = System.currentTimeMillis();
            for (MNode node: nodes.getElements()){
                daoNode.insertPrepared(node);
            }
            for (Map.Entry<MTag, Long> tag: nodes.getTags().entrySet()){
                daoTag.insertPreparedNodeTag(tag.getKey(), tag.getValue());
            }
            JDBCPostgreSQL.commit();
            printTime(nodes.getElements().size() + nodes.getTags().size(), timeStart);
            nodes = osmReader.getNodes(AMOUNT);
        }

        OSMReader.ElementTagContainer<MWay> ways = osmReader.getWays(AMOUNT);
        while (ways.getElements().size() != 0){
            for (MWay way: ways.getElements()){
                daoWay.insertPrepared(way);
            }
            for (Map.Entry<MTag, Long> tag: ways.getTags().entrySet()){
                daoTag.insertPreparedWayTag(tag.getKey(), tag.getValue());
            }
            JDBCPostgreSQL.commit();
            ways = osmReader.getWays(AMOUNT);
        }

        OSMReader.ElementTagContainer<MRelation> relations = osmReader.getRelations(AMOUNT);
        while (relations.getElements().size() != 0){
            for (MRelation relation: relations.getElements()){
                daoRelation.insertPrepared(relation);
            }
            for (Map.Entry<MTag, Long> tag: relations.getTags().entrySet()){
                daoTag.insertPreparedRelationTag(tag.getKey(), tag.getValue());
            }
            JDBCPostgreSQL.commit();
            relations = osmReader.getRelations(AMOUNT);
        }
    }

    public void putBatch() throws JAXBException, XMLStreamException, SQLException {
        OSMReader.ElementTagContainer<MNode> nodes = osmReader.getNodes(AMOUNT);
        while (nodes.getElements().size() != 0){
            long timeStart = System.currentTimeMillis();
            daoNode.insertBatch(nodes.getElements());
            daoTag.insertBatchNodeTag(nodes.getTags());
            JDBCPostgreSQL.commit();
            printTime(nodes.getElements().size() + nodes.getTags().size(), timeStart);
            nodes = osmReader.getNodes(AMOUNT);
        }

        OSMReader.ElementTagContainer<MWay> ways = osmReader.getWays(AMOUNT);
        while (ways.getElements().size() != 0){
            daoWay.insertBatch(ways.getElements());
            daoTag.insertBatchWayTag(ways.getTags());
            JDBCPostgreSQL.commit();
            ways = osmReader.getWays(AMOUNT);
        }

        OSMReader.ElementTagContainer<MRelation> relations = osmReader.getRelations(AMOUNT);
        while (relations.getElements().size() != 0){
            daoRelation.insertBatch(relations.getElements());
            daoTag.insertBatchRelationTag(relations.getTags());
            JDBCPostgreSQL.commit();
            relations = osmReader.getRelations(AMOUNT);
        }
    }

    private void printTime(long size, long timeStart){
        long timeEnd = System.currentTimeMillis();
        double sp = (size) / ((double)(timeEnd - timeStart) / 1000);
        System.out.println("speed " + sp);
    }
}













