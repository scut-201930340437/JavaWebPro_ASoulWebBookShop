package com.GeWei.Repository;

import com.GeWei.EntityClass.OrderItem;
import com.GeWei.util.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository {
    static public int AddOrderItem(String Name,int Count,double TotalPrice,int OrderID,int BookID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="insert into orderitem(Name,Count,TotalPrice,OrderID,BookID) values(?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Name);
            preparedStatement.setInt(2,Count);
            preparedStatement.setDouble(3,TotalPrice);
            preparedStatement.setInt(4,OrderID);
            preparedStatement.setInt(5,BookID);
            preparedStatement.executeUpdate();

            sql="select ID from orderitem";
            preparedStatement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet=preparedStatement.executeQuery();
            resultSet.last();
            res=resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
        return res;
    }

    static public int DeleteOrderItemByOrderID(int OrderID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="delete from orderitem where OrderID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,OrderID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public List<OrderItem> QueryOrderItemByOrderID(int OrderID){
        List<OrderItem>items=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from orderitem where OrderID=?";
            QueryRunner queryRunner=new QueryRunner();
            items=queryRunner.query(connection,sql,new BeanListHandler<>(OrderItem.class),OrderID);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return items;
    }
}
