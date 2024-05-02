package com.yupi.fuapigateway;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.yupi.fuapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 用户发送请求到 API 网关
        // 2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识："+request.getId());
        log.info("请求路径："+request.getPath().value());
        log.info("请求方法："+request.getMethod());
        log.info("请求参数："+request.getQueryParams());
        log.info("请求来源地址："+request.getRemoteAddress());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址："+ sourceAddress);

        ServerHttpResponse response = exchange.getResponse();

        // 3. 黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)){
           return handleNoAuth(response);
        }

        // 4. 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        // todo 实际情况应该是去数据库中查是否已分配给用户
        if ( !"yupi".equals(accessKey)){
            return handleNoAuth(response);
        }
        // 校验随机数，模拟一下，直接判断nonce是否大于10000
        if ( Long.parseLong(nonce) > 10000){
            return handleNoAuth(response);
        }
        // 时间和当前时间不能超过5分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES){
            return handleNoAuth(response);
        }
        // todo 实际情况中是从数据库中查出 sercetKey
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        // 如果生成的签名不一致，则抛出异常，并提示无权限
        if(!sign.equals(serverSign)){
            return handleNoAuth(response);
        }

        // 5. 请求的模拟接口是否存在
        // todo 从数据库中查询模拟接口是否存在，以及请求方法是否匹配（还可以校验请求参数）

        // 6. 请求转发，调用模拟接口
        Mono<Void> filter = chain.filter(exchange);

        // 7. 响应日志
        log.info("响应：" + response.getStatusCode());
        return handleResponse(exchange,chain);

//        log.info("custom global filter");
//        return filter;
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain){
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                return chain.filter(exchange);//降级处理返回数据
            }
            // 装饰、增强能力
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                // 等调用完转发的接口才会执行
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 往返回值里写数据
                        // 拼接字符串
                        return super.writeWith(
                                fluxBody.buffer().map(dataBuffers -> {
                                    // 8. todo 调用成功，接口调用次数 + 1 invokeCount

                                    // 合并多个流集合，解决返回体分段传输
                                    DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                                    DataBuffer buff = dataBufferFactory.join(dataBuffers);
                                    byte[] content = new byte[buff.readableByteCount()];
                                    buff.read(content);
                                    DataBufferUtils.release(buff);//释放掉内存

                                    // 构建返回日志
                                    StringBuilder sb2  = new StringBuilder(200);
                                    List<Object> rspArgs = new ArrayList<>();
                                    rspArgs.add(originalResponse.getStatusCode().value());
                                    String data = new String(content, StandardCharsets.UTF_8); // data
                                    sb2.append(data);
                                    // 打印日志
                                    log.info("响应结果："+data);
                                    return bufferFactory.wrap(content);
                            }));
                    } else {
                        // 9. 调用失败，返回一个规范的业务码
//                        return handleNoError(response);
                        log.error("<-- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            // 设置 response 对象为装饰过的
            return chain.filter(exchange.mutate().response(decoratedResponse).build());

        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }



    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
    public Mono<Void> handleNoError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}