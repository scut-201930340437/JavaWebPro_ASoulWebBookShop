package com.GeWei.EntityClass;

import com.GeWei.Repository.CartItemRepository;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int OwnerID;
    private int TotalCount;
    private double allItemPrice;
    private Map<Integer,CartItem> items=new HashMap<Integer,CartItem>();

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public double getAllItemPrice() {
        return allItemPrice;
    }

    public void setAllItemPrice(double allItemPrice) {
        this.allItemPrice = allItemPrice;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public int getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(int ownerID) {
        OwnerID = ownerID;
    }

    public Cart(int ownerID, int totalCount, double allItemPrice, Map<Integer, CartItem> items) {
        OwnerID = ownerID;
        TotalCount = totalCount;
        this.allItemPrice = allItemPrice;
        this.items = items;
    }

    public Cart(){
    }

    public void addItem(String name,double singlePrice,int ownerID,int bookID){
        //首先在cartitem表中查询该用户是否已有该购物车项
        int res=CartItemRepository.QueryCartItemByNameAndOwnerID(name,ownerID);
        if(res>0){
            //有则数量加1
            CartItem cartItem=items.get(res);
            cartItem.setCount(cartItem.getCount()+1);
            cartItem.setTotalPrice(cartItem.getTotalPrice()+cartItem.getSinglePrice());
            CartItemRepository.UpdateCartItem(cartItem.getID(),cartItem.getCount(),cartItem.getTotalPrice());
        } else {
            //无则添加一条记录
            res=CartItemRepository.AddCartItem(name,singlePrice,ownerID,bookID);
            items.put(res,new CartItem(res,name,singlePrice,ownerID,bookID));
        }
        TotalCount++;allItemPrice+=singlePrice;
    }

    public void deleteItem(Integer ID){
        CartItem cartItem=items.get(ID);
        TotalCount-=cartItem.getCount();
        allItemPrice-=cartItem.getTotalPrice();
        items.remove(ID);
        int res=CartItemRepository.DeleteCartItemByID(ID);
    }

    public void clearItem(){
        int res=CartItemRepository.DeleteCartItemByOwnerID(this.OwnerID);
        items.clear();
        TotalCount=0;allItemPrice=0.0;
    }

    public void updateItem(Integer ID,Integer newCount){
        CartItem cartItem=items.get(ID);
        TotalCount-=cartItem.getCount();allItemPrice-=cartItem.getTotalPrice();
        cartItem.setCount(newCount);
        cartItem.setTotalPrice((double)newCount*cartItem.getSinglePrice());
        TotalCount+=cartItem.getCount();allItemPrice+=cartItem.getTotalPrice();
        int res=CartItemRepository.UpdateCartItem(ID,newCount,cartItem.getTotalPrice());
    }
}
