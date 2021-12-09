package com.GeWei.EntityClass;

import com.GeWei.Repository.BookRepository;

public class Book {
    static int num;
    private int ID;
    private String Name;
    private String Author;
    private double Price;
    private int Sales;
    private int Stock;
    private String Img_path;
    private int Visits=0;
    static {
        num= BookRepository.getRows();
    }

    static public void deleteBook(){
        num--;
    }

    static public void addBook(){num++;}

    public static int getNum() {
        return num;
    }

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

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getSales() {
        return Sales;
    }

    public void setSales(int sales) {
        Sales = sales;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getImg_path() {
        return Img_path;
    }

    public void setImg_path(String img_path) {
        Img_path = img_path;
    }

    public int getVisits() {
        return Visits;
    }

    public void setVisits(int visits) {
        Visits = visits;
    }

    public Book(int id, String name, String author, double price, int sales, int stock, String img_path) {
        Name = name;
        Author = author;
        Price = price;
        Sales = sales;
        Stock = stock;
        Img_path = img_path;
        ID=id;
    }

    public Book() {
    }
}
