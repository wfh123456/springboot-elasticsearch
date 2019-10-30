package com.wfh.base;


import lombok.Data;

@Data
public class ElasticPage {
    private Integer  pageIndex = 0;
    private Integer  pageSize = 10;
}
