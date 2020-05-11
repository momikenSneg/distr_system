package nsu.fit.db.dao;

import nsu.fit.db.JDBCPostgreSQL;
import nsu.fit.db.model.MRelation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DAORelation implements DAO<MRelation>{

    private static final String SQL_INSERT = "insert into Relation(id, username) values (?, ?)";
    private static final String SQL_GET = "select * from Relation where id = ?";

    @Override
    public MRelation getElement(long relationId) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_GET);
        statement.setLong(1, relationId);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return new MRelation(
                    resultSet.getLong("id"),
                    resultSet.getString("username"));
        }
        return null;
    }

    @Override
    public void insert(MRelation element) throws SQLException {
        Statement statement = JDBCPostgreSQL.getConnection().createStatement();
        String sql = "insert into Relation(id, username) " +
                "values (" + element.getId() + ", " +
                element.getUsername() + ")";
        statement.execute(sql);
    }

    @Override
    public void insertPrepared(MRelation element) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_INSERT);
        statement.setLong(1, element.getId());
        statement.setString(2, element.getUsername());
        statement.execute();
    }

    @Override
    public void insertBatch(List<MRelation> element) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_INSERT);

        for (MRelation relation: element){
            statement.setLong(1, relation.getId());
            statement.setString(2, relation.getUsername());
            statement.addBatch();
        }
        statement.execute();
    }
}
