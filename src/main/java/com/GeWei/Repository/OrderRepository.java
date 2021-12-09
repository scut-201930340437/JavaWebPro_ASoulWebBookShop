package com.GeWei.Repository;

import com.GeWei.EntityClass.Order;
import com.GeWei.util.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    static public int AddOrder(String CreateTime,int TotalCount, double OrderPrice, int OwnerID){
        int res=0;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="insert into orderform(CreateTime,TotalCount,OrderPrice,OwnerID) values(?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,CreateTime);
            preparedStatement.setInt(2,TotalCount);
            preparedStatement.setDouble(3,OrderPrice);
            preparedStatement.setInt(4,OwnerID);
            res=preparedStatement.executeUpdate();
            //System.out.println(res);
            sql="select ID from orderform";
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

    static public int DeleteOrderByID(int ID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            //由于有外码，需要先删除该订单的所有订单项
            OrderItemRepository.DeleteOrderItemByOrderID(ID);

            connection= JDBCTools.getConnection();
            String sql="delete from orderform where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,ID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public int UpdateOrder(int ID,int Status){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="update orderform set Status=? where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,Status);
            preparedStatement.setInt(2,ID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public List<Order> QueryOrderByOwnerID(int OwnerID){
        List<Order>items=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from orderform where OwnerID=?";
            QueryRunner queryRunner=new QueryRunner();
            items=queryRunner.query(connection,sql,new BeanListHandler<>(Order.class),OwnerID);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return items;
    }

    static public Order QueryOrderByID(int ID){
        Order item=null;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from orderform where ID=?";
            QueryRunner queryRunner=new QueryRunner();
            item=queryRunner.query(connection,sql,new BeanHandler<>(Order.class),ID);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return item;
    }

    static public List<Order> QueryOrder(){
        List<Order>items=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from orderform";
            QueryRunner queryRunner=new QueryRunner();
            items=queryRunner.query(connection,sql,new BeanListHandler<>(Order.class));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return items;
    }
}
