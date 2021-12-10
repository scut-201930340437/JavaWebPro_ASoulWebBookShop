package com.GeWei.Services;

import com.GeWei.EntityClass.Book;
import com.GeWei.EntityClass.Page;
import com.GeWei.EntityClass.User;
import com.GeWei.Repository.BookRepository;
import com.GeWei.Repository.ScanRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/clientBook")
public class ClientBookServlet extends BaseServlet{
    protected void pageBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo=1;
        //获取页码参数，默认为1
        if(req.getParameter("pageNo")!=null){
            pageNo=Integer.parseInt(req.getParameter("pageNo"));
        }
        //创建page对象管理分页数据
        Page page=new Page(pageNo);
        //从book表中获取数据
        page.setPageSize(10);page.setPageBooks(BookRepository.QueryBooksPage(pageNo,10));page.setPageTotalCount(Book.getNum());
        //计算总页数
        if((page.getPageTotalCount()%page.getPageSize())==0){
            page.setPageTotal(page.getPageTotalCount()/page.getPageSize());
        }else {
            page.setPageTotal(page.getPageTotalCount()/page.getPageSize()+1);
        }
        //设置page
        req.setAttribute("page",page);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

    protected void searchByName (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String bookName=req.getParameter("bookName");
        Page page=new Page(1);
        //从book表获取数据
        page.setPageBooks(BookRepository.QueryBooksByName(bookName));page.setPageTotalCount(page.getPageBooks().size());
        if((page.getPageTotalCount()%page.getPageSize())==0){
            page.setPageTotal(page.getPageTotalCount()/page.getPageSize());
        }else {
            page.setPageTotal(page.getPageTotalCount()/page.getPageSize()+1);
        }
        //设置page
        req.setAttribute("page",page);
        req.setAttribute("bookName",bookName);
        if(req.getParameter("isAdmin")==null){
            req.getRequestDispatcher("pages/client/index.jsp").forward(req,resp);
        }else{
            //该方法也会被后台的书籍管理系统调用
            req.getRequestDispatcher("pages/manager/book_manager.jsp").forward(req,resp);
        }
    }

    protected void searchByID (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int bookID=Integer.parseInt(req.getParameter("bookID"));
        //增加浏览量
        BookRepository.UpdateBookVisit(bookID);

        HttpSession httpSession=req.getSession();
        User user=(User) httpSession.getAttribute("user");
        //如果已经登录且为普通用户，添加浏览记录
        if(user!=null&&user.getRoot()==0){
            int userID=user.getID();
            Date date=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String createTime=format.format(date);
            ScanRepository.addOrUpdateRec(userID,bookID,createTime);
        }
        //从book表获取数据
        Book bookByID=BookRepository.QueryBookByID(bookID);
        req.setAttribute("bookByID",bookByID);
        req.getRequestDispatcher("pages/client/book_Info.jsp").forward(req,resp);
    }
}
