package com.GeWei.EntityClass;

public class Scan {
    private int UserID;
    private int BookID;
    private String CreateTime;
    private String BookName;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public Scan(int userID, int bookID, String createTime,String bookName) {
        UserID = userID;
        BookID = bookID;
        CreateTime = createTime;
        BookName=bookName;
    }

    public Scan(){
    }
}
