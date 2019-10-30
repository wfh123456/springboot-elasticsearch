package com.wfh.controller;


import com.wfh.base.PageElementGrid;
import com.wfh.base.Search;
import com.wfh.base.SystemResponse;
import com.wfh.entity.Item;
import com.wfh.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *   自定义查询
 *
 *   NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
 *
 *   QueryBuilders.matchQuery(“title”, “小米手机”)：利用QueryBuilders来生成一个查询。QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询：
 *
 *
 *    Page<item>：默认是分页查询，因此返回的是一个分页的结果对象，包含属性：
 *    totalElements：总条数
 *    totalPages：总页数
 *    Iterator：迭代器，本身实现了Iterator接口，因此可直接迭代得到当前页的数据
 *    其它属性：
 *
 *    分页查询 :
 *     关键字:   queryBuilder.withPageable(PageRequest.of(page,size));
 *    排序  :
 *              queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
 */

@Controller
@RequestMapping("/matchquery")
@RestController
public class MatchQueryController {

    @Autowired
    private ItemRepository itemRepository;

    /**
     *  自定义查询
     * @return
     */
    @PostMapping("/listByPage")
    public PageElementGrid listByPage(@RequestBody Search search){

        NativeSearchQueryBuilder queryBuilder = builder(Item.class,search);
        queryBuilder.withPageable(PageRequest.of(search.getPageable().getPageIndex(),search.getPageable().getPageSize()));
        Page<Item> items = itemRepository.search(queryBuilder.build());

        PageElementGrid result = PageElementGrid.<Item>newInstance()
                .total(items.getTotalPages())
                .items(items.getContent()).build();
        return result;
    }

    // 查询条件处理
    private NativeSearchQueryBuilder builder(Class entityClass,Search search) {
        NativeSearchQueryBuilder builder = null;
        if(null == search || null == search.getCond()){
            builder = new NativeSearchQueryBuilder();
        } else {
            builder = search.toWithQuery(entityClass);
        }
        if(!StringUtils.isEmpty(search.getCond().getFieldSort()) || !StringUtils.isEmpty(search.getCond().getSortType())){
            builder.withSort(SortBuilders.fieldSort(search.getCond().getFieldSort()).order(SortOrder.fromString(search.getCond().getSortType())));
        }
        return builder;
    }


}
