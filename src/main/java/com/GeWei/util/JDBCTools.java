package com.GeWei.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class JDBCTools {
    static private DataSource dataSource;
    static {
        dataSource=new ComboPooledDataSource("MainC3P0");
    }

    static public Connection getConnection() {
        Connection connection=null;
        try {
            connection=dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    static public void Release(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
