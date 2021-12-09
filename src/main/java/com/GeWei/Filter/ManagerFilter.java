package com.GeWei.Filter;

import com.GeWei.EntityClass.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if(user != null && user.getRoot()==1){
            filterChain.doFilter(servletRequest,servletResponse);
        } else{
            String basePath=servletRequest.getScheme()
                    + "://"
                    + httpServletRequest.getServerName()
                    + ":"
                    + httpServletRequest.getServerPort()
                    + httpServletRequest.getContextPath()
                    + "/";
            if(user==null){
                HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
                httpServletResponse.sendRedirect(basePath+"pages/user/login.jsp");
            }else {
                HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
                httpServletResponse.sendRedirect(basePath);
            }
        }
    }
}
