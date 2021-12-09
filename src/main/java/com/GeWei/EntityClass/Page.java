package com.GeWei.EntityClass;

import java.util.List;

public class Page {
    private int pageNo;
    private int pageTotal;
    private int pageSize=10;
    private int pageTotalCount;
    private List<Book> pageBooks;
    private String url="/clientBook?method=pageBooks";

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(int pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<Book> getPageBooks() {
        return pageBooks;
    }

    public void setPageBooks(List<Book> pageBooks) {
        this.pageBooks = pageBooks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page(int pageNo) {
        this.pageNo = pageNo;
    }
}
