package com.wfh;

import com.wfh.entity.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringbootElasticsearchApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @Description:创建索引，会根据Item类的@Document注解信息来创建
     * @Author: wfh
     * @Date: 2019
     */
    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Item.class);
    }

}
