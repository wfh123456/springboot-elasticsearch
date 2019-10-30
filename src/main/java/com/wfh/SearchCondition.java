package com.wfh;


import com.wfh.base.Rule;
import lombok.Data;

import java.util.List;

@Data
public class SearchCondition {
     private List<Rule> rules;
     private String  fieldSort;
     private String  sortType;
}
