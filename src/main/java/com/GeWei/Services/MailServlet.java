package com.GeWei.Services;

import com.GeWei.EntityClass.Book;
import com.GeWei.EntityClass.User;
import com.GeWei.util.MailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/mail")
public class MailServlet extends BaseServlet{
    protected void SendMail (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            HttpSession session=req.getSession();
            User user = (User)session.getAttribute("user");
            //获取订单
            int orderID=Integer.parseInt(req.getParameter("orderID"));
            //线程发送邮件，防止出现耗时，和网站注册人数过多的情况；
            MailUtil mailUtil=new MailUtil(user,orderID);
            //启动线程，线程启动之后就会执行run方法来发送邮件
            mailUtil.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            req.getRequestDispatcher("pages/mail/sendSuccess.jsp").forward(req,resp);
        }
    }
}
