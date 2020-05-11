package nsu.fit.db.dao;

import nsu.fit.db.JDBCPostgreSQL;
import nsu.fit.db.model.MNode;
import nsu.fit.db.model.MTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DAOTag {

    private static final String SQL_INSERT = "insert into Tag(k, v) values (?, ?); ";
    private static final String SQL_GET_BY_ID = "select * from Tag where id = ?; ";

    private static final String SQL_GET_BY_VALUES = "select * from Tag where k = ? and v = ?; ";

    private static final String SQL_INSERT_TAG_NODE = "WITH rowss AS (INSERT INTO Tag(k, v) values (?, ?) returning id) INSERT INTO Node_tag (node_id, tag_id) values (?, (SELECT id FROM rowss));";
    private static final String SQL_INSERT_TAG_WAY = "WITH rowss AS (INSERT INTO Tag(k, v) values (?, ?) returning id) INSERT INTO Way_tag (way_id, tag_id) values (?, (SELECT id FROM rowss));";
    private static final String SQL_INSERT_TAG_RELATION = "WITH rowss AS (INSERT INTO Tag(k, v) values (?, ?) returning id) INSERT INTO Relation_tag (relation_id, tag_id) values (?, (SELECT id FROM rowss));";

    private static final String SQL_INSERT_NODE_REL = "insert into Node_tag(node_id, tag_id) values (?, ?);";
    private static final String SQL_INSERT_WAY_REL = "insert into Way_tag(way_id, tag_id) values (?, ?);";
    private static final String SQL_INSERT_RELATION_REL = "insert into Relation_tag(relation_id, tag_id) values (?, ?);";


    public MTag getElement(long tagId) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_GET_BY_ID);
        statement.setLong(1, tagId);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return new MTag(
                    resultSet.getLong("id"),
                    resultSet.getString("k"),
                    resultSet.getString("v"));
        }
        return null;
    }

    public void insertNodeTag(MTag element, long nodeId) throws SQLException {
        MTag dbTag = getElementByValue(element.getK(), element.getV());
        Statement statement = JDBCPostgreSQL.getConnection().createStatement();
        if (dbTag == null){
            String sql = getInsertNodeString(element.getK(), element.getV(), nodeId);
            statement.execute(sql);
        } else {
            String sql = getInsertRelNode(dbTag.getId(), nodeId);
            statement.execute(sql);
        }
    }

    public void insertWayTag(MTag element, long wayId) throws SQLException {
        MTag dbTag = getElementByValue(element.getK(), element.getV());
        Statement statement = JDBCPostgreSQL.getConnection().createStatement();
        if (dbTag == null){
            String sql = getInsertWayString(element.getK(), element.getV(), wayId);
            statement.execute(sql);
        } else {
            String sql = getInsertRelWay(dbTag.getId(), wayId);
            statement.execute(sql);
        }
    }

    public void insertRelationTag(MTag element, long relationId) throws SQLException {
        MTag dbTag = getElementByValue(element.getK(), element.getV());
        Statement statement = JDBCPostgreSQL.getConnection().createStatement();
        if (dbTag == null){
            String sql = getInsertRelationString(element.getK(), element.getV(), relationId);
            statement.execute(sql);
        } else {
            String sql = getInsertRelRelation(dbTag.getId(), relationId);
            statement.execute(sql);
        }
    }

    public void insertPreparedNodeTag(MTag element, long nodeId) throws SQLException {
        insertPreparedTag(element, nodeId, SQL_INSERT_TAG_NODE, SQL_INSERT_NODE_REL);
    }

    public void insertPreparedWayTag(MTag element, long wayId) throws SQLException {
        insertPreparedTag(element, wayId, SQL_INSERT_TAG_WAY, SQL_INSERT_WAY_REL);
    }

    public void insertPreparedRelationTag(MTag element, long wayId) throws SQLException {
        insertPreparedTag(element, wayId, SQL_INSERT_TAG_RELATION, SQL_INSERT_RELATION_REL);
    }

    private void insertPreparedTag(MTag element, long wayId, String sql, String sqlE) throws SQLException {
        MTag dbTag = getElementByValue(element.getK(), element.getV());

        if (dbTag == null){
            PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(sql);
            statement.setString(1, element.getK());
            statement.setString(2, element.getV());
            statement.setLong(3, wayId);
            statement.execute();
        } else {
            PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(sqlE);
            statement.setLong(1, wayId);
            statement.setLong(2, dbTag.getId());
            statement.execute();
        }
    }

    public void insertBatchNodeTag(List<MTag> element, Map<MNode, Long> ids) throws SQLException {
        insertBatchTag(element, ids, SQL_INSERT_TAG_NODE, SQL_INSERT_NODE_REL);
    }

    public void insertBatchWayTag(List<MTag> element, Map<MNode, Long> ids) throws SQLException {
        insertBatchTag(element, ids, SQL_INSERT_TAG_WAY, SQL_INSERT_WAY_REL);
    }

    public void insertBatchRelationTag(List<MTag> element, Map<MNode, Long> ids) throws SQLException {
        insertBatchTag(element, ids, SQL_INSERT_TAG_RELATION, SQL_INSERT_RELATION_REL);
    }

    private void insertBatchTag(List<MTag> element, Map<MNode, Long> ids, String sql, String sqlE) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(sql);
        PreparedStatement statementRel = JDBCPostgreSQL.getConnection().prepareStatement(sqlE);
        for (MTag tag: element){
            MTag dbTag = getElementByValue(tag.getK(), tag.getV());
            if (dbTag == null){
                statement.setString(1, tag.getK());
                statement.setString(2, tag.getV());
                statement.setLong(3, ids.get(tag));
                statement.addBatch();
            } else {
                statementRel.setLong(1, ids.get(tag));
                statementRel.setLong(2, dbTag.getId());
                statementRel.addBatch();
            }

            statement.execute();
            statementRel.execute();
        }
    }

    private MTag getElementByValue(String k, String v) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_GET_BY_VALUES);
        statement.setString(1, k);
        statement.setString(2, v);

        ResultSet resultSet = statement.executeQuery();
        return tagFromResult(resultSet);
    }

    private MTag tagFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new MTag(
                    resultSet.getLong("id"),
                    resultSet.getString("k"),
                    resultSet.getString("v"));
        }

        return null;
    }

    private String getInsertNodeString(String k, String v, long node_id){
        return "WITH rowss AS (INSERT INTO Tag(k, v) values (" + k +
                ", " + v + ") returning id) INSERT INTO Node_tag (node_id, tag_id) values (" + node_id + ", (SELECT id FROM rowss));";
    }

    private String getInsertRelNode(long tag_id, long node_id){
        return "insert into Node_tag(node_id, tag_id) values (" + node_id + ", " + tag_id + ");";
    }

    private String getInsertWayString(String k, String v, long way_id){
        return "WITH rowss AS (INSERT INTO Tag(k, v) values (" + k +
                ", " + v + ") returning id) INSERT INTO Way_tag (way_id, tag_id) values (" + way_id + ", (SELECT id FROM rowss));";
    }

    private String getInsertRelWay(long tag_id, long way_id) {
        return "insert into Way_tag(way_id, tag_id) values (" + way_id + ", " + tag_id + ");";
    }

    private String getInsertRelationString(String k, String v, long relation_id){
        return "WITH rowss AS (INSERT INTO Tag(k, v) values (" + k +
                ", " + v + ") returning id) INSERT INTO Relation_tag (relation_id, tag_id) values (" + relation_id + ", (SELECT id FROM rowss));";
    }

    private String getInsertRelRelation(long tag_id, long relation_id){
        return "insert into Relation_tag(relation_id, tag_id) values (" + relation_id + ", " + tag_id + ");";
    }
}
