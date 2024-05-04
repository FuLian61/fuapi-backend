package com.yupi.fuapicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.fuapicommon.model.entity.InterfaceInfo;

/**
* @author 浮涟i
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-03-19 21:58:38
*/
public interface InnerInterfaceInfoService{

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
