/*
Navicat MySQL Data Transfer

Source Server         : test01
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : school_oa_manager_system

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-12-26 16:55:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_depot
-- ----------------------------
DROP TABLE IF EXISTS `file_depot`;
CREATE TABLE `file_depot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `showFileName` varchar(255) NOT NULL COMMENT '上传的文件名',
  `realFileName` varchar(255) NOT NULL COMMENT '服务器中文件名',
  `updateDate` datetime NOT NULL COMMENT '修改日期',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `source` int(11) NOT NULL COMMENT '文件分组大类',
  `ggroup` int(11) DEFAULT NULL COMMENT '具体小组',
  `part` int(11) DEFAULT NULL COMMENT '具体部门',
  `createPerson` varchar(25) NOT NULL COMMENT '文件上传者工号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_depot
-- ----------------------------

-- ----------------------------
-- Table structure for ggroup
-- ----------------------------
DROP TABLE IF EXISTS `ggroup`;
CREATE TABLE `ggroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组id',
  `partId` int(11) NOT NULL COMMENT '部门id',
  `name` varchar(20) NOT NULL COMMENT '分组名称',
  `createPerson` varchar(20) NOT NULL COMMENT '创建人',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `partId` (`partId`),
  CONSTRAINT `ggroup_ibfk_1` FOREIGN KEY (`partId`) REFERENCES `part` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ggroup
-- ----------------------------
INSERT INTO `ggroup` VALUES ('1', '1', '暂无小组', '系统', '2017-10-16 09:38:19');
INSERT INTO `ggroup` VALUES ('2', '1', '初始小组', '初始化', '2017-10-20 22:42:21');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `title` varchar(25) NOT NULL COMMENT '标题',
  `kind` int(11) NOT NULL COMMENT '分类',
  `sendPerson` varchar(25) NOT NULL COMMENT '发送人',
  `sendDate` datetime NOT NULL COMMENT '发送时间',
  `content` text NOT NULL COMMENT '内容',
  `acceptPerson` varchar(25) DEFAULT NULL COMMENT '接受者',
  `acceptPart` int(11) DEFAULT NULL COMMENT '接受部门',
  `acceptGroup` int(11) DEFAULT NULL COMMENT '接受分组',
  PRIMARY KEY (`id`),
  KEY `kind` (`kind`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`kind`) REFERENCES `message_kind` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for message_kind
-- ----------------------------
DROP TABLE IF EXISTS `message_kind`;
CREATE TABLE `message_kind` (
  `id` int(11) NOT NULL COMMENT '类型ID',
  `name` varchar(25) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message_kind
-- ----------------------------
INSERT INTO `message_kind` VALUES ('1', '系统消息');
INSERT INTO `message_kind` VALUES ('2', '公司公告');
INSERT INTO `message_kind` VALUES ('3', '公司消息');
INSERT INTO `message_kind` VALUES ('4', '部门公告');
INSERT INTO `message_kind` VALUES ('5', '部门消息');
INSERT INTO `message_kind` VALUES ('6', '小组消息');
INSERT INTO `message_kind` VALUES ('7', '小组公告');
INSERT INTO `message_kind` VALUES ('8', '个人消息');

-- ----------------------------
-- Table structure for model_option
-- ----------------------------
DROP TABLE IF EXISTS `model_option`;
CREATE TABLE `model_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `modelId` int(11) NOT NULL COMMENT '流程模板ID',
  `name` varchar(25) NOT NULL COMMENT '填选项的名称',
  `oorder` int(11) NOT NULL COMMENT '填选项的顺序，从1开始',
  `must` int(11) NOT NULL COMMENT '是否必填',
  PRIMARY KEY (`id`),
  KEY `modelId` (`modelId`),
  CONSTRAINT `model_option_ibfk_1` FOREIGN KEY (`modelId`) REFERENCES `model_procedure` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of model_option
-- ----------------------------

-- ----------------------------
-- Table structure for model_procedure
-- ----------------------------
DROP TABLE IF EXISTS `model_procedure`;
CREATE TABLE `model_procedure` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '流程名',
  `introduction` text NOT NULL COMMENT '简介',
  `illustrate` text NOT NULL COMMENT '说明',
  `remark` text COMMENT '备注',
  `title` varchar(25) NOT NULL COMMENT '表格题头',
  `projectName` varchar(25) NOT NULL COMMENT '项目名称',
  `enclosure` int(11) NOT NULL,
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `createPerson` varchar(20) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of model_procedure
-- ----------------------------

-- ----------------------------
-- Table structure for model_shen
-- ----------------------------
DROP TABLE IF EXISTS `model_shen`;
CREATE TABLE `model_shen` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `modelId` int(11) NOT NULL COMMENT '流程模板ID',
  `oorder` int(11) NOT NULL COMMENT '过程序号',
  `name` varchar(25) NOT NULL COMMENT '过程标题',
  `part` int(11) NOT NULL COMMENT '审批部门',
  `ggroup` int(11) NOT NULL COMMENT '审批分组',
  `person` varchar(25) NOT NULL COMMENT '审批人',
  PRIMARY KEY (`id`,`modelId`),
  KEY `part` (`part`),
  KEY `ggroup` (`ggroup`),
  KEY `person` (`person`),
  KEY `model_shen_ibfk_1` (`modelId`),
  CONSTRAINT `model_shen_ibfk_1` FOREIGN KEY (`modelId`) REFERENCES `model_procedure` (`id`),
  CONSTRAINT `model_shen_ibfk_2` FOREIGN KEY (`part`) REFERENCES `part` (`id`),
  CONSTRAINT `model_shen_ibfk_3` FOREIGN KEY (`ggroup`) REFERENCES `ggroup` (`id`),
  CONSTRAINT `model_shen_ibfk_4` FOREIGN KEY (`person`) REFERENCES `user_info` (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of model_shen
-- ----------------------------

-- ----------------------------
-- Table structure for part
-- ----------------------------
DROP TABLE IF EXISTS `part`;
CREATE TABLE `part` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `name` varchar(20) NOT NULL COMMENT '部门名称',
  `createPerson` varchar(20) NOT NULL COMMENT '创建人',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of part
-- ----------------------------
INSERT INTO `part` VALUES ('1', '初始部门', '初始化', '2017-10-16 09:37:13');

-- ----------------------------
-- Table structure for procedure_option
-- ----------------------------
DROP TABLE IF EXISTS `procedure_option`;
CREATE TABLE `procedure_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小项ID',
  `procedureId` int(11) NOT NULL COMMENT '流程编号',
  `title` varchar(25) NOT NULL COMMENT '小项名称',
  `content` varchar(25) DEFAULT NULL COMMENT '小项内容',
  `oorder` int(11) NOT NULL COMMENT '小项顺序',
  PRIMARY KEY (`id`),
  KEY `procedureId` (`procedureId`),
  CONSTRAINT `procedure_option_ibfk_1` FOREIGN KEY (`procedureId`) REFERENCES `procedure_submit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procedure_option
-- ----------------------------

-- ----------------------------
-- Table structure for procedure_shen
-- ----------------------------
DROP TABLE IF EXISTS `procedure_shen`;
CREATE TABLE `procedure_shen` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程审批ID',
  `procedureId` int(11) NOT NULL COMMENT '流程编号',
  `name` varchar(255) NOT NULL,
  `userGroup` int(11) NOT NULL COMMENT '审批者所在小组',
  `userPart` int(11) NOT NULL COMMENT '审批者所在部门',
  `userJobId` varchar(25) NOT NULL COMMENT '审批者ID',
  `userName` varchar(25) NOT NULL COMMENT '审批者姓名',
  `content` varchar(25) DEFAULT NULL COMMENT '审批内容',
  `time` datetime DEFAULT NULL COMMENT '审批时间',
  `oorder` int(11) NOT NULL COMMENT '审批顺序',
  `pass` int(11) DEFAULT NULL COMMENT '是否通过',
  `work` int(11) NOT NULL COMMENT '是否已经审批了',
  PRIMARY KEY (`id`),
  KEY `procedureId` (`procedureId`),
  CONSTRAINT `procedure_shen_ibfk_1` FOREIGN KEY (`procedureId`) REFERENCES `procedure_submit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procedure_shen
-- ----------------------------

-- ----------------------------
-- Table structure for procedure_submit
-- ----------------------------
DROP TABLE IF EXISTS `procedure_submit`;
CREATE TABLE `procedure_submit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程编号/ID',
  `name` varchar(25) NOT NULL COMMENT '流程名称',
  `projectName` varchar(25) NOT NULL COMMENT '项目名称',
  `projectNameTitle` varchar(25) NOT NULL COMMENT '项目名称标题',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `createPerson` varchar(25) NOT NULL COMMENT '创建人ID',
  `status` int(11) NOT NULL COMMENT '状态',
  `partName` varchar(25) NOT NULL COMMENT '创建人现所属部门',
  `groupName` varchar(25) NOT NULL COMMENT '创建人现所属小组',
  `showFileName` varchar(255) DEFAULT NULL COMMENT '上传的文件名',
  `fileName` varchar(255) DEFAULT NULL COMMENT '服务器上的文件名',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procedure_submit
-- ----------------------------

-- ----------------------------
-- Table structure for remind
-- ----------------------------
DROP TABLE IF EXISTS `remind`;
CREATE TABLE `remind` (
  `id` int(11) NOT NULL COMMENT '消息id/流程id',
  `remindId` varchar(20) NOT NULL COMMENT '接受者id',
  `isMsg` int(11) NOT NULL COMMENT '是不是消息',
  PRIMARY KEY (`id`,`remindId`,`isMsg`),
  KEY `remindId` (`remindId`),
  CONSTRAINT `remind_ibfk_1` FOREIGN KEY (`remindId`) REFERENCES `user_info` (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of remind
-- ----------------------------

-- ----------------------------
-- Table structure for user_frozen
-- ----------------------------
DROP TABLE IF EXISTS `user_frozen`;
CREATE TABLE `user_frozen` (
  `jobId` varchar(255) NOT NULL COMMENT '冻结用户的jobId',
  `recoveryDateLong` bigint(20) NOT NULL COMMENT '解冻时间',
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_frozen
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `jobId` varchar(25) NOT NULL COMMENT '工号',
  `cardId` varchar(18) NOT NULL COMMENT '身份证号',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` int(11) NOT NULL COMMENT '性别',
  `part` int(11) NOT NULL COMMENT '部门',
  `ggroup` int(11) NOT NULL COMMENT '分组',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `addr` varchar(20) DEFAULT NULL COMMENT '地址',
  `kind` int(11) NOT NULL COMMENT '用户类型',
  `joinTime` datetime NOT NULL,
  `password` varchar(32) NOT NULL COMMENT '密码',
  `status` int(11) NOT NULL COMMENT '用户状态',
  `errorTimes` int(11) NOT NULL,
  `post` varchar(255) DEFAULT NULL COMMENT '职务',
  PRIMARY KEY (`jobId`),
  KEY `part` (`part`),
  KEY `kind` (`kind`),
  KEY `ggroup` (`ggroup`),
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`part`) REFERENCES `part` (`id`),
  CONSTRAINT `user_info_ibfk_2` FOREIGN KEY (`ggroup`) REFERENCES `ggroup` (`id`),
  CONSTRAINT `user_info_ibfk_3` FOREIGN KEY (`kind`) REFERENCES `user_kind` (`id`),
  CONSTRAINT `user_info_ibfk_4` FOREIGN KEY (`ggroup`) REFERENCES `ggroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('0000', '0000', '系统', '0', '1', '1', null, null, null, '0', '2000-01-01 00:00:00', '0', '2', '0', null);
INSERT INTO `user_info` VALUES ('1000', '211103', '初始管理员', '0', '1', '2', null, null, null, '0', '2000-01-01 00:00:00', '839688824ee88492e71fa88f82987456', '1', '0', null);

-- ----------------------------
-- Table structure for user_kind
-- ----------------------------
DROP TABLE IF EXISTS `user_kind`;
CREATE TABLE `user_kind` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '用户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_kind
-- ----------------------------
INSERT INTO `user_kind` VALUES ('0', '网站管理员');
INSERT INTO `user_kind` VALUES ('1', '部门管理员');
INSERT INTO `user_kind` VALUES ('2', '小组管理员');
INSERT INTO `user_kind` VALUES ('3', '成员');
