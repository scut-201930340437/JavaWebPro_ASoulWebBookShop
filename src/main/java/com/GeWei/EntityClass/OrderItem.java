package com.GeWei.EntityClass;

public class OrderItem {
    private int ID;
    private String Name;
    private int Count;
    private double TotalPrice;
    private int OrderID;
    private int BookID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public OrderItem(int ID, String name, int count, double totalPrice, int orderID, int bookID) {
        this.ID=ID;
        Name = name;
        Count = count;
        TotalPrice = totalPrice;
        OrderID = orderID;
        BookID=bookID;
    }

    public OrderItem(){
    }
}
