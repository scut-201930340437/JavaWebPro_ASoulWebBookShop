package com.GeWei.Services;

import com.GeWei.util.Db2CsvExporter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应方式为下载
        resp.setContentType("application/csv");
        Db2CsvExporter exporter = new Db2CsvExporter();
        String path=req.getServletContext().getRealPath("DownloadFiles/");
        String fileName=exporter.export("book",path);
        //设置下载文件名
        resp.setHeader("Content-Disposition","attachment;filename="+fileName);

        OutputStream outputStream=resp.getOutputStream();
        InputStream inputStream=new FileInputStream(path+fileName);

        int tmp=0;
        //云服务器上要加上BOM
        outputStream.write(239);outputStream.write(187);outputStream.write(191);
        while((tmp=inputStream.read())!=-1){
            outputStream.write(tmp);
        }
        inputStream.close();outputStream.close();
    }
}
