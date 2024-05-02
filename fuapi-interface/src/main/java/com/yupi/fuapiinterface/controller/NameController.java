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
        String accessKey = request.getHeader("accessKey");
//        String secretKey = request.getHeader("secretKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");

        // todo 实际情况应该是去数据库中查是否已分配给用户
        if ( !accessKey.equals("yupi")){
            throw new RuntimeException("无权限");
        }
        // 校验随机数，模拟一下，直接判断nonce是否大于10000
        if ( Long.parseLong(nonce) > 10000){
            throw new RuntimeException("无权限");
        }

        // todo 时间和当前时间不能超过5分钟
//        if (timestamp){}
        // todo 实际情况中是从数据库中查出 sercetKey
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        // 如果生成的签名不一致，则抛出异常，并提示无权限
        if(!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }
        String result = "POST 用户名字是" + user.getUserName();
        // 调用成功后，次数+1

        return result;
    }
}
