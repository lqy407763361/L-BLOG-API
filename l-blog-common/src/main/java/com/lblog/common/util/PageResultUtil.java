package com.lblog.common.util;

import java.util.List;

public class PageResultUtil<T> {
    private Integer startPage;

    private Integer size;

    private Integer total;

    private List<T> list;

    public PageResultUtil(Integer startPage, Integer size, Integer total, List<T> list){
        this.startPage = startPage;
        this.size = size;
        this.total = total;
        this.list = list;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
