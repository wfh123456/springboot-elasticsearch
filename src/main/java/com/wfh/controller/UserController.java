package com.wfh.controller;


import com.wfh.entity.Item;
import com.wfh.entity.User;
import com.wfh.repository.ItemRepository;
import com.wfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    /**
     *  增加单个索引
     * @param
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestBody User entity){
        userRepository.save(entity);
        return "保存用户成功";
    }


}
