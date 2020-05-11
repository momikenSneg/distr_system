package nsu.fit.db.dao;

import nsu.fit.db.JDBCPostgreSQL;
import nsu.fit.db.model.MWay;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DAOWay implements DAO<MWay> {

    private static final String SQL_INSERT = "insert into Way(id, username) values (?, ?)";
    private static final String SQL_GET = "select * from Way where id = ?";

    @Override
    public MWay getElement(long elementId) throws SQLException {
        return null;
    }

    @Override
    public void insert(MWay element) throws SQLException {
        Statement statement = JDBCPostgreSQL.getConnection().createStatement();
        String sql = "insert into Way(id, username) " +
                "values (" + element.getId() + ", " +
                element.getUsername() + ")";
        statement.execute(sql);
    }

    @Override
    public void insertPrepared(MWay element) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_INSERT);
        statement.setLong(1, element.getId());
        statement.setString(2, element.getUsername());
        statement.execute();
    }

    @Override
    public void insertBatch(List<MWay> element) throws SQLException {
        PreparedStatement statement = JDBCPostgreSQL.getConnection().prepareStatement(SQL_INSERT);

        for (MWay way: element){
            statement.setLong(1, way.getId());
            statement.setString(2, way.getUsername());
            statement.addBatch();
        }
        statement.execute();
    }
}
