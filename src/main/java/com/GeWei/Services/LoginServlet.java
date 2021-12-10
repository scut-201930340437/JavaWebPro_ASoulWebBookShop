package com.GeWei.Services;

import com.GeWei.EntityClass.Cart;
import com.GeWei.EntityClass.CartItem;
import com.GeWei.EntityClass.User;
import com.GeWei.Repository.CartItemRepository;
import com.GeWei.Repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        String account=req.getParameter("account");
        String password=req.getParameter("password");

        String bookID="";
        if(req.getParameter("bookID")!=null){
            bookID=req.getParameter("bookID");
        }
        //在数据库中查询是否有该用户
        User user=UserRepository.QueryUser(account,password);
        if(user!=null){
            //如果有且是普通用户
            if(user.getRoot()==0){
                //构造购物车实例
                List<CartItem> items= CartItemRepository.QueryCartItemByOwnerID(user.getID());
                int totalCount=0;
                double allItemPrice=0.0;
                Map<Integer,CartItem> itemsMap=new HashMap<>();
                for (CartItem cartItem:items){
                    totalCount+=cartItem.getCount();
                    allItemPrice+=cartItem.getTotalPrice();
                    itemsMap.put(cartItem.getID(),cartItem);
                }
                Cart cart=new Cart(user.getID(),totalCount,allItemPrice,itemsMap);
                //设置购物车
                session.setAttribute("cart",cart);
            }
            //设置user
            session.setAttribute("user",user);
            if(bookID.equals("")){
                resp.sendRedirect("index.jsp");
            } else{
                req.getRequestDispatcher("/clientBook?method=searchByID&bookID="+bookID).forward(req,resp);
            }
        }
        //没有该用户
        else{
            req.setAttribute("account",account);
            req.setAttribute("password",password);
            //设置错误提示信息
            req.setAttribute("msg","用户名或密码错误！");
            //转回登录页面
            req.getRequestDispatcher("pages/user/login.jsp").forward(req,resp);
        }
    }
}
