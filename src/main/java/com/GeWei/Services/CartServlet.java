package com.GeWei.Services;

import com.GeWei.EntityClass.Cart;
import com.GeWei.EntityClass.CartItem;
import com.GeWei.EntityClass.User;
import com.GeWei.Repository.CartItemRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends BaseServlet{
    protected void AddCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        Cart cart=(Cart) httpSession.getAttribute("cart");
        //构造购物车项
        int ownerID=cart.getOwnerID();
        String name=req.getParameter("bookName");
        double singlePrice=Double.parseDouble(req.getParameter("singlePrice"));
        int bookID=Integer.parseInt(req.getParameter("bookID"));
        //在购物车中添加一条记录
        cart.addItem(name,singlePrice,ownerID,bookID);
        httpSession.setAttribute("cart",cart);
        resp.getWriter().write("加入成功！在购物车等您~");
    }

    protected void ClearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        Cart cart=(Cart) httpSession.getAttribute("cart");
        cart.clearItem();
        httpSession.setAttribute("cart",cart);
        req.getRequestDispatcher("pages/cart/cart.jsp").forward(req,resp);
    }

    protected void DeleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        Cart cart=(Cart) httpSession.getAttribute("cart");
        int ID=Integer.parseInt(req.getParameter("ID"));
        cart.deleteItem(ID);
        httpSession.setAttribute("cart",cart);
        req.getRequestDispatcher("pages/cart/cart.jsp").forward(req,resp);
    }

    protected void UpdateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        Cart cart=(Cart) httpSession.getAttribute("cart");
        int ID=Integer.parseInt(req.getParameter("ID"));
        int count=Integer.parseInt(req.getParameter("count"));
        cart.updateItem(ID,count);
        httpSession.setAttribute("cart",cart);
        req.getRequestDispatcher("pages/cart/cart.jsp").forward(req,resp);
    }
}
