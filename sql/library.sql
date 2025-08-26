/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 26/08/2025 17:17:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_category
-- ----------------------------
DROP TABLE IF EXISTS `book_category`;
CREATE TABLE `book_category`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '书籍类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_category
-- ----------------------------
INSERT INTO `book_category` VALUES (1, '文学类');
INSERT INTO `book_category` VALUES (2, '社会学类');
INSERT INTO `book_category` VALUES (3, '科普类');
INSERT INTO `book_category` VALUES (4, '历史类');
INSERT INTO `book_category` VALUES (5, '哲学类');
INSERT INTO `book_category` VALUES (6, '艺术类');
INSERT INTO `book_category` VALUES (11, '体育类');

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书名',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  `categoryId` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '分类',
  `count` int(0) NOT NULL DEFAULT 1 COMMENT '剩余数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '书籍表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (6, '《红楼梦》', '曹雪芹', 1, 78);
INSERT INTO `books` VALUES (7, '《百年孤独》', '加西亚·马尔克斯', 1, 63);
INSERT INTO `books` VALUES (8, '《乡土中国》', '费孝通', 2, 56);
INSERT INTO `books` VALUES (9, '《乌合之众》', '古斯塔夫·勒庞', 1, 46);
INSERT INTO `books` VALUES (10, '《时间简史》', '史蒂芬·霍金', 3, 15);
INSERT INTO `books` VALUES (11, '《人类简史》', '尤瓦尔·赫拉利', 4, 33);
INSERT INTO `books` VALUES (12, '《理想国》', '柏拉图', 5, 22);
INSERT INTO `books` VALUES (13, '《西方哲学史》', '伯特兰·罗素', 5, 19);
INSERT INTO `books` VALUES (14, '《艺术的故事》', 'E.H.贡布里希', 6, 74);
INSERT INTO `books` VALUES (15, '《大黄的一天》', 'da.huang', 5, -1);

-- ----------------------------
-- Table structure for borrow_requests
-- ----------------------------
DROP TABLE IF EXISTS `borrow_requests`;
CREATE TABLE `borrow_requests`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书籍名',
  `requestTime` datetime(0) NOT NULL COMMENT '请求时间',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '（0 审核中,1 同意, 2 拒绝）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '借阅表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow_requests
-- ----------------------------
INSERT INTO `borrow_requests` VALUES (4, 'user1', '《乡土中国》', '2025-02-04 22:16:09', '', 1);
INSERT INTO `borrow_requests` VALUES (5, 'admin', '《红楼梦》', '2025-02-05 14:45:20', '', 2);
INSERT INTO `borrow_requests` VALUES (6, 'user1', '《红楼梦》', '2025-02-09 19:46:20', '', 1);
INSERT INTO `borrow_requests` VALUES (7, 'user5', '《红楼梦》', '2025-08-25 14:37:17', '', 2);
INSERT INTO `borrow_requests` VALUES (8, 'user5', '《百年孤独》', '2025-08-25 14:37:30', '', 2);
INSERT INTO `borrow_requests` VALUES (9, 'user5', '《乌合之众》', '2025-08-25 14:37:39', '', 1);

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `permissions` tinyint(0) NOT NULL COMMENT '权限控制(1 用户，0 管理员,6 超管)',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `avatarUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES (1, 'admin', '123456', '2025-01-06 18:47:58', 6, '1514055091@qq.com', 'http://localhost:8080/images/eae14579-adb6-4c1a-aac2-923ee96edf1c.jpg');
INSERT INTO `login` VALUES (7, 'user1', '123456', '2025-01-07 20:01:06', 0, '123@qq.com', 'http://localhost:8080/images/5d77374e-ed39-4a3a-8444-ab877f019cb3.jpg');
INSERT INTO `login` VALUES (10, 'user3', '123456', '2025-01-07 20:04:13', 0, '123@qq.com', NULL);
INSERT INTO `login` VALUES (14, 'user5', 'e10adc3949ba59abbe56e057f20f883e', '2025-01-22 20:41:58', 1, '1514055091@qq.com', NULL);
INSERT INTO `login` VALUES (15, 'rrrrrrr', 'b3e013fe0d50b1cc22502651370a9a31', '2025-01-22 20:44:07', 1, '123@qq.com', NULL);
INSERT INTO `login` VALUES (16, 'eeee', '32bf0e6fcff51e53bd74e70ba1d622b2', '2025-01-22 20:44:50', 1, '1514055091@qq.com', NULL);
INSERT INTO `login` VALUES (17, 'rrrrr', '66ba13e5474d241e80f7a12ed434645d', '2025-01-22 20:45:22', 1, '156@cc.com', NULL);
INSERT INTO `login` VALUES (18, '哈哈哈', 'c4ded2b85cc5be82fa1d2464eba9a7d3', '2025-01-22 22:15:41', 1, '33@123.com', NULL);
INSERT INTO `login` VALUES (19, 'dahuang', 'e10adc3949ba59abbe56e057f20f883e', '2025-01-23 17:41:41', 1, '1514055091@qq.com', NULL);
INSERT INTO `login` VALUES (20, 'adhuangtest2', 'e10adc3949ba59abbe56e057f20f883e', '2025-01-24 16:47:28', 1, '1514055091@qq.com', NULL);
INSERT INTO `login` VALUES (21, 'dahuangtest3', 'e10adc3949ba59abbe56e057f20f883e', '2025-01-24 16:48:21', 1, '123@qq.com', NULL);
INSERT INTO `login` VALUES (22, 'null', '123456', '2025-01-24 16:48:21', 0, '1514055091@qq.com', 'http://localhost:8080/images/584da634-fb9a-4060-912f-71e2f7c70251.png');

-- ----------------------------
-- Table structure for verification_email
-- ----------------------------
DROP TABLE IF EXISTS `verification_email`;
CREATE TABLE `verification_email`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '验证码',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '验证码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of verification_email
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
