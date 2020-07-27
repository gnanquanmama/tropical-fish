/*
 Navicat Premium Data Transfer

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 27/07/2020 22:22:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_generate_code
-- ----------------------------
DROP TABLE IF EXISTS `base_generate_code`;
CREATE TABLE `base_generate_code`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `target_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目标',
  `strategy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成策略:自增策略auto_increment',
  `prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前缀',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀',
  `current_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成的下一个号码',
  `start_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始的号码',
  `max_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最大的值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '编码生成规则' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of base_generate_code
-- ----------------------------
INSERT INTO `base_generate_code` VALUES ('1', '大套餐-活动配置编码', 'BIG-PACKAGE-ACTIVITY-CONFIG', 'com.mcoding.modular.generatecode.strategy.DateIncrementStrategy', NULL, NULL, '202007210000001', '2020020900001', '9999999', '2020-02-09 12:39:47', '2020-02-09 12:39:51', 157);
INSERT INTO `base_generate_code` VALUES ('2', '大套餐-活动订单编码', 'BIG-PACKAGE-ACTIVITY-ORDER', 'com.mcoding.modular.generatecode.strategy.DateIncrementStrategy', NULL, NULL, '202007270000001', '2020020900001', '9999999', '2020-02-09 12:39:47', '2020-02-09 12:39:51', 317);

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'openId',
  `unionId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'unionId',
  `mobile_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_status` int(11) NOT NULL DEFAULT 0 COMMENT '用户状态，1为正常，2为冻结',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` int(11) NULL DEFAULT NULL COMMENT '性别 0：未知、1：男、2：女',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域',
  `dealer_id` int(11) NULL DEFAULT NULL COMMENT '经销商ID',
  `dealer_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经销商编码',
  `dealer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经销商名称',
  `store_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店ID',
  `store_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店编码',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店名称',
  `binding_time` datetime(0) NULL DEFAULT NULL COMMENT '绑定时间',
  `binding_status` int(11) NULL DEFAULT 0 COMMENT '绑定状态，0为未绑定，1为已绑定',
  `order_quantity` int(11) NULL DEFAULT 0 COMMENT '产生订单数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除,0为否，1为是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES (2, 'oLrji5KPgx-4V_WFMcqX5D3c8fgs', NULL, '13800000001', '東', NULL, 0, 'https://wx.qlogo.cn/mmopen/vi_32/v6pQJTnBcricplYxWPgpKhWBGTFLSQTxmKzN9ADFdB8IFDPXRvGQzB2ccutEj3Bt7qyVUpe08b7Aqp8Skb2wUqw/132', 1, 'Guangdong', 'Guangzhou', 'China', 100278, 'JXS00071', '燕塘养猪场', '2c8f89d56c74fbfd016c752b7e5e0004', 'S0000011016', '西门烤鱼', NULL, 1, 0, '2020-04-15 10:49:18', '2020-07-23 14:47:10', 1, 0);
INSERT INTO `base_user` VALUES (3, 'ofind4vDz5-NVXMOUFcdW1jKi5bI', NULL, '13800000000', 'tomcatuw', NULL, 0, 'https://sdfsdf', 1, 'guangdong', 'guangzhou', 'tianhe', 195, 'lgx-000012', NULL, '', '', '', NULL, 0, 0, '2020-04-20 17:38:14', '2020-07-13 17:01:27', 1, 0);

-- ----------------------------
-- Table structure for base_user_token
-- ----------------------------
DROP TABLE IF EXISTS `base_user_token`;
CREATE TABLE `base_user_token`  (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `auth_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权token',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除,0为否，1为是',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户授权token' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of base_user_token
-- ----------------------------
INSERT INTO `base_user_token` VALUES (2, '8krl9N2IzOWRkNmQ3ZGM5NGM1NDlhZmM4M2Y5YTc1NTI2N2E=', '2020-07-21 00:26:28', '2020-07-27 15:53:18', 1, 0);
INSERT INTO `base_user_token` VALUES (5, 'y953fZDc1NGU2ZmIyZDAxNGQyN2FjNDkxN2I4MTk5ZGQ0YWY=', '2020-07-21 00:31:36', '2020-07-27 18:04:32', 1, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别(字典)',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id(多个逗号隔开)',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id(多个逗号隔开)',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态(字典)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '1124606971782160385', 'admin', '123456', 'abcdef', 'admin', '2018-11-16 00:00:00', 'M', 'sn93@qq.com', '18200000000', '1', 25, 'ENABLE', '2016-01-29 08:49:53', NULL, '2019-06-28 14:38:19', 24, 25);

SET FOREIGN_KEY_CHECKS = 1;
