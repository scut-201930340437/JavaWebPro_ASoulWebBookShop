package com.GeWei.Services;

import com.GeWei.EntityClass.Scan;
import com.GeWei.EntityClass.User;
import com.GeWei.Repository.ScanRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/scan")
public class ScanServlet extends BaseServlet{
    protected void ListMyScan(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        int userID=((User)httpSession.getAttribute("user")).getID();
        List<Scan> scans= ScanRepository.ListMyScan(userID);
        req.setAttribute("scanRecords",scans);
        req.setAttribute("scanCount",scans.size());
        req.getRequestDispatcher("pages/scan/scanRecord.jsp").forward(req,resp);
    }

    protected void deleteScan(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        int userID=((User)httpSession.getAttribute("user")).getID();
        int bookID=Integer.parseInt(req.getParameter("bookID"));
        int res=ScanRepository.deleteScanRecord(userID,bookID);
        req.getRequestDispatcher("/scan?method=ListMyScan").forward(req,resp);
    }

    protected void clearScan(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession httpSession=req.getSession();
        int userID=((User)httpSession.getAttribute("user")).getID();
        int res=ScanRepository.clearScanRecord(userID);
        req.getRequestDispatcher("/scan?method=ListMyScan").forward(req,resp);
    }

}
