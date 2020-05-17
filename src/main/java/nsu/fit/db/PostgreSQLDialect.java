package nsu.fit.db;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class PostgreSQLDialect extends PostgreSQL82Dialect {
    public PostgreSQLDialect(){
        super();
        //registerHibernateType(Types.INTEGER, StandardBasicTypes.LONG.getName());
        //registerHibernateType(Types.BIGINT, StandardBasicTypes.INTEGER.getName());
    }
}
