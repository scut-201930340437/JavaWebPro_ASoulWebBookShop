package com.GeWei.EntityClass;

import com.GeWei.Repository.OrderItemRepository;
import com.GeWei.Repository.OrderRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int ID;
    private String CreateTime;
    private int TotalCount;
    private double OrderPrice;
    private int OwnerID;
    private int Status=0;
//    private Map<Integer,OrderItem>items=new HashMap<>();
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public double getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        OrderPrice = orderPrice;
    }

    public int getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(int ownerID) {
        OwnerID = ownerID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

//    public Map<Integer, OrderItem> getItems() {
//        return items;
//    }
//
//    public void setItems(Map<Integer, OrderItem> items) {
//        this.items = items;
//    }

    static public int generateOrder(Cart cart) {
        Order order=new Order();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        order.CreateTime=format.format(date);
        //在orderform表中添加记录
        order.ID=OrderRepository.AddOrder(order.getCreateTime(),cart.getTotalCount(),cart.getAllItemPrice(),cart.getOwnerID());
        order.TotalCount=cart.getTotalCount();
        order.OrderPrice=cart.getAllItemPrice();
        order.OwnerID=cart.getOwnerID();
//        OrderItem orderItem=null;
        CartItem cartItem;
        //每一条购物车项对应一个订单项
        Map<Integer,CartItem>cartItems=cart.getItems();
        for (Map.Entry<Integer, CartItem> entry : cartItems.entrySet()) {
            cartItem=entry.getValue();
            OrderItemRepository.AddOrderItem(cartItem.getName(),cartItem.getCount(),cartItem.getTotalPrice(),order.ID,cartItem.getBookID());
//            orderItem=new OrderItem(orderItemID,cartItem.getName(),cartItem.getCount(),cartItem.getTotalPrice(),order.ID,cartItem.getBookID());
//            order.items.put(orderItem.getID(),orderItem);
        }
        return order.ID;
    }

   static public int generateOrder(String Name,double SinglePrice,int OwnerID,int bookID){
        Order order=new Order();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        order.CreateTime=format.format(date);
       //在orderform表中添加记录
        order.ID=OrderRepository.AddOrder(order.getCreateTime(),1,SinglePrice,OwnerID);
        order.TotalCount=1;
        order.OrderPrice=SinglePrice;
        order.OwnerID=OwnerID;
       //创建一个订单项，添加到orderitem表中
        OrderItemRepository.AddOrderItem(Name,1,SinglePrice,order.ID,bookID);
//        OrderItem orderItem=new OrderItem(orderItemID,Name,1,SinglePrice,order.ID,bookID);
//        order.items.put(orderItem.getID(),orderItem);
        return order.ID;
    }

    public Order(){
    }
}
