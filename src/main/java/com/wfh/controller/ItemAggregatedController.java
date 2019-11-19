package com.wfh.controller;


import com.wfh.base.PageElementGrid;
import com.wfh.entity.Item;
import com.wfh.repository.ItemRepository;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *   聚合为桶
 */

@RestController
public class ItemAggregatedController {

   @Autowired
   private ItemRepository itemRepository;

   /**
    *   以品牌分组
    * @param
    * @return
    */
   @PostMapping("/group/brands")
    public PageElementGrid brands(){

       NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
       // 不查询任何结果
       queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
       // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
       queryBuilder.addAggregation(
               AggregationBuilders.terms("brands").field("brand"));
       // 2、查询,需要把结果强转为AggregatedPage类型
       AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
       // 3、解析
       // 3.1、从结果中取出名为brands的那个聚合，
       // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
       StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
       // 3.2、获取桶
       List<StringTerms.Bucket> buckets = agg.getBuckets();

      List<Map> backList = buckets.stream().map(m ->{
           Map map = new HashMap();

           // 获取筒中的key,也就是品牌名称
           map.put("brands",m.getKeyAsString());
           // 、获取桶中的文档数量
           map.put("count",m.getDocCount());
           return map;

       }).collect(Collectors.toList());

       PageElementGrid result = PageElementGrid.<Map>newInstance()
               .total(backList.size())
               .items(backList).build();

       return result;
   }
   /**
    *   嵌套聚合 求价格的平均值
    * @param
    * @return
    */
   @PostMapping("/group/priceAvg")
    public PageElementGrid priceAvg(){

       NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
       // 不查询任何结果
       queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
       // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
       queryBuilder.addAggregation(
               AggregationBuilders.terms("brands").field("brand")
                       .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
       );
       // 2、查询,需要把结果强转为AggregatedPage类型
       AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
       // 3、解析
       // 3.1、从结果中取出名为brands的那个聚合，
       // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
       StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
       // 3.2、获取桶
       List<StringTerms.Bucket> buckets = agg.getBuckets();
       // 3.3、遍历
       List<Map> backList = buckets.stream().map(m ->{
           Map map = new HashMap();

           // 获取筒中的key,也就是品牌名称
           map.put("brands",m.getKeyAsString());
           // 、获取桶中的文档数量
           map.put("count",m.getDocCount());
           InternalAvg avg = (InternalAvg) m.getAggregations().asMap().get("priceAvg");
           map.put("priceAvg",avg.getValue());
           return map;

       }).collect(Collectors.toList());

       PageElementGrid result = PageElementGrid.<Map>newInstance()
               .total(backList.size())
               .items(backList).build();
       return result;
   }



}
