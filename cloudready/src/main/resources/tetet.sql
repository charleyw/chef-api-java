# MySQL-Front 5.1  (Build 2.7)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: tetet
# ------------------------------------------------------
# Server version 5.0.41-community-nt

DROP DATABASE IF EXISTS `tetet`;
CREATE DATABASE `tetet` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tetet`;

#
# Source for table instances
#

DROP TABLE IF EXISTS `instances`;
CREATE TABLE `instances` (
  `Id` varchar(50) NOT NULL default '',
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  `owner` int(11) default NULL,
  `status` varchar(255) default NULL,
  `startTime` timestamp NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table instances
#
/*!40000 ALTER TABLE `instances` DISABLE KEYS */;

INSERT INTO `instances` VALUES ('0cac6178-1519-4795-8c6d-d874a12bfc14','PG1','PGSA',0,'spawning','2012-08-12 17:44:04');
INSERT INTO `instances` VALUES ('0cd516c0-3195-4146-8b25-d66036093c96','PG1','PGSA',0,'spawning','2012-08-12 17:43:48');
INSERT INTO `instances` VALUES ('122b9e6a-51b3-440e-9064-63a7c76cbd62','PG1','PGSA',0,'spawning','2012-08-12 17:52:21');
INSERT INTO `instances` VALUES ('132e59e2-ef59-4ede-984e-5a9ff73926d5','PG1','PGSA',0,'spawning','2012-08-12 17:43:11');
INSERT INTO `instances` VALUES ('34bb7730-021a-456b-a3fe-d22381523169','PG1','PGSA',0,'spawning','2012-08-12 17:46:28');
INSERT INTO `instances` VALUES ('55585411-4749-4e8e-b50a-446ba95a9252','PG1','PGSA',0,'spawning','2012-08-12 21:27:15');
INSERT INTO `instances` VALUES ('57ac6cfc-4b82-4dbe-be67-9164b63404db','PG1','PGSA',0,'spawning','2012-08-12 21:26:58');
INSERT INTO `instances` VALUES ('88837249-f831-473d-96e4-75e33ecae6cf','PG1','PGSA',0,'spawning','2012-08-12 17:43:08');
INSERT INTO `instances` VALUES ('b9ed5540-b80b-4e28-892b-9652519562ae','PG1','PGSA',0,'spawning','2012-08-12 21:25:50');
INSERT INTO `instances` VALUES ('e5128104-d0a1-4162-9bc9-6b54f7920d5a','PG1','PGSA',0,'spawning','2012-08-12 21:28:58');
/*!40000 ALTER TABLE `instances` ENABLE KEYS */;

#
# Source for table servers
#

DROP TABLE IF EXISTS `servers`;
CREATE TABLE `servers` (
  `Id` int(11) NOT NULL auto_increment,
  `opstkId` varchar(255) default NULL,
  `instanceId` varchar(50) default NULL,
  `hostname` varchar(255) default NULL,
  `ipaddr` varchar(255) default NULL,
  `status` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table servers
#
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;

INSERT INTO `servers` VALUES (2,'akdflksdkasdfklsda','88837249-f831-473d-96e4-75e33ecae6cf','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (3,'akdflksdkasdfklsda','132e59e2-ef59-4ede-984e-5a9ff73926d5','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (4,'akdflksdkasdfklsda','0cd516c0-3195-4146-8b25-d66036093c96','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (5,'akdflksdkasdfklsda','0cac6178-1519-4795-8c6d-d874a12bfc14','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (6,'akdflksdkasdfklsda','34bb7730-021a-456b-a3fe-d22381523169','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (7,'akdflksdkasdfklsda','122b9e6a-51b3-440e-9064-63a7c76cbd62','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (8,'akdflksdkasdfklsda','b9ed5540-b80b-4e28-892b-9652519562ae','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (9,'akdflksdkasdfklsda','57ac6cfc-4b82-4dbe-be67-9164b63404db','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (10,'akdflksdkasdfklsda','55585411-4749-4e8e-b50a-446ba95a9252','node1005','192.168.100.1','OK');
INSERT INTO `servers` VALUES (11,'akdflksdkasdfklsda','e5128104-d0a1-4162-9bc9-6b54f7920d5a','node1005','192.168.100.1','OK');
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
