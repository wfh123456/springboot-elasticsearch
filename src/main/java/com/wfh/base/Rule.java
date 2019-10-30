package com.wfh.base;


import lombok.Data;

@Data
public class Rule {

    // 查询字段名称
    private String field;
    // 查询数据
    private Object data;
    // 查询类型
    private String op;

}
