/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : tourism

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-05-14 14:07:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `poi_id` int(10) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `content` text,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '22', '3123', '防守打法第三', '1');
INSERT INTO `comment` VALUES ('2', '22', '31232', '123213', '1');
INSERT INTO `comment` VALUES ('3', '23', '2016-05-13 15:43:39', '5896', '1');
INSERT INTO `comment` VALUES ('4', '22', '2016-05-14 13:27:05', '6^&7755', '1');
INSERT INTO `comment` VALUES ('5', '26', '2016-05-14 14:00:52', '89987', '1');
INSERT INTO `comment` VALUES ('6', '26', '2016-05-14 14:02:34', '5667', '1');

-- ----------------------------
-- Table structure for `jingdian`
-- ----------------------------
DROP TABLE IF EXISTS `jingdian`;
CREATE TABLE `jingdian` (
  `poi_id` int(10) NOT NULL,
  `money` double(10,2) DEFAULT NULL,
  `img` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`poi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jingdian
-- ----------------------------
INSERT INTO `jingdian` VALUES ('16', '50.00', '0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('17', '50.00', '0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('18', '50.00', '0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('19', '50.00', '0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('20', '50.00', '0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('21', '50.00', '0.jpg;0.jpg;0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('22', '50.00', '0.jpg;0.jpg;0.jpg;0.jpg;welcome.png');
INSERT INTO `jingdian` VALUES ('23', '62.00', '0.jpg;0.jpg;0.jpg');
INSERT INTO `jingdian` VALUES ('24', '50.00', '0.jpg;0.jpg');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `jingdian_name` varchar(20) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `poi_id` int(10) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '景点：鲁东大学', '2016-05-13 12:00:52', '22', '1', '山东省烟台市芝罘区世回尧街道鲁东大学(南区)', 'finish');
INSERT INTO `orders` VALUES ('2', '景点：鲁东大学', '2016-05-13 12:06:53', '22', '1', '山东省烟台市芝罘区世回尧街道鲁东大学(南区)', 'finish');
INSERT INTO `orders` VALUES ('3', '景点：鲁东大学', '2016-05-13 12:07:35', '22', '1', '山东省烟台市芝罘区世回尧街道鲁东大学(南区)', 'finish');
INSERT INTO `orders` VALUES ('4', '景点：烟台勃朗鲁东滑雪场', '2016-05-13 14:53:40', '23', '1', '山东省烟台市芝罘区世回尧街道烟台勃朗鲁东滑雪场', 'finish');
INSERT INTO `orders` VALUES ('5', '景点：鲁东大学', '2016-05-14 13:22:07', '22', '1', '山东省烟台市芝罘区世回尧街道鲁东大学(南区)', 'finish');
INSERT INTO `orders` VALUES ('6', '景点：鲁东大学', '2016-05-14 13:26:28', '22', '1', '山东省烟台市芝罘区世回尧街道鲁东大学(南区)', 'finish');
INSERT INTO `orders` VALUES ('7', '景点：烟台勃朗鲁东滑雪场', '2016-05-14 13:53:19', '23', '1', '山东省烟台市芝罘区世回尧街道烟台勃朗鲁东滑雪场', 'finish');
INSERT INTO `orders` VALUES ('8', '景点：烟台农商银行(惠安支行)', '2016-05-14 14:00:17', '26', '1', '山东省烟台市芝罘区世回尧街道通世华府', 'finish');
INSERT INTO `orders` VALUES ('9', '景点：烟台农商银行(惠安支行)', '2016-05-14 14:02:21', '26', '1', '山东省烟台市芝罘区世回尧街道通世华府', 'finish');
INSERT INTO `orders` VALUES ('10', '景点：烟台勃朗鲁东滑雪场', '2016-05-14 14:02:24', '23', '1', '山东省烟台市芝罘区世回尧街道烟台勃朗鲁东滑雪场', 'order');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `money` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '闫聪', '1', '1', '28.00');
INSERT INTO `users` VALUES ('2', '2', '2', '2', '100.00');
