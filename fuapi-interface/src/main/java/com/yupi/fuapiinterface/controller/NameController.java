package com.yupi.fuapiinterface.controller;

/**
 * @Author 浮涟
 * @Since 2024/4/10
 * @Version 1.0
 */

import com.yupi.fuapiclientsdk.model.User;
import com.yupi.fuapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *  名称API
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name,HttpServletRequest request){
        System.out.println(request.getHeader("yupi"));
        return  "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return  "POST 你的名字是" + name;
    }


    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request){
        String result = "POST 用户名字是" + user.getUserName();
        // 调用成功后，次数+1

        return result;
    }
}
