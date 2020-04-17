
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
INSERT INTO `base_generate_code` VALUES ('1', '大套餐-活动配置编码', 'BIG-PACKAGE-ACTIVITY-CONFIG', 'com.mcoding.modular.generatecode.strategy.DateIncrementStrategy', NULL, NULL, '202004090000001', '2020020900001', '9999999', '2020-02-09 12:39:47', '2020-02-09 12:39:51', 184);
INSERT INTO `base_generate_code` VALUES ('2', '大套餐-活动订单编码', 'BIG-PACKAGE-ACTIVITY-ORDER', 'com.mcoding.modular.generatecode.strategy.DateIncrementStrategy', NULL, NULL, '202003310000001', '2020020900001', '9999999', '2020-02-09 12:39:47', '2020-02-09 12:39:51', 319);

SET FOREIGN_KEY_CHECKS = 1;
