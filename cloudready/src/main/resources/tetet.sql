/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : tetet

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2012-08-13 15:26:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `instances`
-- ----------------------------
DROP TABLE IF EXISTS `instances`;
CREATE TABLE `instances` (
  `Id` varchar(50) NOT NULL DEFAULT '',
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of instances
-- ----------------------------
INSERT INTO `instances` VALUES ('0cac6178-1519-4795-8c6d-d874a12bfc14', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 17:44:04');
INSERT INTO `instances` VALUES ('0cd516c0-3195-4146-8b25-d66036093c96', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 17:43:48');
INSERT INTO `instances` VALUES ('122b9e6a-51b3-440e-9064-63a7c76cbd62', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 17:52:21');
INSERT INTO `instances` VALUES ('132e59e2-ef59-4ede-984e-5a9ff73926d5', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 17:43:11');
INSERT INTO `instances` VALUES ('14197135-387c-4897-9860-8d4bc5d1a504', 'PG1', 'PGSA', '0', 'spawning', '2012-08-13 14:30:09');
INSERT INTO `instances` VALUES ('34bb7730-021a-456b-a3fe-d22381523169', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 17:46:28');
INSERT INTO `instances` VALUES ('55585411-4749-4e8e-b50a-446ba95a9252', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 21:27:15');
INSERT INTO `instances` VALUES ('57ac6cfc-4b82-4dbe-be67-9164b63404db', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 21:26:58');
INSERT INTO `instances` VALUES ('88837249-f831-473d-96e4-75e33ecae6cf', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 17:43:08');
INSERT INTO `instances` VALUES ('b9ed5540-b80b-4e28-892b-9652519562ae', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 21:25:50');
INSERT INTO `instances` VALUES ('e5128104-d0a1-4162-9bc9-6b54f7920d5a', 'PG1', 'PGSA', '0', 'spawning', '2012-08-12 21:28:58');

-- ----------------------------
-- Table structure for `logs`
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `loggerId` varchar(255) DEFAULT NULL,
  `logTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES ('22', 'test1', '2012-08-13 13:55:31', 'test');
INSERT INTO `logs` VALUES ('23', 'test2', '2012-08-13 14:22:52', 'test');
INSERT INTO `logs` VALUES ('24', 'test0', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('25', 'test1', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('26', 'test2', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('27', 'test3', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('28', 'test4', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('29', 'test5', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('30', 'test6', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('31', 'test7', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('32', 'test8', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('33', 'test9', '2012-08-13 14:30:09', 'testsatset');
INSERT INTO `logs` VALUES ('34', 'test2', '2012-08-13 15:14:01', 'test');

-- ----------------------------
-- Table structure for `servers`
-- ----------------------------
DROP TABLE IF EXISTS `servers`;
CREATE TABLE `servers` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `opstkId` varchar(255) DEFAULT NULL,
  `instanceId` varchar(50) DEFAULT NULL,
  `hostname` varchar(255) DEFAULT NULL,
  `ipaddr` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of servers
-- ----------------------------
INSERT INTO `servers` VALUES ('2', 'akdflksdkasdfklsda', '88837249-f831-473d-96e4-75e33ecae6cf', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('3', 'akdflksdkasdfklsda', '132e59e2-ef59-4ede-984e-5a9ff73926d5', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('4', 'akdflksdkasdfklsda', '0cd516c0-3195-4146-8b25-d66036093c96', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('5', 'akdflksdkasdfklsda', '0cac6178-1519-4795-8c6d-d874a12bfc14', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('6', 'akdflksdkasdfklsda', '34bb7730-021a-456b-a3fe-d22381523169', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('7', 'akdflksdkasdfklsda', '122b9e6a-51b3-440e-9064-63a7c76cbd62', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('8', 'akdflksdkasdfklsda', 'b9ed5540-b80b-4e28-892b-9652519562ae', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('9', 'akdflksdkasdfklsda', '57ac6cfc-4b82-4dbe-be67-9164b63404db', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('10', 'akdflksdkasdfklsda', '55585411-4749-4e8e-b50a-446ba95a9252', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('11', 'akdflksdkasdfklsda', 'e5128104-d0a1-4162-9bc9-6b54f7920d5a', 'node1005', '192.168.100.1', 'OK');
INSERT INTO `servers` VALUES ('12', 'akdflksdkasdfklsda', '14197135-387c-4897-9860-8d4bc5d1a504', 'node1005', '192.168.100.1', 'OK');
