package com.wfh.controller;


import com.wfh.entity.Item;
import com.wfh.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  基本查询
 */

@RestController
public class ItemController {

   @Autowired
   private ItemRepository itemRepository;

   /**
    *  增加单个索引
    * @param
    * @return
    */
   @PostMapping("/item/add")
    public String add(@RequestBody Item t){
       itemRepository.save(t);
       return "保存成功";
   }

   /**
    *  查询所有索引
    * @return
    */
   @PostMapping("/item/findAll")
    public Iterable<Item> findAll(){
       return itemRepository.findAll();
   }
   /**
    *  删除指定索引内容
    * @return
    */
   @PostMapping("/item/delById")
    public String delById(@RequestBody Item item){
      itemRepository.delete(item);
      return "删除成功";
   }
   /**
    *  删除指定索引内容
    * @return
    */
   @PostMapping("/item/delAll")
    public String delAll(){
      itemRepository.deleteAll();
      return "删除全部数据成功";
   }
   /**
    *  自定义查询
    *   价格之间查询
    * @return
    */
   @PostMapping("/item/findByPriceBetween")
    public List<Item> findByPriceBetween(@RequestParam Double price1,@RequestParam Double price2){

      List<Item> itemList = itemRepository.findByPriceBetween(price1,price2);
      return itemList;
   }
   /**
    *  自定义查询
    *   模糊查询配牌数据 且根据价格排序
    * @return
    */
   @PostMapping("/item/findByBrandLikeOrderByPrice")
    public List<Item> findByBrandLikeOrderByPrice(@RequestParam String brand){
      List<Item> itemList = itemRepository.findByBrandLikeOrderByPrice(brand);
      return itemList;
   }
   /**
    *    自定义查询
    *   全模糊查询  且根据价格排序
    * @return
    */
   @PostMapping("/item/findByBrandContainingOrderByPriceDesc")
    public List<Item> findByBrandContainingOrderByPriceDesc(@RequestParam String brand){
      List<Item> itemList = itemRepository.findByBrandContainingOrderByPriceDesc(brand);
      return itemList;
   }

}
