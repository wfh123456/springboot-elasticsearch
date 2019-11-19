package com.wfh.base;


import com.wfh.SearchCondition;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
                       case eq: // 绝对匹配查询
                           queryBuilder.withQuery(QueryBuilders.termQuery(rule.getField(), rule.getData()));
                           break;
                       case cn: // 模糊查询，但是这个好像是不好使
                           queryBuilder.withQuery(QueryBuilders.wildcardQuery(rule.getField(), "*"+rule.getData()+"*"));
                           break;
                       case bw: // 前缀查询
                           queryBuilder.withQuery(QueryBuilders.prefixQuery(rule.getField(), rule.getData().toString()));
                           break;
                       case ew: // 右配置
                           queryBuilder.withQuery(QueryBuilders.wildcardQuery(rule.getField(), "*"+rule.getData()));
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
