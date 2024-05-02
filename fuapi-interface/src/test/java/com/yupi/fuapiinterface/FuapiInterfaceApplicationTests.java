package com.yupi.fuapiinterface;

import com.yupi.fuapiclientsdk.client.FuApiClient;
import com.yupi.fuapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class FuapiInterfaceApplicationTests {

    @Resource
    private FuApiClient fuApiClient;

    @Test
    void contextLoads() {
        String yupi = fuApiClient.getNameByGet("yupi");
        User user = new User();
        user.setUserName("fulian");
        String userNameByPost = fuApiClient.getUserNameByPost(user);
        System.out.println(yupi);
        System.out.println(userNameByPost);
    }

}
