use fuapi;

-- 接口信息
create table if not exists fuapi.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '接口名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    'requestParams' text not null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息';

insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('秦明哲', '马俊驰', 'www.marcelle-champlin.biz', '曾伟诚', '苏果', 0, '宋智宸', 9);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('金伟泽', '唐睿渊', 'www.edgar-white.co', '武烨霖', '余哲瀚', 0, '丁鹏飞', 8813386251);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('万炫明', '孙越彬', 'www.keli-cummerata.info', '赵子骞', '曹弘文', 0, '唐博文', 11756425);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('熊哲瀚', '黎峻熙', 'www.loren-bartell.co', '薛修杰', '苏鸿煊', 0, '方志泽', 5);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('郝文昊', '邹擎宇', 'www.scot-schinner.org', '赵致远', '郑晓啸', 0, '钱金鑫', 1915086488);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黄天宇', '高浩轩', 'www.zula-hodkiewicz.name', '顾思', '余明辉', 0, '唐彬', 715);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('戴懿轩', '周泽洋', 'www.laure-grimes.com', '吴志强', '黄文', 0, '宋荣轩', 94500262);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黄鑫鹏', '许思聪', 'www.drew-hoeger.co', '白鸿煊', '邵靖琪', 0, '董雨泽', 90);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('石彬', '顾明杰', 'www.caleb-ferry.com', '崔琪', '袁风华', 0, '熊明杰', 1898366358);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('唐熠彤', '赖聪健', 'www.buford-satterfield.biz', '唐天磊', '龙建辉', 0, '梁明杰', 382);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('姜文昊', '梁子轩', 'www.joaquin-mills.name', '沈思淼', '曹越彬', 0, '梁泽洋', 7435576);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('薛懿轩', '汪天磊', 'www.sidney-frami.biz', '田潇然', '冯智辉', 0, '顾明', 47126165);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('戴苑博', '朱黎昕', 'www.fidel-gusikowski.info', '雷鸿煊', '汪致远', 0, '熊黎昕', 8);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('杜旭尧', '江立辉', 'www.carol-mueller.org', '萧弘文', '白烨磊', 0, '郭晓啸', 2996);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('夏钰轩', '邵荣轩', 'www.cristopher-collins.co', '韩鸿煊', '阎鹏煊', 0, '侯博涛', 93);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('方天翊', '毛凯瑞', 'www.salvador-hauck.io', '冯瑞霖', '林志强', 0, '田健雄', 128517);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('戴潇然', '董航', 'www.esta-beahan.co', '韦子轩', '胡天磊', 0, '于文轩', 7997874975);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('覃修杰', '余熠彤', 'www.marcellus-hoeger.net', '莫修洁', '谢哲瀚', 0, '彭晓啸', 19368);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('沈文昊', '范雨泽', 'www.ettie-goodwin.name', '萧展鹏', '阎思', 0, '雷烨霖', 811564);
insert into fuapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('段君浩', '周潇然', 'www.marlin-jakubowski.com', '夏钰轩', '龚思远', 0, '韦锦程', 3426272);

-- 用户调用接口关系表
create table if not exists fuapi.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常 1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系表';