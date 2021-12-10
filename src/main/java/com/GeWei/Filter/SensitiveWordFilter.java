package com.GeWei.Filter;

import javax.servlet.*;
import java.io.IOException;


public class SensitiveWordFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String account=servletRequest.getParameter("account");
        if(account!=null) {
            account=account.replaceAll("敏感词","***");
            servletRequest.setAttribute("account",account);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
