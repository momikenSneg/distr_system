package nsu.fit.db.dao;

import nsu.fit.db.JDBCPostgreSQL;
import nsu.fit.db.model.MNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DAONode implements DAO<MNode> {

    private static final String SQL_INSERT = "insert into Node(id, lon, lat, username) values (?, ?, ?, ?)";
    private static final String SQL_GET = "select * from Node where id = ?";

    @Override
    public MNode getElement(long nodeId) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_GET);
        statement.setLong(1, nodeId);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return new MNode(
                    resultSet.getLong("id"),
                    resultSet.getLong("lon"),
                    resultSet.getLong("lat"),
                    resultSet.getString("username")
            );
        }
        return null;
    }

    @Override
    public void insert(MNode element) throws SQLException {
        Statement statement = JDBCPostgreSQL.getConnection().createStatement();
        String sql = "insert into Node(id, lon, lat, username) " +
                "values (" + element.getId() + ", " +
                element.getLon() + ", " +
                element.getLat() + ", " +
                element.getUsername() + ")";

        statement.execute(sql);
    }

    @Override
    public void insertPrepared(MNode element) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_INSERT);
        statement.setLong(1, element.getId());
        statement.setDouble(2, element.getLon());
        statement.setDouble(3, element.getLat());
        statement.setString(4, element.getUsername());
        statement.execute();
    }

    @Override
    public void insertBatch(List<MNode> element) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_INSERT);

        for (MNode node: element){
            statement.setLong(1, node.getId());
            statement.setDouble(2, node.getLon());
            statement.setDouble(3, node.getLat());
            statement.setString(4, node.getUsername());

            statement.addBatch();
        }
        statement.execute();
    }

}
