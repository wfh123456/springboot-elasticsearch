package com.wfh.base;


import lombok.Data;

public class ElasticPage {
    private Integer  pageIndex;
    private Integer  pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {

        this.pageIndex = pageIndex == 0 ? 1 : pageIndex - 1;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? 10 : pageSize;
    }
}
