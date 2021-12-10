package com.GeWei.Repository;

import com.GeWei.EntityClass.CartItem;
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

public class CartItemRepository {
    static public int AddCartItem(String Name,double SinglePrice,int OwnerID,int bookID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="insert into cartitem(Name,SinglePrice,TotalPrice,OwnerID,BookID) values(?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Name);
            preparedStatement.setDouble(2,SinglePrice);
            preparedStatement.setDouble(3,SinglePrice);
            preparedStatement.setInt(4,OwnerID);
            preparedStatement.setInt(5,bookID);
            preparedStatement.executeUpdate();

            sql="select ID from cartitem";
            preparedStatement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet=preparedStatement.executeQuery();
            resultSet.last();
            res=resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
            return res;
        }
    }

    static public int DeleteCartItemByOwnerID(int OwnerID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="delete from cartitem where OwnerID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,OwnerID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
            return res;
        }
    }

    static public int DeleteCartItemByID(int ID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="delete from cartitem where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,ID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
            return res;
        }
    }

    static public int UpdateCartItem(int ID,int Count,double TotalPrice){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="update cartitem set Count=?,TotalPrice=? where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,Count);
            preparedStatement.setDouble(2,TotalPrice);
            preparedStatement.setInt(3,ID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
            return res;
        }
    }

    static public List<CartItem> QueryCartItemByOwnerID(int OwnerID){
        List<CartItem>items=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from cartitem where OwnerID=?";
            QueryRunner queryRunner=new QueryRunner();
            items=queryRunner.query(connection,sql,new BeanListHandler<>(CartItem.class),OwnerID);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return items;
    }

    static public CartItem QueryCartItemByID(int ID){
        CartItem item=null;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select *from cartitem where ID=?";
            QueryRunner queryRunner=new QueryRunner();
            item=queryRunner.query(connection,sql,new BeanHandler<>(CartItem.class),ID);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return item;
    }

    static public int QueryCartItemByNameAndOwnerID(String Name,int OwnerID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select ID from cartitem where Name=? and OwnerID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Name);
            preparedStatement.setInt(2,OwnerID);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                res=resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
            return res;
        }
    }
}
