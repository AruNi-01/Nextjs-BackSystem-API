/*
 Navicat Premium Data Transfer

 Source Server         : localmysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : nextjs_antd

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 18/11/2023 19:15:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `member_count` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES (1, 'Development', 1);
INSERT INTO `team` VALUES (2, 'Design', 2);
INSERT INTO `team` VALUES (3, 'Sell', 1);
INSERT INTO `team` VALUES (4, 'Finance', 1);
INSERT INTO `team` VALUES (5, 'Legal', 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `gender` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `team_id` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'tom', 18, 'male', '18888888888', NULL, '2023-11-16 12:15:11');
INSERT INTO `user` VALUES (2, 'july', NULL, 'female', '1777777777', NULL, '2023-11-16 12:52:26');
INSERT INTO `user` VALUES (3, 'jack', 12, 'male', '1744777777', NULL, '2023-11-16 12:54:28');
INSERT INTO `user` VALUES (4, 'jack2', NULL, 'male', '1744777777', NULL, '2023-11-16 12:56:07');
INSERT INTO `user` VALUES (5, 'jack2', NULL, 'male', '1744777777', NULL, '2023-11-16 12:58:07');
INSERT INTO `user` VALUES (6, 'AruNi_Lu', 22, 'male', '16888888888', 2, '2023-11-16 15:53:21');
INSERT INTO `user` VALUES (22, 'AarynLu', 13, 'male', NULL, 1, '2023-11-18 11:58:47');
INSERT INTO `user` VALUES (25, 'AarynLu', NULL, 'male', NULL, 5, '2023-11-18 14:57:05');
INSERT INTO `user` VALUES (29, 'AarynLu', 123, 'male', '18888888888', 4, '2023-11-18 16:13:44');
INSERT INTO `user` VALUES (30, 'AruNi', 22, 'male', NULL, 5, '2023-11-18 16:14:51');
INSERT INTO `user` VALUES (31, 'master2', NULL, 'female', NULL, 3, '2023-11-18 16:15:05');
INSERT INTO `user` VALUES (32, 'master3', 23, 'male', NULL, 2, '2023-11-18 16:15:18');

SET FOREIGN_KEY_CHECKS = 1;
