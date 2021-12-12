package com.GeWei.util;

import com.GeWei.EntityClass.Book;
import com.GeWei.EntityClass.Order;
import com.GeWei.EntityClass.OrderItem;
import com.GeWei.Repository.OrderItemRepository;
import com.GeWei.Repository.OrderRepository;
import com.sun.mail.util.MailSSLSocketFactory;
import com.GeWei.EntityClass.User;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
public class MailUtil extends Thread{
    //用于给用户发送邮件的邮箱
    private String from = "";
    //邮箱的用户名
    private String username = "";
    //邮箱的密码
    private String password = "";
    //发送邮件的服务器地址
    private String host = "smtp.qq.com";

    private User user;
    private int orderID;
    private Order order;
    private List<OrderItem>items;
    public MailUtil(User user, int orderID){
        this.user = user;this.orderID=orderID;
        this.order= OrderRepository.QueryOrderByID(orderID);
        this.items= OrderItemRepository.QueryOrderItemByOrderID(orderID);
    }

    //重写run方法的实现，在run方法中发送邮件给指定的用户
    @Override
    public void run() {
        try{
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");

            // 关于QQ邮箱，还要设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", true);
            prop.put("mail.smtp.ssl.socketFactory", sf);

            //1、创建定义整个应用程序所需的环境信息的 Session 对象
            Session sess = Session.getDefaultInstance(prop, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮件用户名、授权码
                    return new PasswordAuthentication("2478987785@qq.com", "rkbayrwalevgebae");
                }
            });

            //开启Session的debug模式，可以查看程序发送Email的运行状态
            sess.setDebug(true);

            //2、通过session得到transport对象
            Transport ts = sess.getTransport();

            //3、使用邮箱的用户名和授权码连上邮件服务器
            ts.connect(host, username, password);

            //4、创建邮件
            MimeMessage message = new MimeMessage(sess);
            message.setFrom(new InternetAddress(from)); //发件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getE_mail())); //收件人
            message.setSubject("一个魂儿书城订单邮件"); //邮件的标题

            String info = "尊敬的用户"+user.getAccount()+":\n"+"  感谢您在一个魂儿书城的购买!"+"订单号:"+orderID+"，商品将尽快送到您的手中，如有问题请联系书城客服!\n";
            info+="您所购买的商品如下:\n";
            OrderItem orderItem;
            int i=0;
            for (;i<items.size()-1;i++){
                orderItem=items.get(i);
                info+="  "+orderItem.getName()+":"+orderItem.getCount()+"本，\n";
            }
            orderItem=items.get(i);
            info+="  "+orderItem.getName()+":"+orderItem.getCount()+"本。\n";

            message.setContent(info, "text/html;charset=UTF-8");
            message.saveChanges();

            //发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
