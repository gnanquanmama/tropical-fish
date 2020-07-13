/*
 Navicat Premium Data Transfer

 Source Server         : 立构星-uat
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : 192.168.126.80:3307
 Source Schema         : seckillcenter

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 19/06/2020 11:17:27
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
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '编码生成规则' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of base_generate_code
-- ----------------------------
INSERT INTO `base_generate_code` VALUES ('1', '大套餐-活动配置编码', 'BIG-PACKAGE-ACTIVITY-CONFIG', 'com.mcoding.modular.biz.generatecode.strategy.DateIncrementStrategy', NULL, NULL, '202006180000001', '2020020900001', '9999999', '2020-02-09 12:39:47', '2020-02-09 12:39:51', 194);
INSERT INTO `base_generate_code` VALUES ('2', '大套餐-活动订单编码', 'BIG-PACKAGE-ACTIVITY-ORDER', 'com.mcoding.modular.biz.generatecode.strategy.DateIncrementStrategy', NULL, NULL, '202006190000002', '2020020900001', '9999999', '2020-02-09 12:39:47', '2020-02-09 12:39:51', 2847);

SET FOREIGN_KEY_CHECKS = 1;


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
  `order_quantity` int(11) NULL DEFAULT 0 COMMENT '产生订单数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` int(11) NOT NULL DEFAULT 1 COMMENT '版本',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除,0为否，1为是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES (2, 'oLrji5KPgx-4V_WFMcqX5D3c8fgs', NULL, NULL, '東', NULL, 0, 'https://wx.qlogo.cn/mmopen/vi_32/v6pQJTnBcricplYxWPgpKhWBGTFLSQTxmKzN9ADFdB8IFDPXRvGQzB2ccutEj3Bt7qyVUpe08b7Aqp8Skb2wUqw/132', 1, 'Guangdong', 'Guangzhou', 'China', 195, 'jxs00071', NULL, '4028fe58717d22ca017181ba292e0008', 'S0000020555', '我来当门店', NULL, 0, '2020-04-15 10:49:18', '2020-04-16 14:43:59', 1, 0);
INSERT INTO `base_user` VALUES (3, 'ofind4vDz5-NVXMOUFcdW1jKi5bI', NULL, '13800000000', 'tomcatuw', NULL, 0, 'https://sdfsdf', 0, 'guangdong', 'guangzhou', 'tianhe', 195, 'lgx-000012', NULL, NULL, NULL, NULL, NULL, 0, '2020-04-20 17:38:14', '2020-06-21 21:27:27', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
