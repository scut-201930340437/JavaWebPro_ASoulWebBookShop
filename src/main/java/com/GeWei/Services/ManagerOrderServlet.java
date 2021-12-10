package com.GeWei.Services;

import com.GeWei.EntityClass.Order;
import com.GeWei.EntityClass.OrderItem;
import com.GeWei.EntityClass.User;
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

@WebServlet("/managerOrder")
public class ManagerOrderServlet extends BaseServlet{
    //查看所有订单
    protected void ListOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Order>orders=OrderRepository.QueryOrder();
        req.setAttribute("orders",orders);
        req.setAttribute("orderCount",orders.size());
        double sumPrice=0.0;
        for (Order order:orders){
            sumPrice+=order.getOrderPrice();
        }
        req.setAttribute("sumPrice",sumPrice);
        req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);
    }
    //发货
    protected void UpdateOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));
        int res= OrderRepository.UpdateOrder(orderID,2);
        req.getRequestDispatcher("/managerOrder?method=ListOrder").forward(req,resp);
    }
    //查询订单
    protected void QueryByID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));

        List<Order>orders=new ArrayList<>();
        Order order=OrderRepository.QueryOrderByID(orderID);
        if(order!=null){
            orders.add(order);
            req.setAttribute("orders",orders);
            req.setAttribute("orderCount",1);
            req.setAttribute("sumPrice",orders.get(0).getOrderPrice());
        }else{
            req.setAttribute("msg","无法查询到该订单，请确认订单号无误。");
        }
        req.setAttribute("isQ","1");
        req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);
    }
    //根据状态查询订单
    protected void QueryOrderByStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderStatus=Integer.parseInt(req.getParameter("orderStatus"));
        List<Order>orders=OrderRepository.QueryOrder();
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
    //查看订单详情
    protected void DetailOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int orderID=Integer.parseInt(req.getParameter("orderID"));
        Order order=OrderRepository.QueryOrderByID(orderID);
        List<OrderItem>orderItems= OrderItemRepository.QueryOrderItemByOrderID(orderID);

        req.setAttribute("orderItems",orderItems);
        req.setAttribute("totalCount",order.getTotalCount());
        req.setAttribute("orderPrice",order.getOrderPrice());
        req.setAttribute("orderStatus",order.getStatus());
        req.getRequestDispatcher("pages/order/orderDetail.jsp").forward(req,resp);
    }
}
