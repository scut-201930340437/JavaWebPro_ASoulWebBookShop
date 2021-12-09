package com.GeWei.Repository;

import com.GeWei.EntityClass.Book;
import com.GeWei.EntityClass.CartItem;
import com.GeWei.EntityClass.Scan;
import com.GeWei.util.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScanRepository {
    static public int addOrUpdateRec(int userID,int bookID,String createTime){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from scanrecord where UserID=? and BookID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,userID);
            preparedStatement.setInt(2,bookID);
            resultSet=preparedStatement.executeQuery();

            Book book=BookRepository.QueryBookByID(bookID);
            String bookName=book.getName();

            if(resultSet.next()){
                sql="update scanrecord set CreateTime=?,BookName=? where UserID=? and BookID=?";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setString(1,createTime);
                preparedStatement.setString(2,bookName);
                preparedStatement.setInt(3,userID);
                preparedStatement.setInt(4,bookID);
            }else{
                sql="insert into scanrecord values (?,?,?,?)";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,userID);
                preparedStatement.setInt(2,bookID);
                preparedStatement.setString(3,createTime);
                preparedStatement.setString(4,bookName);
            }
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
        return res;
    }

    static public int deleteScanRecord(int userID,int bookID){
        Connection connection=null;PreparedStatement preparedStatement=null;
        int res=0;
        try {
            connection= JDBCTools.getConnection();
            String sql="delete from scanrecord where UserID=? and BookID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,userID);
            preparedStatement.setInt(2,bookID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public int clearScanRecord(int userID){
        Connection connection=null;PreparedStatement preparedStatement=null;
        int res=0;
        try {
            connection= JDBCTools.getConnection();
            String sql="delete from scanrecord where UserID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,userID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public List<Scan> ListMyScan(int userID){
        List<Scan>items=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from scanrecord where UserID=?";
            QueryRunner queryRunner=new QueryRunner();
            items=queryRunner.query(connection,sql,new BeanListHandler<>(Scan.class),userID);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return items;
    }
}
