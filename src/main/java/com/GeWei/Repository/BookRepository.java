package com.GeWei.Repository;

import com.GeWei.EntityClass.Book;
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

public class BookRepository {
    static public int getRows(){    
        int res=0;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from book";
            preparedStatement=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet=preparedStatement.executeQuery();
            resultSet.last();
            res=resultSet.getRow();
            resultSet.beforeFirst();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
        return res;
    }

    static public int AddBook(String Name,String Author,double Price,int Sales,int Stock,String Img_path){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="insert into book(Name,Author,Price,Sales,Stock,Img_path) values(?,?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Name);
            preparedStatement.setString(2,Author);
            preparedStatement.setDouble(3,Price);
            preparedStatement.setInt(4,Sales);
            preparedStatement.setInt(5,Stock);
            preparedStatement.setString(6,Img_path);
            res=preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
        return res;
    }

    static public Book QueryBookByID(int ID){
        Book book=null;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from book where ID=?";
            QueryRunner queryRunner=new QueryRunner();
            book=queryRunner.query(connection,sql,new BeanHandler<>(Book.class),ID);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return book;
    }

    static public List<Book> QueryBooksByName(String bookName){
        List<Book> books=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from book where Name like ?";
            QueryRunner queryRunner=new QueryRunner();
            books=queryRunner.query(connection,sql,new BeanListHandler<>(Book.class),"%"+bookName+"%");

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return books;
    }

    static public List<Book> QueryBooks(){
        List<Book> books=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from book";
            QueryRunner queryRunner=new QueryRunner();
            books=queryRunner.query(connection,sql,new BeanListHandler<>(Book.class));

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return books;
    }

    static public List<Book> QueryBooksPage(int pageNo,int pageSize){
        List<Book> books=new ArrayList<>();
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from book limit ?,?";
            QueryRunner queryRunner=new QueryRunner();
            books=queryRunner.query(connection,sql,new BeanListHandler<>(Book.class),(pageNo-1)*pageSize,pageSize);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return books;
    }

    static public int QueryBookStock(int ID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select Stock from book where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,ID);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                res=resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public int UpdateBookByID(int ID,String Name,String Author,double Price,int Sales,int Stock){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="update book set Name=? , Author=? , Price=? , Sales=? , Stock=? where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,Name);
            preparedStatement.setString(2,Author);
            preparedStatement.setDouble(3,Price);
            preparedStatement.setInt(4,Sales);
            preparedStatement.setInt(5,Stock);
            preparedStatement.setInt(6,ID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }

    static public int DeleteBookByID(int ID){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="delete from book where ID=?";
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

    static public void UpdateBookVisit(int ID){
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select Visits from book where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,ID);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int res=resultSet.getInt(1);
                sql="update book set Visits=? where ID=?";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,res+1);
                preparedStatement.setInt(2,ID);
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
    }

    static public void UpdateBookStock(int ID,int count){
        Connection connection=null;PreparedStatement preparedStatement=null;
        try {
            connection = JDBCTools.getConnection();
            int res=QueryBookStock(ID);
            String sql="update book set Stock=? where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,res-count);
            preparedStatement.setInt(2,ID);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
    }

    static public void UpdateBookSales(int ID,int count){
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select Sales from book where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,ID);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int res=resultSet.getInt(1);
                sql="update book set Sales=? where ID=?";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,res+count);
                preparedStatement.setInt(2,ID);
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
    }
}
