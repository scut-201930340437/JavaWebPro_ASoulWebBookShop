package com.GeWei.Services;

import com.GeWei.EntityClass.*;
import com.GeWei.Repository.BookRepository;
import com.GeWei.Repository.CartItemRepository;
import com.GeWei.Repository.OrderItemRepository;
import com.GeWei.Repository.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/clientOrder")
public class ClientOrderServlet extends BaseServlet{
    //用户查询自己所有的订单
    protected void ListMyOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        User user=(User) httpSession.getAttribute("user");
        List<Order>orders= OrderRepository.QueryOrderByOwnerID(user.getID());
        req.setAttribute("orders",orders);
        req.setAttribute("orderCount",orders.size());
        double sumPrice=0.0;
        for (Order order:orders){
            sumPrice+=order.getOrderPrice();
        }
        req.setAttribute("sumPrice",sumPrice);
        req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);
    }
    //结算订单
    protected void PayOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));
        List<OrderItem>orderItems=OrderItemRepository.QueryOrderItemByOrderID(orderID);
        boolean work=true;
        //查询库存，看购物车中购买的商品的库存是否够
        for (OrderItem value : orderItems) {
            //只要有一件商品库存不够都无法购买
            if(value.getCount() > BookRepository.QueryBookStock(value.getBookID())){
                work=false;
                req.setAttribute("errorMsg","您购买的"+value.getName()+"，库存不足，无法购买。请等待管理员补充货源后再次购买。给您带来的不便敬请谅解。");
                req.getRequestDispatcher("/clientOrder?method=ListMyOrder").forward(req,resp);
            }
        }
        if(work){
            int res= OrderRepository.UpdateOrder(orderID,1);
            //修改销量和库存
            for(OrderItem orderItem:orderItems){
                BookRepository.UpdateBookSales(orderItem.getBookID(),orderItem.getCount());
                BookRepository.UpdateBookStock(orderItem.getBookID(),orderItem.getCount());
            }
            req.getRequestDispatcher("pages/order/checkout.jsp?orderID="+orderID).forward(req,resp);
        }
    }
    //签收订单
    protected void UpdateOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));
        int res= OrderRepository.UpdateOrder(orderID,3);
        req.getRequestDispatcher("/clientOrder?method=ListMyOrder").forward(req,resp);
    }
    //删除订单
    protected void DeleteOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));
        int res=OrderRepository.DeleteOrderByID(orderID);
        req.getRequestDispatcher("/clientOrder?method=ListMyOrder").forward(req,resp);
    }
    //查询订单
    protected void QueryByID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        User user=(User) httpSession.getAttribute("user");

        int orderID=Integer.parseInt(req.getParameter("orderID"));
        List<Order>orders=new ArrayList<>();
        Order orderByID=OrderRepository.QueryOrderByID(orderID);
        if(orderByID!=null&&orderByID.getOwnerID()==user.getID()){
            orders.add(orderByID);
            req.setAttribute("orders",orders);
            req.setAttribute("orderCount",1);
            req.setAttribute("sumPrice",orderByID.getOrderPrice());
        }else{
            req.setAttribute("msg","无法查询到该订单，请确认订单号无误。");
        }
        req.setAttribute("isQ","1");
        req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);
    }
    //根据状态查询订单
    protected void QueryOrderByStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderStatus=Integer.parseInt(req.getParameter("orderStatus"));
        HttpSession httpSession=req.getSession();
        User user=(User) httpSession.getAttribute("user");
        List<Order>orders= OrderRepository.QueryOrderByOwnerID(user.getID());
        List<Order>ordersStatus=new ArrayList<>();
        double sumPrice=0.0;
        for (Order order:orders){
            if(order.getStatus()==orderStatus){
                ordersStatus.add(order);
                sumPrice+=order.getOrderPrice();
            }
        }
        req.setAttribute("orders",ordersStatus);
        req.setAttribute("orderCount",ordersStatus.size());
        req.setAttribute("sumPrice",sumPrice);
        req.setAttribute("isQ","1");
        req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);
    }
    //通过购物车创建订单
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        Cart cart=(Cart) httpSession.getAttribute("cart");
        //通过购物车生成订单
        int orderID=Order.generateOrder(cart);
        //从购物车生成订单后，清除购物车
        cart.clearItem();
        httpSession.setAttribute("cart",cart);
        req.getRequestDispatcher("/clientOrder?method=QueryByID&orderID="+orderID).forward(req,resp);
    }
    //通过直接购买创建订单
    protected void createOrderByPurchase(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int bookID=Integer.parseInt(req.getParameter("bookID"));
        String name=req.getParameter("bookName");
        HttpSession httpSession=req.getSession();
        User user=(User) httpSession.getAttribute("user");

        double singlePrice=Double.parseDouble(req.getParameter("singlePrice"));
        //通过直接购买生成订单
        int orderID=Order.generateOrder(name,singlePrice,user.getID(),bookID);
        req.getRequestDispatcher("/clientOrder?method=QueryByID&orderID="+orderID).forward(req,resp);
    }
    //查看订单详情
    protected void DetailOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));
        Order order=OrderRepository.QueryOrderByID(orderID);
        List<OrderItem>orderItems=OrderItemRepository.QueryOrderItemByOrderID(orderID);

        req.setAttribute("orderItems",orderItems);
        req.setAttribute("totalCount",order.getTotalCount());
        req.setAttribute("orderPrice",order.getOrderPrice());
        req.setAttribute("orderStatus",order.getStatus());
        req.getRequestDispatcher("pages/order/orderDetail.jsp").forward(req,resp);
    }
}
