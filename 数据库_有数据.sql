/*
Navicat MySQL Data Transfer

Source Server         : test01
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : school_oa_manager_system

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-12-26 16:44:58
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
INSERT INTO `file_depot` VALUES ('1', '想念熊1.gif', '1000_1512651730561.gif', '2017-12-07 21:02:10', '94321', '1', '0', '0', '1000');
INSERT INTO `file_depot` VALUES ('2', '想念熊2.gif', '1000_1512651745522.gif', '2017-12-07 21:02:25', '72433', '0', '0', '18', '1000');
INSERT INTO `file_depot` VALUES ('4', '想念熊6.gif', '1000_1512651767059.gif', '2017-12-07 21:02:47', '99718', '2', '32', '18', '1000');
INSERT INTO `file_depot` VALUES ('5', '想念熊5.gif', '1000_1512651774655.gif', '2017-12-07 21:02:54', '24535', '2', '47', '22', '1000');
INSERT INTO `file_depot` VALUES ('6', '想念熊6.gif', '1000_1512716444832.gif', '2017-12-08 15:00:44', '99718', '1', '0', '0', '1000');

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
INSERT INTO `ggroup` VALUES ('25', '18', '暂无小组', '系统', '2017-11-20 11:23:54');
INSERT INTO `ggroup` VALUES ('26', '19', '暂无小组', '系统', '2017-11-20 11:24:20');
INSERT INTO `ggroup` VALUES ('27', '20', '暂无小组', '系统', '2017-11-20 11:24:57');
INSERT INTO `ggroup` VALUES ('28', '21', '暂无小组', '系统', '2017-11-20 11:25:41');
INSERT INTO `ggroup` VALUES ('29', '22', '暂无小组', '系统', '2017-11-20 11:25:48');
INSERT INTO `ggroup` VALUES ('30', '23', '暂无小组', '系统', '2017-11-20 11:26:22');
INSERT INTO `ggroup` VALUES ('31', '18', '第一小组', '初始管理员', '2017-11-20 11:27:50');
INSERT INTO `ggroup` VALUES ('32', '18', '第二小组', '初始管理员', '2017-11-20 11:27:50');
INSERT INTO `ggroup` VALUES ('33', '18', '第三小组', '初始管理员', '2017-11-20 11:27:50');
INSERT INTO `ggroup` VALUES ('34', '18', '第四小组', '初始管理员', '2017-11-20 11:27:50');
INSERT INTO `ggroup` VALUES ('35', '18', '第五小组', '初始管理员', '2017-11-20 11:27:50');
INSERT INTO `ggroup` VALUES ('36', '19', '第一组', '初始管理员', '2017-11-20 11:29:07');
INSERT INTO `ggroup` VALUES ('37', '19', '第二组', '初始管理员', '2017-11-20 11:29:07');
INSERT INTO `ggroup` VALUES ('38', '19', '第三组', '初始管理员', '2017-11-20 11:29:07');
INSERT INTO `ggroup` VALUES ('39', '20', '第一组', '初始管理员', '2017-11-20 11:29:47');
INSERT INTO `ggroup` VALUES ('40', '21', '第一组', '初始管理员', '2017-11-20 11:29:54');
INSERT INTO `ggroup` VALUES ('41', '22', '第一组', '初始管理员', '2017-11-20 11:29:58');
INSERT INTO `ggroup` VALUES ('42', '23', '第一组', '初始管理员', '2017-11-20 11:30:02');
INSERT INTO `ggroup` VALUES ('43', '20', '第二组', '初始管理员', '2017-11-20 11:29:47');
INSERT INTO `ggroup` VALUES ('44', '20', '第三组', '初始管理员', '2017-11-20 11:29:47');
INSERT INTO `ggroup` VALUES ('45', '21', '第二组', '初始管理员', '2017-11-20 11:29:54');
INSERT INTO `ggroup` VALUES ('46', '21', '第三组', '初始管理员', '2017-11-20 11:29:54');
INSERT INTO `ggroup` VALUES ('47', '22', '第二组', '初始管理员', '2017-11-20 11:29:58');
INSERT INTO `ggroup` VALUES ('48', '22', '第三组', '初始管理员', '2017-11-20 11:29:58');
INSERT INTO `ggroup` VALUES ('49', '23', '第二组', '初始管理员', '2017-11-20 11:30:02');
INSERT INTO `ggroup` VALUES ('50', '23', '第三组', '初始管理员', '2017-11-20 11:30:02');

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
INSERT INTO `message` VALUES ('33', '【系统】您有一个需要审批的流程', '1', '0000', '2017-11-20 21:59:08', '详细情况请查看“需要审批的流程”【流程号：“10”】', '1041', null, null);
INSERT INTO `message` VALUES ('34', '【系统】您的一个流程已经审核完成，请查看结果。', '1', '0000', '2017-11-20 22:01:40', '审核已通过！\\(^o^)/~（详细情况请查看“我的流程”）', '1002', null, null);
INSERT INTO `message` VALUES ('35', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-07 21:23:05', '详细情况请查看“需要审批的流程”【流程号：“11”】', '1001', null, null);
INSERT INTO `message` VALUES ('36', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-07 21:23:40', '详细情况请查看“需要审批的流程”', '1014', null, null);
INSERT INTO `message` VALUES ('37', '公司OA系统上线', '2', '1000', '2017-12-07 21:24:51', '欢迎各位使用本系统。', null, null, null);
INSERT INTO `message` VALUES ('38', '请假流程使用', '4', '1000', '2017-12-07 21:26:02', '请至少提前3天请假，使用初始部门请假流程', null, '1', null);
INSERT INTO `message` VALUES ('39', '人员分配说明', '4', '1000', '2017-12-07 21:27:06', '配合公司的纸质文件分配人员。', null, '1', '2');
INSERT INTO `message` VALUES ('40', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-08 14:51:03', '详细情况请查看“需要审批的流程”【流程号：“12”】', '1000', null, null);
INSERT INTO `message` VALUES ('41', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-08 14:57:02', '详细情况请查看“需要审批的流程”【流程号：“13”】', '1000', null, null);
INSERT INTO `message` VALUES ('42', '【系统】您的一个流程已经审核完成，请查看结果。', '1', '0000', '2017-12-08 14:59:59', '审核未通过。/(ㄒoㄒ)/~~（详细情况请查看“我的流程”）', '1000', null, null);
INSERT INTO `message` VALUES ('43', '你明天有时间么', '8', '1000', '2017-12-08 15:02:06', '你明天有时间和我一起去商场么？', '1013', null, null);
INSERT INTO `message` VALUES ('44', '放假通知', '2', '1000', '2017-12-08 15:02:44', '明天休息一天（即2017年12月9日）', null, null, null);
INSERT INTO `message` VALUES ('45', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-08 15:09:35', '详细情况请查看“需要审批的流程”', '1035', null, null);
INSERT INTO `message` VALUES ('46', '【系统】您的一个流程已经审核完成，请查看结果。', '1', '0000', '2017-12-08 15:10:19', '审核已通过！\\(^o^)/~（详细情况请查看“我的流程”）', '1023', null, null);
INSERT INTO `message` VALUES ('47', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-22 17:05:33', '详细情况请查看“需要审批的流程”【流程号：“14”】', '1002', null, null);
INSERT INTO `message` VALUES ('48', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-22 17:06:07', '详细情况请查看“需要审批的流程”【流程号：“15”】', '1001', null, null);
INSERT INTO `message` VALUES ('49', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-22 17:06:32', '详细情况请查看“需要审批的流程”【流程号：“16”】', '1002', null, null);
INSERT INTO `message` VALUES ('50', '【系统】您有一个需要审批的流程', '1', '0000', '2017-12-22 17:06:57', '详细情况请查看“需要审批的流程”【流程号：“17”】', '1002', null, null);
INSERT INTO `message` VALUES ('51', '【系统】您的一个流程已经审核完成，请查看结果。', '1', '0000', '2017-12-22 17:08:02', '审核已通过！\\(^o^)/~（详细情况请查看“我的流程”）', '1000', null, null);
INSERT INTO `message` VALUES ('52', '【系统】您的一个流程已经审核完成，请查看结果。', '1', '0000', '2017-12-22 17:11:15', '审核未通过。/(ㄒoㄒ)/~~（详细情况请查看“我的流程”）', '1000', null, null);
INSERT INTO `message` VALUES ('53', '12312', '1', '0000', '2017-12-22 17:42:58', '三生三世', '1013', null, null);
INSERT INTO `message` VALUES ('54', 'sdf', '3', '0000', '2017-12-22 17:46:21', 'asd ', null, null, null);

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
INSERT INTO `model_option` VALUES ('27', '13', '开始时间', '1', '1');
INSERT INTO `model_option` VALUES ('28', '13', '结束时间', '2', '1');
INSERT INTO `model_option` VALUES ('29', '13', '原因', '3', '1');
INSERT INTO `model_option` VALUES ('30', '11', '请假开始时间', '1', '1');
INSERT INTO `model_option` VALUES ('31', '11', '请假结束时间', '2', '1');
INSERT INTO `model_option` VALUES ('32', '11', '请假原因', '3', '1');
INSERT INTO `model_option` VALUES ('33', '14', '请假原因', '1', '1');
INSERT INTO `model_option` VALUES ('34', '14', '请假开始时间', '2', '1');
INSERT INTO `model_option` VALUES ('35', '14', '请假结束时间', '3', '1');
INSERT INTO `model_option` VALUES ('36', '15', '活动主题', '1', '1');
INSERT INTO `model_option` VALUES ('37', '15', '活动内容', '2', '1');
INSERT INTO `model_option` VALUES ('38', '15', '活动开始时间', '3', '1');
INSERT INTO `model_option` VALUES ('39', '15', '活动结束时间', '4', '1');
INSERT INTO `model_option` VALUES ('40', '15', '活动地点', '5', '1');
INSERT INTO `model_option` VALUES ('41', '15', '活动人员', '6', '1');
INSERT INTO `model_option` VALUES ('42', '15', '活动经费', '7', '1');
INSERT INTO `model_option` VALUES ('43', '15', '活动物品', '8', '0');
INSERT INTO `model_option` VALUES ('44', '15', '活动主办人', '9', '1');

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
INSERT INTO `model_procedure` VALUES ('11', '财务部请假', '财务部门请假通用。', '请假时使用，至少提前1天请假。', '无', '财务部请假表', '部门请假申请', '2', '2017-11-20 21:55:26', '1000');
INSERT INTO `model_procedure` VALUES ('13', '后勤部请假', '后勤部请假通用', '后勤部请假使用，不分小组。', '日期写到日', '后勤部请假申请', '请假天数', '0', '2017-11-21 09:33:39', '1000');
INSERT INTO `model_procedure` VALUES ('14', '公司请假', '用于公司请假使用', '公司请假使用', '无', '公司总请假', '是否请假', '0', '2017-12-07 21:06:48', '1000');
INSERT INTO `model_procedure` VALUES ('15', '小型活动申请', '公司内小活动的申请', '时间格式为：年月日时分，例：2017年12月7日21:15:00。', '无', '公司内活动申请', '活动名称', '2', '2017-12-07 21:15:47', '1000');

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
INSERT INTO `model_shen` VALUES ('28', '13', '1', '意见', '19', '36', '1042');
INSERT INTO `model_shen` VALUES ('29', '11', '1', '请假意见', '18', '31', '1041');
INSERT INTO `model_shen` VALUES ('30', '14', '1', '总审批人意见', '1', '2', '1000');
INSERT INTO `model_shen` VALUES ('31', '15', '1', '意见一', '18', '31', '1001');
INSERT INTO `model_shen` VALUES ('32', '15', '2', '意见二', '19', '37', '1014');
INSERT INTO `model_shen` VALUES ('33', '15', '3', '意见三', '23', '42', '1035');

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
INSERT INTO `part` VALUES ('18', '财务部', '初始管理员', '2017-11-20 11:23:54');
INSERT INTO `part` VALUES ('19', '后勤部', '初始管理员', '2017-11-20 11:24:20');
INSERT INTO `part` VALUES ('20', '人力资源部', '初始管理员', '2017-11-20 11:24:57');
INSERT INTO `part` VALUES ('21', '销售部', '初始管理员', '2017-11-20 11:25:41');
INSERT INTO `part` VALUES ('22', '技术部', '初始管理员', '2017-11-20 11:25:48');
INSERT INTO `part` VALUES ('23', '保卫部', '初始管理员', '2017-11-20 11:26:22');

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
INSERT INTO `procedure_option` VALUES ('14', '10', '请假开始时间', '2017年11月22日', '1');
INSERT INTO `procedure_option` VALUES ('15', '10', '请假结束时间', '2017年11月23日', '2');
INSERT INTO `procedure_option` VALUES ('16', '10', '请假原因', '拉肚子', '3');
INSERT INTO `procedure_option` VALUES ('17', '11', '活动主题', '生日', '1');
INSERT INTO `procedure_option` VALUES ('18', '11', '活动内容', '庆祝新成员生日', '2');
INSERT INTO `procedure_option` VALUES ('19', '11', '活动开始时间', '2017年12月18日19:00:00', '3');
INSERT INTO `procedure_option` VALUES ('20', '11', '活动结束时间', '2017年12月18日21:0:00', '4');
INSERT INTO `procedure_option` VALUES ('21', '11', '活动地点', '公司食堂', '5');
INSERT INTO `procedure_option` VALUES ('22', '11', '活动人员', '销售部门第一小组全体人员', '6');
INSERT INTO `procedure_option` VALUES ('23', '11', '活动经费', '500', '7');
INSERT INTO `procedure_option` VALUES ('24', '11', '活动物品', '', '8');
INSERT INTO `procedure_option` VALUES ('25', '11', '活动主办人', '王艳娇', '9');
INSERT INTO `procedure_option` VALUES ('26', '12', '请假原因', '生病', '1');
INSERT INTO `procedure_option` VALUES ('27', '12', '请假开始时间', '2017年12月10日', '2');
INSERT INTO `procedure_option` VALUES ('28', '12', '请假结束时间', '2017年12月11日', '3');
INSERT INTO `procedure_option` VALUES ('29', '13', '请假原因', '生病', '1');
INSERT INTO `procedure_option` VALUES ('30', '13', '请假开始时间', '2017年12月8日', '2');
INSERT INTO `procedure_option` VALUES ('31', '13', '请假结束时间', '2017年12月9日', '3');
INSERT INTO `procedure_option` VALUES ('32', '14', '001', '123', '1');
INSERT INTO `procedure_option` VALUES ('33', '15', ' 是的', '23', '1');
INSERT INTO `procedure_option` VALUES ('34', '16', '0', 'uuu', '1');
INSERT INTO `procedure_option` VALUES ('35', '17', '0', '12', '1');

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
INSERT INTO `procedure_shen` VALUES ('11', '10', '请假意见', '31', '18', '1041', '陈娟', '允许', '2017-11-20 22:01:40', '1', '1', '1');
INSERT INTO `procedure_shen` VALUES ('12', '11', '意见一', '31', '18', '1001', '王二', '同意。', '2017-12-07 21:23:40', '1', '1', '1');
INSERT INTO `procedure_shen` VALUES ('13', '11', '意见二', '37', '19', '1014', '陈柏升', '同意', '2017-12-08 15:09:35', '2', '1', '1');
INSERT INTO `procedure_shen` VALUES ('14', '11', '意见三', '42', '23', '1035', '王飞', '同意', '2017-12-08 15:10:19', '3', '1', '1');
INSERT INTO `procedure_shen` VALUES ('15', '12', '总审批人意见', '2', '1', '1000', '初始管理员', null, null, '1', null, '2');
INSERT INTO `procedure_shen` VALUES ('16', '13', '总审批人意见', '2', '1', '1000', '初始管理员', '不同意。', '2017-12-08 14:59:59', '1', '0', '1');
INSERT INTO `procedure_shen` VALUES ('17', '14', '002', '31', '18', '1002', '王三', '是的', '2017-12-22 17:08:02', '1', '1', '1');
INSERT INTO `procedure_shen` VALUES ('18', '15', '但是', '31', '18', '1001', '王二', null, null, '1', null, '2');
INSERT INTO `procedure_shen` VALUES ('19', '16', '存储1', '31', '18', '1002', '王三', null, null, '1', null, '2');
INSERT INTO `procedure_shen` VALUES ('20', '16', '存储2', '31', '18', '1001', '王二', null, null, '2', null, '0');
INSERT INTO `procedure_shen` VALUES ('21', '17', '存储1', '31', '18', '1002', '王三', '123', '2017-12-22 17:11:15', '1', '0', '1');
INSERT INTO `procedure_shen` VALUES ('22', '17', '存储2', '31', '18', '1001', '王二', null, null, '2', null, '3');

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
INSERT INTO `procedure_submit` VALUES ('10', '财务部请假', '请假申请', '部门请假申请', '2017-11-20 21:59:08', '1002', '1', '财务部', '第一小组', null, null, '无');
INSERT INTO `procedure_submit` VALUES ('11', '小型活动申请', '庆祝生日', '活动名称', '2017-12-07 21:23:05', '1023', '1', '销售部', '第一组', null, null, '无');
INSERT INTO `procedure_submit` VALUES ('12', '公司请假', '请假申请', '是否请假', '2017-12-08 14:51:03', '1000', '3', '初始部门', '初始小组', null, null, '无');
INSERT INTO `procedure_submit` VALUES ('13', '公司请假', '请假申请', '是否请假', '2017-12-08 14:57:02', '1000', '2', '初始部门', '初始小组', null, null, '无');
INSERT INTO `procedure_submit` VALUES ('14', '测试一，必须', '123', '6', '2017-12-22 17:05:33', '1000', '1', '初始部门', '初始小组', '想念熊13.gif', '1000_1513933533390.gif', '4');
INSERT INTO `procedure_submit` VALUES ('15', '测试三，没有', '23 ', '6', '2017-12-22 17:06:07', '1000', '3', '初始部门', '初始小组', null, null, '4');
INSERT INTO `procedure_submit` VALUES ('16', '测试二，允许', '有', '6', '2017-12-22 17:06:32', '1000', '3', '初始部门', '初始小组', '想念熊6.gif', '1000_1513933592035.gif', '4');
INSERT INTO `procedure_submit` VALUES ('17', '测试二，允许', '111', '6', '2017-12-22 17:06:57', '1000', '2', '初始部门', '初始小组', null, null, '4');

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
INSERT INTO `remind` VALUES ('37', '0000', '0');
INSERT INTO `remind` VALUES ('38', '0000', '0');
INSERT INTO `remind` VALUES ('44', '0000', '0');
INSERT INTO `remind` VALUES ('37', '1001', '0');
INSERT INTO `remind` VALUES ('44', '1001', '0');
INSERT INTO `remind` VALUES ('48', '1001', '1');
INSERT INTO `remind` VALUES ('37', '1002', '0');
INSERT INTO `remind` VALUES ('44', '1002', '0');
INSERT INTO `remind` VALUES ('37', '1003', '0');
INSERT INTO `remind` VALUES ('44', '1003', '0');
INSERT INTO `remind` VALUES ('37', '1004', '0');
INSERT INTO `remind` VALUES ('44', '1004', '0');
INSERT INTO `remind` VALUES ('37', '1005', '0');
INSERT INTO `remind` VALUES ('44', '1005', '0');
INSERT INTO `remind` VALUES ('37', '1006', '0');
INSERT INTO `remind` VALUES ('44', '1006', '0');
INSERT INTO `remind` VALUES ('37', '1007', '0');
INSERT INTO `remind` VALUES ('44', '1007', '0');
INSERT INTO `remind` VALUES ('37', '1008', '0');
INSERT INTO `remind` VALUES ('44', '1008', '0');
INSERT INTO `remind` VALUES ('37', '1009', '0');
INSERT INTO `remind` VALUES ('44', '1009', '0');
INSERT INTO `remind` VALUES ('37', '1010', '0');
INSERT INTO `remind` VALUES ('44', '1010', '0');
INSERT INTO `remind` VALUES ('37', '1011', '0');
INSERT INTO `remind` VALUES ('44', '1011', '0');
INSERT INTO `remind` VALUES ('37', '1012', '0');
INSERT INTO `remind` VALUES ('44', '1012', '0');
INSERT INTO `remind` VALUES ('37', '1013', '0');
INSERT INTO `remind` VALUES ('43', '1013', '1');
INSERT INTO `remind` VALUES ('44', '1013', '0');
INSERT INTO `remind` VALUES ('36', '1014', '1');
INSERT INTO `remind` VALUES ('37', '1014', '0');
INSERT INTO `remind` VALUES ('44', '1014', '0');
INSERT INTO `remind` VALUES ('37', '1015', '0');
INSERT INTO `remind` VALUES ('44', '1015', '0');
INSERT INTO `remind` VALUES ('37', '1016', '0');
INSERT INTO `remind` VALUES ('44', '1016', '0');
INSERT INTO `remind` VALUES ('37', '1017', '0');
INSERT INTO `remind` VALUES ('44', '1017', '0');
INSERT INTO `remind` VALUES ('37', '1018', '0');
INSERT INTO `remind` VALUES ('44', '1018', '0');
INSERT INTO `remind` VALUES ('37', '1019', '0');
INSERT INTO `remind` VALUES ('44', '1019', '0');
INSERT INTO `remind` VALUES ('37', '1020', '0');
INSERT INTO `remind` VALUES ('44', '1020', '0');
INSERT INTO `remind` VALUES ('37', '1021', '0');
INSERT INTO `remind` VALUES ('44', '1021', '0');
INSERT INTO `remind` VALUES ('37', '1022', '0');
INSERT INTO `remind` VALUES ('44', '1022', '0');
INSERT INTO `remind` VALUES ('44', '1023', '0');
INSERT INTO `remind` VALUES ('37', '1024', '0');
INSERT INTO `remind` VALUES ('44', '1024', '0');
INSERT INTO `remind` VALUES ('37', '1025', '0');
INSERT INTO `remind` VALUES ('44', '1025', '0');
INSERT INTO `remind` VALUES ('37', '1026', '0');
INSERT INTO `remind` VALUES ('44', '1026', '0');
INSERT INTO `remind` VALUES ('37', '1027', '0');
INSERT INTO `remind` VALUES ('44', '1027', '0');
INSERT INTO `remind` VALUES ('37', '1028', '0');
INSERT INTO `remind` VALUES ('44', '1028', '0');
INSERT INTO `remind` VALUES ('37', '1029', '0');
INSERT INTO `remind` VALUES ('44', '1029', '0');
INSERT INTO `remind` VALUES ('37', '1030', '0');
INSERT INTO `remind` VALUES ('44', '1030', '0');
INSERT INTO `remind` VALUES ('37', '1031', '0');
INSERT INTO `remind` VALUES ('44', '1031', '0');
INSERT INTO `remind` VALUES ('37', '1032', '0');
INSERT INTO `remind` VALUES ('44', '1032', '0');
INSERT INTO `remind` VALUES ('37', '1033', '0');
INSERT INTO `remind` VALUES ('44', '1033', '0');
INSERT INTO `remind` VALUES ('37', '1034', '0');
INSERT INTO `remind` VALUES ('44', '1034', '0');
INSERT INTO `remind` VALUES ('37', '1035', '0');
INSERT INTO `remind` VALUES ('44', '1035', '0');
INSERT INTO `remind` VALUES ('45', '1035', '1');
INSERT INTO `remind` VALUES ('37', '1036', '0');
INSERT INTO `remind` VALUES ('44', '1036', '0');
INSERT INTO `remind` VALUES ('37', '1037', '0');
INSERT INTO `remind` VALUES ('44', '1037', '0');
INSERT INTO `remind` VALUES ('37', '1038', '0');
INSERT INTO `remind` VALUES ('44', '1038', '0');
INSERT INTO `remind` VALUES ('37', '1039', '0');
INSERT INTO `remind` VALUES ('44', '1039', '0');
INSERT INTO `remind` VALUES ('37', '1040', '0');
INSERT INTO `remind` VALUES ('44', '1040', '0');
INSERT INTO `remind` VALUES ('37', '1041', '0');
INSERT INTO `remind` VALUES ('44', '1041', '0');
INSERT INTO `remind` VALUES ('37', '1042', '0');
INSERT INTO `remind` VALUES ('44', '1042', '0');
INSERT INTO `remind` VALUES ('37', '1043', '0');
INSERT INTO `remind` VALUES ('44', '1043', '0');
INSERT INTO `remind` VALUES ('37', '1044', '0');
INSERT INTO `remind` VALUES ('44', '1044', '0');
INSERT INTO `remind` VALUES ('37', '1045', '0');
INSERT INTO `remind` VALUES ('44', '1045', '0');
INSERT INTO `remind` VALUES ('37', '1046', '0');
INSERT INTO `remind` VALUES ('44', '1046', '0');
INSERT INTO `remind` VALUES ('37', '1047', '0');
INSERT INTO `remind` VALUES ('44', '1047', '0');
INSERT INTO `remind` VALUES ('37', '1048', '0');
INSERT INTO `remind` VALUES ('44', '1048', '0');
INSERT INTO `remind` VALUES ('37', '1049', '0');
INSERT INTO `remind` VALUES ('44', '1049', '0');
INSERT INTO `remind` VALUES ('37', '1050', '0');
INSERT INTO `remind` VALUES ('44', '1050', '0');

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
INSERT INTO `user_info` VALUES ('0000', '0000', '系统', '0', '1', '1', null, null, null, '0', '2000-01-01 00:00:31', '0', '2', '0', null);
INSERT INTO `user_info` VALUES ('1000', '211103', '初始管理员', '0', '1', '2', '15042311234', '123@123.com', null, '0', '2017-10-11 11:06:05', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1001', '211101', '王二', '0', '18', '31', null, null, null, '2', '2017-11-20 21:20:36', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1002', '211102', '王三', '1', '18', '31', null, null, null, '3', '2017-11-20 21:20:44', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1003', '211103', '王五', '1', '19', '38', null, null, null, '2', '2017-11-20 21:23:35', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1004', '211104', '陈慧青', '1', '18', '32', null, null, null, '2', '2017-11-20 21:20:51', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1005', '211105', '王蓦瑶', '0', '18', '32', null, null, null, '3', '2017-11-20 21:20:58', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1006', '211106', '陈小峻', '1', '18', '33', null, null, null, '2', '2017-11-20 21:21:07', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1007', '211107', '王漫', '0', '18', '34', null, null, null, '2', '2017-11-20 21:21:18', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1008', '211108', '陈籽菡', '1', '18', '34', null, null, null, '3', '2017-11-20 21:21:26', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1009', '211109', '孙奎', '1', '18', '35', null, null, null, '2', '2017-11-20 21:21:34', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1010', '211110', '王宏', '1', '18', '35', null, null, null, '3', '2017-11-20 21:21:40', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1011', '211111', '李福娃', '0', '18', '33', null, null, null, '3', '2017-11-20 21:22:00', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1012', '211112', '李卓豫', '0', '19', '36', null, null, null, '2', '2017-11-20 21:22:08', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1013', '211113', '王畦嵘', '1', '19', '36', null, null, null, '3', '2017-11-20 21:22:15', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1014', '211114', '陈柏升', '1', '19', '37', null, null, null, '2', '2017-11-20 21:22:25', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1015', '211115', '孙楮', '1', '19', '37', null, null, null, '3', '2017-11-20 21:22:41', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1016', '211116', '陈柏升', '1', '19', '38', null, null, null, '3', '2017-11-20 21:23:49', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1017', '211117', '孙位', '0', '20', '39', null, null, null, '2', '2017-11-20 21:23:57', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1018', '211118', '孙路', '1', '20', '39', null, null, null, '3', '2017-11-20 21:24:03', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1019', '211119', '孙楚一', '1', '20', '43', null, null, null, '2', '2017-11-20 21:24:13', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1020', '211120', '李利坚', '0', '20', '43', null, null, null, '3', '2017-11-20 21:24:19', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1021', '211121', '陈宇', '0', '20', '44', null, null, null, '2', '2017-11-20 21:24:27', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1022', '211122', '李政斌', '1', '20', '44', null, null, null, '3', '2017-11-20 21:24:41', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1023', '211123', '王艳娇', '0', '21', '40', null, null, null, '2', '2017-11-20 21:25:25', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1024', '211124', '陈佳', '0', '21', '45', null, null, null, '2', '2017-11-20 21:25:33', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1025', '211125', '王誉玲', '0', '21', '40', null, null, null, '3', '2017-11-20 21:25:48', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1026', '211126', '李荣成', '0', '21', '46', null, null, null, '2', '2017-11-20 21:25:42', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1027', '211127', '王斐然', '1', '21', '45', null, null, null, '3', '2017-11-20 21:25:55', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1028', '211128', '王雪蕊', '0', '21', '46', null, null, null, '3', '2017-11-20 21:26:01', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1029', '211129', '陈素言', '0', '22', '41', null, null, null, '2', '2017-11-20 21:34:00', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1030', '211130', '王科晴', '1', '22', '47', null, null, null, '2', '2017-11-20 21:34:07', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1031', '211131', '王鹏云', '1', '22', '48', null, null, null, '2', '2017-11-20 21:34:16', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1032', '211132', '李毅', '1', '22', '41', null, null, null, '3', '2017-11-20 21:34:22', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1033', '211133', '陈旭斌', '1', '22', '47', null, null, null, '3', '2017-11-20 21:34:30', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1034', '211134', '李正娟', '0', '22', '48', null, null, null, '3', '2017-11-20 21:34:37', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1035', '211135', '王飞', '1', '23', '42', null, null, null, '2', '2017-11-20 21:34:45', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1036', '211136', '陈二方', '0', '23', '49', null, null, null, '2', '2017-11-20 21:34:51', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1037', '211137', '陈金斌', '1', '23', '50', null, null, null, '2', '2017-11-20 21:34:58', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1038', '211138', '王敦强', '1', '23', '42', null, null, null, '3', '2017-11-20 21:35:04', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1039', '211139', '王佛兰', '0', '23', '49', null, null, null, '3', '2017-11-20 21:35:18', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1040', '211140', '陈宇', '1', '23', '50', null, null, null, '3', '2017-11-20 21:35:11', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1041', '211141', '陈娟', '0', '18', '31', null, null, null, '1', '2017-11-20 21:36:28', '839688824ee88492e71fa88f82987456', '1', '0', null);
INSERT INTO `user_info` VALUES ('1042', '211142', '陈素言', '0', '19', '36', null, null, null, '1', '2017-11-20 21:36:47', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1043', '211143', '王欣', '0', '20', '39', null, null, null, '1', '2017-11-20 21:36:37', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1044', '211144', '孙楚一', '0', '21', '40', null, null, null, '1', '2017-11-20 21:37:09', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1045', '211145', '李毅', '0', '22', '41', null, null, null, '1', '2017-11-20 21:37:20', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1046', '211146', '王小燕', '1', '23', '42', null, null, null, '1', '2017-11-20 21:37:38', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1047', '211147', '孙亚伟', '1', '22', '41', null, null, null, '3', '2017-11-20 21:37:50', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1048', '211148', '陈恒盛', '0', '22', '41', null, null, null, '3', '2017-11-20 21:37:57', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1049', '211149', '孙优优', '1', '20', '43', null, null, null, '3', '2017-11-20 21:38:14', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1050', '211150', '李平', '0', '20', '43', null, null, null, '3', '2017-11-20 21:38:22', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1090', '211190', '张三', '0', '19', '26', null, null, null, '3', '2017-12-08 15:07:15', '2a0a92e34ddb799623d76de5f894a3c3', '0', '0', null);
INSERT INTO `user_info` VALUES ('1091', '211191', '李四', '1', '19', '26', null, null, null, '3', '2017-12-08 15:07:15', '839688824ee88492e71fa88f82987456', '1', '4', null);

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
