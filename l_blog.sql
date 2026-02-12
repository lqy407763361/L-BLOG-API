/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : l_blog_bak

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 12/02/2026 13:40:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for l_about
-- ----------------------------
DROP TABLE IF EXISTS `l_about`;
CREATE TABLE `l_about`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，1=启用，2=禁用',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_about
-- ----------------------------
INSERT INTO `l_about` VALUES (1, '<p>关于我</p><p>这是关于我的介绍</p>', 1, 1763602181);

-- ----------------------------
-- Table structure for l_admin
-- ----------------------------
DROP TABLE IF EXISTS `l_admin`;
CREATE TABLE `l_admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL COMMENT '管理员群组ID',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，1=启用，2=禁用',
  `add_time` int NULL DEFAULT NULL COMMENT '创建时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_admin
-- ----------------------------

-- ----------------------------
-- Table structure for l_admin_group
-- ----------------------------
DROP TABLE IF EXISTS `l_admin_group`;
CREATE TABLE `l_admin_group`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '群组名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '简介',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，1=启用，2=禁用',
  `sort_order` int NULL DEFAULT NULL COMMENT '排序',
  `view_power` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '查看权限',
  `edit_power` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '编辑权限',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_admin_group
-- ----------------------------

-- ----------------------------
-- Table structure for l_admin_login_record
-- ----------------------------
DROP TABLE IF EXISTS `l_admin_login_record`;
CREATE TABLE `l_admin_login_record`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin_id` int NOT NULL COMMENT '管理员表ID',
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP',
  `login_time` int NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_admin_login_record
-- ----------------------------

-- ----------------------------
-- Table structure for l_admin_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `l_admin_refresh_token`;
CREATE TABLE `l_admin_refresh_token`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin_id` int NULL DEFAULT NULL COMMENT '管理员表ID',
  `refresh_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '长期token',
  `is_revoked` int NOT NULL DEFAULT 0 COMMENT '是否废弃，0=默认，1=废弃',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_admin_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for l_article
-- ----------------------------
DROP TABLE IF EXISTS `l_article`;
CREATE TABLE `l_article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL COMMENT '文章分类ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `read_count` int NULL DEFAULT NULL COMMENT '阅读量',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，1=启用，2=禁用',
  `sort_order` int NULL DEFAULT NULL COMMENT '排序',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_article
-- ----------------------------

-- ----------------------------
-- Table structure for l_article_category
-- ----------------------------
DROP TABLE IF EXISTS `l_article_category`;
CREATE TABLE `l_article_category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章分类名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '简介',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，1=启用，2=禁用',
  `sort_order` int NULL DEFAULT NULL COMMENT '排序',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_article_category
-- ----------------------------

-- ----------------------------
-- Table structure for l_message
-- ----------------------------
DROP TABLE IF EXISTS `l_message`;
CREATE TABLE `l_message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '发送人ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `is_read` int NOT NULL DEFAULT 2 COMMENT '是否阅读，1=已读，2=未读',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_message
-- ----------------------------

-- ----------------------------
-- Table structure for l_site_config
-- ----------------------------
DROP TABLE IF EXISTS `l_site_config`;
CREATE TABLE `l_site_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `meta_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Meta标题',
  `meta_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'Meta描述',
  `meta_keywords` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'Meta关键词',
  `site_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网页标题',
  `site_list_limit` int NULL DEFAULT NULL COMMENT '网站列表页展示数量',
  `admin_list_limit` int NULL DEFAULT NULL COMMENT '后台列表页展示数量',
  `logo_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'LOGO路径',
  `logo_image_full_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'LOGO绝对路径',
  `site_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '网站底部配置信息',
  `system_maintenance` int NULL DEFAULT NULL COMMENT '系统维护模式，0关闭，1=开启',
  `site_login_max_number` int NULL DEFAULT NULL COMMENT '网站最大登录次数',
  `admin_login_max_number` int NULL DEFAULT NULL COMMENT '后台最大登录次数',
  `site_session_expire` int NULL DEFAULT NULL COMMENT '网站登录会话周期',
  `admin_session_expire` int NULL DEFAULT NULL COMMENT '后台登录会话周期',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_site_config
-- ----------------------------
INSERT INTO `l_site_config` VALUES (1, 'L-BLOG', 'L-BLOG', 'L-BLOG', 'L-BLOG', 10, 10, '', NULL, '<p>L-BLOG后台管理系统</p>', 1, 5, 5, 10, 11, 1764838397);

-- ----------------------------
-- Table structure for l_user
-- ----------------------------
DROP TABLE IF EXISTS `l_user`;
CREATE TABLE `l_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，1=启用，2=禁用',
  `register_type` int NULL DEFAULT 1 COMMENT '注册方式，1=网页注册',
  `register_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '注册地IP',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_user
-- ----------------------------

-- ----------------------------
-- Table structure for l_user_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `l_user_refresh_token`;
CREATE TABLE `l_user_refresh_token`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户表ID',
  `refresh_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '长期token',
  `is_revoked` int NOT NULL DEFAULT 0 COMMENT '是否废弃，0=默认，1=废弃',
  `add_time` int NULL DEFAULT NULL COMMENT '添加时间',
  `edit_time` int NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_user_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for l_user_visit_record
-- ----------------------------
DROP TABLE IF EXISTS `l_user_visit_record`;
CREATE TABLE `l_user_visit_record`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户表ID',
  `visit_module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问模块',
  `visit_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问IP',
  `visit_time` int NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of l_user_visit_record
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
