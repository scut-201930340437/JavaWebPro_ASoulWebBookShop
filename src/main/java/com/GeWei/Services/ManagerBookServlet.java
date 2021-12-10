package com.GeWei.Services;

import com.GeWei.EntityClass.Book;
import com.GeWei.EntityClass.Page;
import com.GeWei.EntityClass.User;
import com.GeWei.Repository.BookRepository;
import com.GeWei.Repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/managerBook")
public class ManagerBookServlet extends BaseServlet{
    protected void AddBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNo=req.getParameter("pageNo");
        String bookName=req.getParameter("name");
        String bookAuthor=req.getParameter("author");
        double bookPrice=Double.parseDouble(req.getParameter("price"));
        int bookStock=Integer.parseInt(req.getParameter("stock"));
        int newBookID = BookRepository.AddBook(bookName,bookAuthor,bookPrice,0,bookStock,"static/img/default.jpg");
        Book.addBook();
        req.getRequestDispatcher("/managerBook?method=pageBooks"+"&pageNo="+pageNo).forward(req,resp);

    }

//    protected void ListBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        List<Book>books=BookRepository.QueryBooks();
//        req.setAttribute("books",books);
//        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
//    }

    protected void pageBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int pageNo=1,pageSize=5;
        if(req.getParameter("pageNo")!=null){
            pageNo=Integer.parseInt(req.getParameter("pageNo"));
        }
        Page page=new Page(pageNo);
        page.setPageSize(pageSize);page.setPageBooks(BookRepository.QueryBooksPage(pageNo,pageSize));page.setPageTotalCount(Book.getNum());
        if((page.getPageTotalCount()%page.getPageSize())==0){
            page.setPageTotal(page.getPageTotalCount()/page.getPageSize());
        }else {
            page.setPageTotal(page.getPageTotalCount()/page.getPageSize()+1);
        }
        page.setUrl("/managerBook?method=pageBooks");
        req.setAttribute("page",page);
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int ID=Integer.parseInt(req.getParameter("bookID"));
        String pageNo=req.getParameter("pageNo");
        Book bookByID=BookRepository.QueryBookByID(ID);
        req.setAttribute("bookByID",bookByID);
        req.setAttribute("pageNo",pageNo);
        req.setAttribute("operation","UpdateBook");
        req.getRequestDispatcher("pages/manager/book_edit.jsp").forward(req,resp);
    }

    protected void UpdateBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int id=Integer.parseInt(req.getParameter("id"));
        String newName=req.getParameter("name");
        String newAuthor=req.getParameter("author");
        Double newPrice=Double.parseDouble(req.getParameter("price"));
        int newSales=Integer.parseInt(req.getParameter("sales"));
        int newStock=Integer.parseInt(req.getParameter("stock"));
        String pageNo=req.getParameter("pageNo");

        int res=BookRepository.UpdateBookByID(id,newName,newAuthor,newPrice,newSales,newStock);
        req.getRequestDispatcher("/managerBook?method=pageBooks"+"&pageNo="+pageNo).forward(req,resp);
    }

    protected void DeleteBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int bookID=Integer.parseInt(req.getParameter("bookID"));
        int res=BookRepository.DeleteBookByID(bookID);
        if(res>0){
            Book.deleteBook();
        }else{
            req.setAttribute("errorMsg","无法删除，可能由于某用户在购物车中添加了该书籍！");
        }
        String pageNo=req.getParameter("pageNo");
        req.getRequestDispatcher("/managerBook?method=pageBooks"+"&pageNo="+pageNo).forward(req,resp);
    }
}
