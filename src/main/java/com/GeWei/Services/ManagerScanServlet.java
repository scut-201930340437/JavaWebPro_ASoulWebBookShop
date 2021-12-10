package com.GeWei.Services;

import com.GeWei.EntityClass.Scan;
import com.GeWei.Repository.ScanRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/managerScan")
public class ManagerScanServlet extends BaseServlet{
    protected void listScan(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Scan> scans= ScanRepository.ListScan();
        req.setAttribute("scans",scans);
        req.setAttribute("scanCount",scans.size());
        req.getRequestDispatcher("pages/manager/scan_manager.jsp").forward(req,resp);
    }
}
