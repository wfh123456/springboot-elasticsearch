package com.wfh.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "user",type = "docs",shards = 1,replicas = 0)
@Data
public class User {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name; //姓名

    @Field(type = FieldType.Keyword)
    private Integer age;// 年龄

    @Field(type = FieldType.Keyword)
    private String sex; // 性别

    @Field(type = FieldType.Date)
    private Date birthday; // 生日

}
