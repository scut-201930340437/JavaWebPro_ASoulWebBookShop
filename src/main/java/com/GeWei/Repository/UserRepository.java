package com.GeWei.Repository;

import com.GeWei.EntityClass.User;
import com.GeWei.util.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;


import java.sql.*;

public class UserRepository {
    static public int getRows(){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="select * from user";
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
    static public int AddUser(String myAccount,String myPassword,String myEmail){
        int res=0;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql="insert into user(Account,Password,E_mail) values(?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,myAccount);
            preparedStatement.setString(2,myPassword);
            preparedStatement.setString(3,myEmail);
            preparedStatement.executeUpdate();

            sql="select ID from user where Account=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,myAccount);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                res=resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
        return res;
    }

    static public User QueryUser(String myAccount, String myPassword){
        User user=null;
        Connection connection=null;PreparedStatement preparedStatement=null;ResultSet resultSet=null;
        try {
            connection= JDBCTools.getConnection();
            String sql=null;
            if(myPassword!=null) {
                sql = "select * from user where Account=? and Password=?";
            }else{
                sql="select * from user where Account=?";
            }
            QueryRunner queryRunner=new QueryRunner();
            if(myPassword!=null) {
                user = queryRunner.query(connection, sql, new BeanHandler<>(User.class), myAccount, myPassword);
            }else{
                user=queryRunner.query(connection,sql,new BeanHandler<>(User.class),myAccount);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,resultSet);
        }
        return user;
    }

    static public int UpdateUser(int ID,String newName,String newTele,String newEmail){
        Connection connection=null;PreparedStatement preparedStatement=null;
        int res=0;
        try {
            connection= JDBCTools.getConnection();
            String sql="update user set Name=?,Telephone=?,E_mail=? where ID=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,newTele);
            preparedStatement.setString(3,newEmail);
            preparedStatement.setInt(4,ID);
            res=preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCTools.Release(connection,preparedStatement,null);
        }
        return res;
    }
}
