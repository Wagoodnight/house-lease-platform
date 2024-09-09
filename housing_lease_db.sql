/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 90001
 Source Host           : 
 Source Schema         : housing_lease_db

 Target Server Type    : MySQL
 Target Server Version : 90001
 File Encoding         : 65001

 Date: 09/09/2024 18:21:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_house_rental_request
-- ----------------------------
DROP TABLE IF EXISTS `t_house_rental_request`;
CREATE TABLE `t_house_rental_request`  (
  `request_id` bigint NOT NULL AUTO_INCREMENT COMMENT '请求ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地点',
  `area` double(20, 2) NOT NULL COMMENT '面积',
  `room` int NOT NULL COMMENT '室',
  `hall` int NOT NULL COMMENT '厅',
  `rest` int NOT NULL COMMENT '卫',
  `is_kitchen` tinyint(1) NOT NULL COMMENT '是否有厨房',
  `is_balcony` tinyint(1) NOT NULL COMMENT '是否有阳台',
  `house_facing` int NOT NULL COMMENT '房屋朝向',
  `price` double(10, 2) NOT NULL COMMENT '价格',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`request_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_house_rental_request
-- ----------------------------

-- ----------------------------
-- Table structure for t_house_source
-- ----------------------------
DROP TABLE IF EXISTS `t_house_source`;
CREATE TABLE `t_house_source`  (
  `house_id` bigint NOT NULL AUTO_INCREMENT COMMENT '房源ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地点',
  `area` double(20, 2) NOT NULL COMMENT '面积',
  `room` int NOT NULL COMMENT '室',
  `hall` int NOT NULL COMMENT '厅',
  `rest` int NOT NULL COMMENT '卫',
  `is_kitchen` tinyint(1) NOT NULL COMMENT '是否有厨房',
  `is_balcony` tinyint(1) NOT NULL COMMENT '是否有阳台',
  `house_facing` int NOT NULL COMMENT '房屋朝向',
  `house_base64` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房屋照片base64',
  `price` double(10, 2) NOT NULL COMMENT '价格',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`house_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_house_source
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `operate_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用方法',
  `parameter` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '结果',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `age` int NOT NULL COMMENT '年龄',
  `sex` tinyint(1) NOT NULL COMMENT '性别',
  `id_card` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证',
  `role` int NOT NULL DEFAULT 1 COMMENT '权限（0游客、1会员、2管理员）',
  `published` tinyint(1) NOT NULL DEFAULT 1 COMMENT '房源权限',
  `rental_request` tinyint(1) NOT NULL DEFAULT 1 COMMENT '求购权限',
  `user_img` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户头像base64',
  `last_login_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1831302123552829443 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (20168523965977, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '14719847190', 'XXX@XXX', 12, 1, '13u19e743907X', 2, 1, 1, NULL, '2024-09-09 15:17:03', '2024-09-02 14:51:06', '2024-09-09 15:17:07');

SET FOREIGN_KEY_CHECKS = 1;
