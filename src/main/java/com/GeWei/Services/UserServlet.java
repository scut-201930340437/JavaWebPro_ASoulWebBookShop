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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {

    protected void AjaxExistsAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String accountNeededQuery=req.getParameter("Account");
        User user=UserRepository.QueryUser(accountNeededQuery,null);
        if(user!=null){
            resp.getWriter().write("1");
        } else{
            resp.getWriter().write("0");
        }
    }

    protected void Register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session=req.getSession();
        String accStr=req.getParameter("Account");
        String passStr=req.getParameter("Password");
        String emailStr=req.getParameter("E_mail");
        //在user表添加新的记录，user表会自动生成一个唯一的用户编号
        int newUserID = UserRepository.AddUser(accStr,passStr,emailStr);
        if(newUserID>0){
            //创建用户对象
            User user=new User(newUserID,accStr,passStr,emailStr);
            //创建用户的购物车对象
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
            //设置user和购物车
            session.setAttribute("user",user);session.setAttribute("cart",cart);

            resp.sendRedirect("pages/user/regist_success.jsp");
        } else{
            req.setAttribute("account",accStr);
            req.setAttribute("e_mail",emailStr);
            req.setAttribute("msg","注册失败！");
            req.getRequestDispatcher("pages/user/regist.jsp").forward(req,resp);
        }
    }

    protected void Update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session=req.getSession();
        User user=(User) session.getAttribute("user");
        String newName=req.getParameter("newName");
        String newTele=req.getParameter("newTele");
        String newEmail=req.getParameter("newEmail");
        user.setName(newName);user.setTelephone(newTele);user.setE_mail(newEmail);
        UserRepository.UpdateUser(user.getID(),newName,newTele,newEmail);
        session.setAttribute("user",user);
        req.setAttribute("msg","修改成功!");
        req.getRequestDispatcher("pages/user/UpdateUser.jsp").forward(req,resp);
    }
}
