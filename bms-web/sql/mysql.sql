/*
Navicat MySQL Data Transfer

Source Server         : mysql-me
Source Server Version : 50633
Source Host           : 101.132.33.136:3306
Source Database       : console

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2018-05-17 09:38:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T_BULLETIN_READ_LOG
-- ----------------------------
DROP TABLE IF EXISTS `T_BULLETIN_READ_LOG`;
CREATE TABLE `T_BULLETIN_READ_LOG` (
  `ID` varchar(64) NOT NULL,
  `BULLETIN_ID` varchar(64) DEFAULT NULL COMMENT '公告ID',
  `READ_STATUS` varchar(20) DEFAULT NULL COMMENT '公告阅读状态',
  `LOGIN_NAME` varchar(32) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL COMMENT '数据标识，是否有效 1：有效,0:无效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统公告阅读状态表';

-- ----------------------------
-- Records of T_BULLETIN_READ_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYSTEM_BULLETIN
-- ----------------------------
DROP TABLE IF EXISTS `T_SYSTEM_BULLETIN`;
CREATE TABLE `T_SYSTEM_BULLETIN` (
  `ID` varchar(64) NOT NULL,
  `BULLETIN_TITLE` varchar(200) DEFAULT NULL COMMENT '公告标题',
  `BULLETIN_CONTENT` varchar(500) DEFAULT NULL COMMENT '公告内容',
  `BEGIN_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `END_DATE` datetime DEFAULT NULL COMMENT '结束时间',
  `RELEASE_CHANNEL` varchar(200) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统公告';

-- ----------------------------
-- Records of T_SYSTEM_BULLETIN
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_CONFIG`;
CREATE TABLE `T_SYS_CONFIG` (
  `ID` varchar(64) NOT NULL,
  `CONFIG_CODE` varchar(64) DEFAULT NULL,
  `CONFIG_NAME` varchar(200) DEFAULT NULL,
  `CONFIG_DESC` varchar(300) DEFAULT NULL,
  `CONFIG_VALUE` varchar(200) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime NOT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';

-- ----------------------------
-- Records of T_SYS_CONFIG
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_DATA_DICTIONARY
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_DATA_DICTIONARY`;
CREATE TABLE `T_SYS_DATA_DICTIONARY` (
  `ID` varchar(64) NOT NULL,
  `DICT_CODE` varchar(64) DEFAULT NULL,
  `CODE` varchar(64) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `MARKS` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime(6) DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of T_SYS_DATA_DICTIONARY
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_DEPT
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_DEPT`;
CREATE TABLE `T_SYS_DEPT` (
  `ID` varchar(255) NOT NULL COMMENT '主键',
  `DEP_LEADER` varchar(32) DEFAULT NULL COMMENT '部门领导',
  `DEP_NAME` varchar(30) NOT NULL COMMENT '部门名称',
  `DEP_TYPE` varchar(32) DEFAULT NULL COMMENT '部门类型',
  `DESCRIPTION` varchar(120) DEFAULT NULL COMMENT '部门描述',
  `ORDER_NUMBER` int(11) DEFAULT NULL COMMENT '部门表',
  `PARENT_ID` varchar(32) NOT NULL COMMENT '部门父id',
  `DEP_CODE` varchar(32) DEFAULT NULL COMMENT '部门编码',
  `ACTIVE_FLAG` int(1) DEFAULT NULL COMMENT '部门表',
  `CREATE_BY` varchar(60) DEFAULT NULL COMMENT '创建者',
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(60) DEFAULT NULL COMMENT '修改者',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of T_SYS_DEPT
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_DICTIONARY
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_DICTIONARY`;
CREATE TABLE `T_SYS_DICTIONARY` (
  `ID` varchar(64) NOT NULL,
  `CODE` varchar(64) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `MARKS` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of T_SYS_DICTIONARY
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_FILE
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_FILE`;
CREATE TABLE `T_SYS_FILE` (
  `ID` varchar(32) NOT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `SIZE` int(11) DEFAULT NULL,
  `EXT` varchar(100) DEFAULT NULL,
  `FILE_PATH` varchar(100) DEFAULT NULL,
  `TYPE` varchar(60) DEFAULT NULL,
  `CREATE_BY` varchar(32) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(32) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件';

-- ----------------------------
-- Records of T_SYS_FILE
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_FUNCTION
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_FUNCTION`;
CREATE TABLE `T_SYS_FUNCTION` (
  `ID` varchar(60) NOT NULL,
  `FUN_CODE` varchar(32) DEFAULT NULL,
  `FUN_NAME` varchar(60) DEFAULT NULL,
  `ORDER_NUMBER` int(11) DEFAULT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  `URL` varchar(60) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ICON` varchar(32) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `CREATE_BY` varchar(60) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(60) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT '0000-00-00 00:00:00',
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of T_SYS_FUNCTION
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_POSITION
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_POSITION`;
CREATE TABLE `T_SYS_POSITION` (
  `ID` varchar(60) NOT NULL,
  `POSITION_CODE` varchar(50) DEFAULT NULL,
  `POSITION_NAME` varchar(50) DEFAULT NULL,
  `MARKS` varchar(255) DEFAULT NULL,
  `CREATE_BY` varchar(60) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(60) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位表';

-- ----------------------------
-- Records of T_SYS_POSITION
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_ROLE`;
CREATE TABLE `T_SYS_ROLE` (
  `ID` varchar(64) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `ROLE_CODE` varchar(100) DEFAULT NULL,
  `REMARKS` varchar(255) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of T_SYS_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_ROLE_DATA
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_ROLE_DATA`;
CREATE TABLE `T_SYS_ROLE_DATA` (
  `ID` varchar(64) NOT NULL,
  `ROLE_CODE` varchar(64) DEFAULT NULL,
  `CTRL_TYPE` varchar(64) DEFAULT NULL COMMENT '控制类型：公司、部门、组',
  `CTRL_DATA` varchar(64) DEFAULT NULL COMMENT '控制数据',
  `CREATE_BY` varchar(32) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(32) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色数据权限表';

-- ----------------------------
-- Records of T_SYS_ROLE_DATA
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_ROLE_FUNCTION
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_ROLE_FUNCTION`;
CREATE TABLE `T_SYS_ROLE_FUNCTION` (
  `ID` varchar(64) NOT NULL,
  `ROLE_CODE` varchar(64) DEFAULT NULL,
  `FUNCTION_CODE` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime NOT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of T_SYS_ROLE_FUNCTION
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_SCHEDULER
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_SCHEDULER`;
CREATE TABLE `T_SYS_SCHEDULER` (
  `ID` varchar(64) NOT NULL,
  `JOB_NAME` varchar(100) DEFAULT NULL,
  `JOB_CLASS` varchar(100) DEFAULT NULL,
  `CRON` varchar(100) DEFAULT NULL,
  `IS_START` char(1) DEFAULT NULL,
  `NOTES` varchar(100) DEFAULT NULL,
  `JOB_PARAMS` varchar(1000) DEFAULT NULL,
  `CREATE_BY` varchar(32) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(32) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of T_SYS_SCHEDULER
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_SESSION
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_SESSION`;
CREATE TABLE `T_SYS_SESSION` (
  `ID` varchar(64) NOT NULL,
  `IP` varchar(20) DEFAULT NULL,
  `LAST_ACCESS_TIME` timestamp(6) NULL DEFAULT '0000-00-00 00:00:00.000000',
  `SESSION_ID` varchar(64) DEFAULT NULL,
  `SESSION_VALUE` varchar(3000) DEFAULT NULL,
  `LOGIN_NAME` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(255) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话表';

-- ----------------------------
-- Records of T_SYS_SESSION
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_USER`;
CREATE TABLE `T_SYS_USER` (
  `id` varchar(64) NOT NULL,
  `channel_code` varchar(30) DEFAULT NULL,
  `login_name` varchar(30) DEFAULT NULL,
  `real_name` varchar(30) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `tel` varchar(30) DEFAULT NULL COMMENT '电话',
  `phone` varchar(30) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `company_Code` varchar(100) DEFAULT NULL,
  `dept_Code` varchar(100) DEFAULT NULL,
  `group_Code` varchar(100) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
  `position_code` varchar(50) DEFAULT NULL COMMENT '职位编码',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active_flag` int(11) DEFAULT NULL COMMENT '数据有效标识 1：有效 0：无效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of T_SYS_USER
-- ----------------------------
INSERT INTO `T_SYS_USER` VALUES ('00000', null, '000000', null, null, null, null, null, 'cc0de7ced8358f24d545a68ca1485d10', null, null, null, 'b80a86ef4c1462a44a47e5701b3afbdd', null, null, null, null, null, '1');

-- ----------------------------
-- Table structure for T_SYS_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_USER_ROLE`;
CREATE TABLE `T_SYS_USER_ROLE` (
  `ID` varchar(64) NOT NULL,
  `LOGIN_NAME` varchar(64) DEFAULT NULL,
  `ROLE_CODE` varchar(64) DEFAULT NULL,
  `REMARKS` varchar(255) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `T_SYS_USER_ROLE_LOGINNAME` (`LOGIN_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of T_SYS_USER_ROLE
-- ----------------------------
