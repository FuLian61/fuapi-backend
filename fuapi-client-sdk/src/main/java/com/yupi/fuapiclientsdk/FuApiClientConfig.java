package com.yupi.fuapiclientsdk;

import com.yupi.fuapiclientsdk.client.FuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 浮涟
 * @Since 2024/4/12
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties("fuapi.client")
@Data
@ComponentScan
public class FuApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public FuApiClient fuApiClient(){
        return  new FuApiClient(accessKey,secretKey);
    }

}
