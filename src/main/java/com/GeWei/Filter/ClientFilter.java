package com.GeWei.Filter;

import com.GeWei.EntityClass.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ClientFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if(user != null){
            filterChain.doFilter(servletRequest,servletResponse);
        } else{
            httpServletRequest.getRequestDispatcher("pages/user/login.jsp?bookID="+httpServletRequest.getParameter("bookID")).forward(servletRequest,servletResponse);
        }
    }
}
