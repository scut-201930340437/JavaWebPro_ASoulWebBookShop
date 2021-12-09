package com.GeWei.EntityClass;

public class CartItem {
    private int ID;
    private String Name;
    private int Count=1;
    private double SinglePrice;
    private double TotalPrice;
    private int OwnerID;
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

    public double getSinglePrice() {
        return SinglePrice;
    }

    public void setSinglePrice(double singlePrice) {
        SinglePrice = singlePrice;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(int ownerID) {
        OwnerID = ownerID;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public CartItem(int ID, String name, double singlePrice, int ownerID,int bookID) {
        this.ID=ID;
        Name = name;
        SinglePrice = singlePrice;
        TotalPrice = singlePrice;
        OwnerID=ownerID;
        BookID=bookID;
    }

    public CartItem(){
    }
}
