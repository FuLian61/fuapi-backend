package com.yupi.fuapicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.fuapicommon.model.entity.UserInterfaceInfo;

/**
* @author 浮涟i
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2024-04-14 10:32:37
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
