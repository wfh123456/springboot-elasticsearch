package com.wfh.base;


import com.wfh.SearchCondition;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 *  查询条件封装类
 */

public class Search {

    // 查询条件
    private SearchCondition cond;
    // 分页
    private ElasticPage pageable;

    public NativeSearchQueryBuilder toWithQuery(Class entityClass){
       if(cond.getRules() == null || cond.getRules().size() == 0){
             return new NativeSearchQueryBuilder();
       }
       NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        cond.getRules().forEach(rule ->{
           if(rule != null && !StringUtils.isEmpty(rule.getField()) ){
               // 判断是否存在这个属性
               boolean entityFLag = false;
               for (Field file : entityClass.getDeclaredFields() ) {
                    String key = file.getName();
                    if(key.equals(rule.getField())){
                        entityFLag = true;
                        break;
                    }
               }
               if(entityFLag){
                   switch(Operate.of(rule.getOp())) {
                       case eq:
                           queryBuilder.withQuery(QueryBuilders.termQuery(rule.getField(), rule.getData()));
                           break;
                       case cn:
                           queryBuilder.withQuery(QueryBuilders.fuzzyQuery(rule.getField(), rule.getData()));
                           break;
                       case bw:
                           queryBuilder.withQuery(QueryBuilders.prefixQuery(rule.getField(), rule.getData().toString()));
                           break;
                       default:
                           queryBuilder.withQuery(QueryBuilders.termQuery(rule.getField(), rule.getData()));
                           break;
                   }
               }
           }
       });
       return queryBuilder;
    }

    public SearchCondition getCond() {
        return this.cond;
    }

    public void setCond(final SearchCondition cond) {
        this.cond = cond;
    }

    public ElasticPage getPageable() {
        return pageable;
    }

    public void setPageable(ElasticPage pageable) {
        this.pageable = pageable;
    }
}
