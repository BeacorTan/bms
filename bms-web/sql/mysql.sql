/*
Navicat MySQL Data Transfer

Source Server         : mysql-me
Source Server Version : 50633
Source Host           : 101.132.33.136:3306
Source Database       : console

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2018-05-22 10:53:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_bulletin_read_log
-- ----------------------------
DROP TABLE IF EXISTS `t_bulletin_read_log`;
CREATE TABLE `t_bulletin_read_log` (
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
-- Records of t_bulletin_read_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_system_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `t_system_bulletin`;
CREATE TABLE `t_system_bulletin` (
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
-- Records of t_system_bulletin
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
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
-- Records of t_sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_data_dictionary`;
CREATE TABLE `t_sys_data_dictionary` (
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
-- Records of t_sys_data_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
CREATE TABLE `T_SYS_DEPT` (
  `ID` VARCHAR(255) NOT NULL COMMENT '主键',
  `LEADER` VARCHAR(32) DEFAULT NULL COMMENT '部门领导',
  `DEP_NAME` VARCHAR(30) NOT NULL COMMENT '部门名称',
  `DEP_TYPE` VARCHAR(32) DEFAULT NULL COMMENT '部门类型',
  `TREE_NAMES` VARCHAR(2000) NOT NULL COMMENT '全节点名',
  `REMARKS` VARCHAR(120) DEFAULT NULL COMMENT '备注信息',
  `PARENT_CODE` VARCHAR(32) NOT NULL COMMENT '部门父ID',
  `TREE_LEAF` CHAR(1) NOT NULL COMMENT '是否最末级',
  `TREE_LEVEL` DECIMAL(4,0) NOT NULL COMMENT '层次级别',
  `PARENT_CODES` VARCHAR(2000) NOT NULL COMMENT '所有父级编号',
  `TREE_SORT` DECIMAL(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `DEPT_CODE` VARCHAR(32) DEFAULT NULL COMMENT '部门编码',
  `PHONE` VARCHAR(100) DEFAULT NULL COMMENT '办公电话',
  `ADDRESS` VARCHAR(255) DEFAULT NULL COMMENT '联系地址',
  `ZIP_CODE` VARCHAR(100) DEFAULT NULL COMMENT '邮政编码',
  `EMAIL` VARCHAR(300) DEFAULT NULL COMMENT '电子邮箱',
  `ACTIVE_FLAG` INT(1) DEFAULT NULL COMMENT '部门表',
  `CREATE_BY` VARCHAR(60) DEFAULT NULL COMMENT '创建者',
  `CREATE_DATE` DATETIME DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` VARCHAR(60) DEFAULT NULL COMMENT '修改者',
  `UPDATE_DATE` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='部门';



-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dictionary`;
CREATE TABLE `t_sys_dictionary` (
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
-- Records of t_sys_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_file`;
CREATE TABLE `t_sys_file` (
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
-- Records of t_sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_function
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_function`;
CREATE TABLE `t_sys_function` (
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
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_function
-- ----------------------------
INSERT INTO `t_sys_function` VALUES ('07453972A9AF4F5B886D8D6A9274D79E', 'FUNCATIONCODE1521167224892', '组织管理', '2', '123', 'position', null, 'icon-magnet', '1', '000000', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('123', '1', '首页', '1', '0', '/index', '首页', null, '1', '123', '2018-05-17 10:37:04', null, '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('1B4102D742C84443960F401A1035BB46', 'FUNCATIONCODE1515913865281', '菜单管理', '40', 'E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'function/main', null, 'icon-settings', '1', 'admin', '2018-05-17 10:37:04', 'admin', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('2333F83C26C04A6683EC324333422CDA', 'crmSysConfig', '系统配置管理', '40', 'E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'systemConfig/main', null, 'icon-settings', '1', '000000', '2018-05-20 09:38:01', '000000', '2018-05-20 09:38:01', '1');
INSERT INTO `t_sys_function` VALUES ('32348ADA43334872A5BF369AD7A1C678', 'FUNCATIONCODE1522822105611', '部门管理', '2', '07453972A9AF4F5B886D8D6A9274D79E', 'dep/main', null, 'icon-emoticon-smile', '1', '000000', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('478CE9F7710343898FAA92EE38E3F22D', 'FUNCATIONCODE1521167305642', '职位管理', null, '07453972A9AF4F5B886D8D6A9274D79E', 'position/main', null, 'icon-star', '1', '000000', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('D0FE02D3169742DAA0372D792488237E', 'FUNCATIONCODE1522822087887', '用户管理', '1', '07453972A9AF4F5B886D8D6A9274D79E', 'user/main', null, 'icon-user-follow', '1', '000000', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('D38460BFC0334888B553904C19737A8D', 'bulletin', '公告', '6', 'E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'bulletin/main', null, 'icon-map', '1', '000000', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('D7E68611F5004E6B8CABA7165F49F8CB', 'roleCode', '角色管理', '30', 'E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'role/main', null, 'icon-bulb', '1', 'admin', '2018-05-17 10:37:04', 'admin', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'FUNCATIONCODE1515906197208', '系统管理', '90', '123', 'system', null, 'icon-grid', '1', 'admin', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('F563E3EABBD144DD8C9A4E0E364F4BD1', 'FUNCATIONCODE1522031992915', '字典管理', '2', 'E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'dict/main', null, 'icon-book-open', '1', '000000', '2018-05-17 10:37:04', '000000', '2018-05-17 10:37:04', '1');

-- ----------------------------
-- Table structure for t_sys_position
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_position`;
CREATE TABLE `t_sys_position` (
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
-- Records of t_sys_position
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
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
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('3E653BD21D3A4F09AB082C76552B523F', '超级管理员', 'ROLE1522135810759', '拥有系统绝对权限', 'admin', null, '000000', null, '1');

-- ----------------------------
-- Table structure for t_sys_role_data
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_data`;
CREATE TABLE `t_sys_role_data` (
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
-- Records of t_sys_role_data
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_role_function
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_function`;
CREATE TABLE `t_sys_role_function` (
  `ID` varchar(64) NOT NULL,
  `ROLE_CODE` varchar(64) DEFAULT NULL,
  `FUNCTION_CODE` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_sys_role_function
-- ----------------------------
INSERT INTO `t_sys_role_function` VALUES ('0F537BF811B5499F80F6898DE14CD16C', 'ROLE1522135810759', 'FUNCATIONCODE1522031992915', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('1F8CC7CBF35A44A0A533F5479B5314EE', 'ROLE1522135810759', 'FUNCATIONCODE1515913865281', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('2DD7A44156F840FE9B0E7744640871E7', 'ROLE1522135810759', 'FUNCATIONCODE1515906197208', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('34A80503E9C747F991A12EB8C8150231', 'ROLE1522135810759', 'FUNCATIONCODE1522822105611', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('3F17F2F0CFE844B69957709078046384', 'ROLE1522135810759', 'bulletin', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('5AF63F9C73EB4100B6F2F22DB6480560', 'ROLE1522135810759', 'roleCode', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('78E83649E96C4649968F88109D63B88E', 'ROLE1522135810759', 'FUNCATIONCODE1521167305642', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('87EBD36CA4C24AEA956C1ED23B217AA2', 'ROLE1522135810759', 'FUNCATIONCODE1522822087887', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('C292DA6E3D1B4A74B029EF2E28E15333', 'ROLE1522135810759', 'crmSysConfig', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('C2ABDDD1E0AA4BF195B7CA8AFB7490BF', 'ROLE1522135810759', '1', '000000', null, '000000', null, '1');
INSERT INTO `t_sys_role_function` VALUES ('DBA7987C244C48E68589190D6657CC05', 'ROLE1522135810759', 'FUNCATIONCODE1521167224892', '000000', null, '000000', null, '1');

-- ----------------------------
-- Table structure for t_sys_scheduler
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_scheduler`;
CREATE TABLE `t_sys_scheduler` (
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
-- Records of t_sys_scheduler
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_session
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_session`;
CREATE TABLE `t_sys_session` (
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
-- Records of t_sys_session
-- ----------------------------
INSERT INTO `t_sys_session` VALUES ('4ac07027-4d66-411b-b532-7c6248e06209', '0:0:0:0:0:0:0:1', '2018-05-17 11:15:11.612000', '4ac07027-4d66-411b-b532-7c6248e06209', 'rO0ABXNyACpvcmcuYXBhY2hlLnNoaXJvLnNlc3Npb24ubWd0LlNpbXBsZVNlc3Npb26dHKG41YxibgMAAHhwdwIA23QAJDRhYzA3MDI3LTRkNjYtNDExYi1iNTMyLTdjNjI0OGUwNjIwOXNyAA5qYXZhLnV0aWwuRGF0ZWhqgQFLWXQZAwAAeHB3CAAAAWNsFqrQeHNxAH4AA3cIAAABY2wYJHx4dxkAAAAAABt3QAAPMDowOjA6MDowOjA6MDoxc3IAEWphdmEudXRpbC5IYXNoTWFwBQfawcMWYNEDAAJGAApsb2FkRmFjdG9ySQAJdGhyZXNob2xkeHA/QAAAAAAADHcIAAAAEAAAAAV0AAh1c2VySW5mb3NyACNjb20uY2wuY20uYmFzZS51c2VyLm1vZGVsLlVzZXJCYXNpY8d1N6CWo6b4AgANTAALY2hhbm5lbENvZGV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtjb21wYW55Q29kZXEAfgAKTAAIZGVwdENvZGVxAH4ACkwABWVtYWlscQB+AApMAAlncm91cENvZGVxAH4ACkwACWxvZ2luTmFtZXEAfgAKTAAIcGFzc3dvcmRxAH4ACkwABXBob25lcQB+AApMAAxwb3NpdGlvbkNvZGVxAH4ACkwACHJlYWxOYW1lcQB+AApMAARzYWx0cQB+AApMAANzZXhxAH4ACkwAA3RlbHEAfgAKeHIAJmNvbS5jbC5jb21tb24uZnJhbWV3b3JrLmJhc2UuQmFzZU1vZGVsFHyv2s1DhmkCAAZMAAphY3RpdmVGbGFndAATTGphdmEvbGFuZy9JbnRlZ2VyO0wACGNyZWF0ZUJ5cQB+AApMAApjcmVhdGVEYXRldAAQTGphdmEvdXRpbC9EYXRlO0wAAmlkcQB+AApMAAh1cGRhdGVCeXEAfgAKTAAKdXBkYXRlRGF0ZXEAfgANeHBwcHB0AAUwMDAwMHBwcHBwcHB0AAYwMDAwMDB0ACBjYzBkZTdjZWQ4MzU4ZjI0ZDU0NWE2OGNhMTQ4NWQxMHBwcHQAIGI4MGE4NmVmNGMxNDYyYTQ0YTQ3ZTU3MDFiM2FmYmRkcHB0ABFzaGlyb1NhdmVkUmVxdWVzdHNyACZvcmcuYXBhY2hlLnNoaXJvLndlYi51dGlsLlNhdmVkUmVxdWVzdK/OPK15gsq6AgADTAAGbWV0aG9kcQB+AApMAAtxdWVyeVN0cmluZ3EAfgAKTAAKcmVxdWVzdFVSSXEAfgAKeHB0AANHRVRwdAARL2NtbWcvZmF2aWNvbi5pY290AAlsb2dpbk5hbWVxAH4AEHQAUG9yZy5hcGFjaGUuc2hpcm8uc3ViamVjdC5zdXBwb3J0LkRlZmF1bHRTdWJqZWN0Q29udGV4dF9BVVRIRU5USUNBVEVEX1NFU1NJT05fS0VZc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXQATW9yZy5hcGFjaGUuc2hpcm8uc3ViamVjdC5zdXBwb3J0LkRlZmF1bHRTdWJqZWN0Q29udGV4dF9QUklOQ0lQQUxTX1NFU1NJT05fS0VZc3IAMm9yZy5hcGFjaGUuc2hpcm8uc3ViamVjdC5TaW1wbGVQcmluY2lwYWxDb2xsZWN0aW9uqH9YJcajCEoDAAFMAA9yZWFsbVByaW5jaXBhbHN0AA9MamF2YS91dGlsL01hcDt4cHNyABdqYXZhLnV0aWwuTGlua2VkSGFzaE1hcDTATlwQbMD7AgABWgALYWNjZXNzT3JkZXJ4cQB+AAY/QAAAAAAADHcIAAAAEAAAAAF0ACVjb20uY2wuY29tbW9uLnNoaXJvLkF1dGhDdXN0b21SZWFsbV8wc3IAF2phdmEudXRpbC5MaW5rZWRIYXNoU2V02GzXWpXdKh4CAAB4cgARamF2YS51dGlsLkhhc2hTZXS6RIWVlri3NAMAAHhwdwwAAAAQP0AAAAAAAAFxAH4ADnh4AHcBAXEAfgAheHh4', '000000', 'sessionManager', '2018-05-17 11:15:11', null, '2018-05-17 11:15:12', '1');
INSERT INTO `t_sys_session` VALUES ('75280f29-a858-44be-b2c1-310623ad961f', '0:0:0:0:0:0:0:1', '2018-05-20 09:05:46.448000', '75280f29-a858-44be-b2c1-310623ad961f', 'rO0ABXNyACpvcmcuYXBhY2hlLnNoaXJvLnNlc3Npb24ubWd0LlNpbXBsZVNlc3Npb26dHKG41YxibgMAAHhwdwIA23QAJDc1MjgwZjI5LWE4NTgtNDRiZS1iMmMxLTMxMDYyM2FkOTYxZnNyAA5qYXZhLnV0aWwuRGF0ZWhqgQFLWXQZAwAAeHB3CAAAAWN7CWLReHNxAH4AA3cIAAABY3sUu9B4dxkAAAAAABt3QAAPMDowOjA6MDowOjA6MDoxc3IAEWphdmEudXRpbC5IYXNoTWFwBQfawcMWYNEDAAJGAApsb2FkRmFjdG9ySQAJdGhyZXNob2xkeHA/QAAAAAAADHcIAAAAEAAAAAV0AAh1c2VySW5mb3NyAB1jb20uYmFzZS51c2VyLm1vZGVsLlVzZXJCYXNpY7Qqb1hD1xeyAgANTAALY2hhbm5lbENvZGV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtjb21wYW55Q29kZXEAfgAKTAAIZGVwdENvZGVxAH4ACkwABWVtYWlscQB+AApMAAlncm91cENvZGVxAH4ACkwACWxvZ2luTmFtZXEAfgAKTAAIcGFzc3dvcmRxAH4ACkwABXBob25lcQB+AApMAAxwb3NpdGlvbkNvZGVxAH4ACkwACHJlYWxOYW1lcQB+AApMAARzYWx0cQB+AApMAANzZXhxAH4ACkwAA3RlbHEAfgAKeHIAI2NvbS5jb21tb24uZnJhbWV3b3JrLmJhc2UuQmFzZU1vZGVs/+GXXj8Q9ZICAAZMAAphY3RpdmVGbGFndAATTGphdmEvbGFuZy9JbnRlZ2VyO0wACGNyZWF0ZUJ5cQB+AApMAApjcmVhdGVEYXRldAAQTGphdmEvdXRpbC9EYXRlO0wAAmlkcQB+AApMAAh1cGRhdGVCeXEAfgAKTAAKdXBkYXRlRGF0ZXEAfgANeHBwcHB0AAUwMDAwMHBwdAAD5pegcHBwcHQABjAwMDAwMHQAIGNjMGRlN2NlZDgzNThmMjRkNTQ1YTY4Y2ExNDg1ZDEwdAALMTU4MjEyMzU2NjVwdAAP6LaF57qn566h55CG5ZGYdAAgYjgwYTg2ZWY0YzE0NjJhNDRhNDdlNTcwMWIzYWZiZGR0AAExcHQAEXNoaXJvU2F2ZWRSZXF1ZXN0c3IAJm9yZy5hcGFjaGUuc2hpcm8ud2ViLnV0aWwuU2F2ZWRSZXF1ZXN0r848rXmCyroCAANMAAZtZXRob2RxAH4ACkwAC3F1ZXJ5U3RyaW5ncQB+AApMAApyZXF1ZXN0VVJJcQB+AAp4cHQAA0dFVHB0ABAvYm1zL2Zhdmljb24uaWNvdAAJbG9naW5OYW1lcQB+ABF0AFBvcmcuYXBhY2hlLnNoaXJvLnN1YmplY3Quc3VwcG9ydC5EZWZhdWx0U3ViamVjdENvbnRleHRfQVVUSEVOVElDQVRFRF9TRVNTSU9OX0tFWXNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAF0AE1vcmcuYXBhY2hlLnNoaXJvLnN1YmplY3Quc3VwcG9ydC5EZWZhdWx0U3ViamVjdENvbnRleHRfUFJJTkNJUEFMU19TRVNTSU9OX0tFWXNyADJvcmcuYXBhY2hlLnNoaXJvLnN1YmplY3QuU2ltcGxlUHJpbmNpcGFsQ29sbGVjdGlvbqh/WCXGowhKAwABTAAPcmVhbG1QcmluY2lwYWxzdAAPTGphdmEvdXRpbC9NYXA7eHBzcgAXamF2YS51dGlsLkxpbmtlZEhhc2hNYXA0wE5cEGzA+wIAAVoAC2FjY2Vzc09yZGVyeHEAfgAGP0AAAAAAAAx3CAAAABAAAAABdAAiY29tLmNvbW1vbi5zaGlyby5BdXRoQ3VzdG9tUmVhbG1fMHNyABdqYXZhLnV0aWwuTGlua2VkSGFzaFNldNhs11qV3SoeAgAAeHIAEWphdmEudXRpbC5IYXNoU2V0ukSFlZa4tzQDAAB4cHcMAAAAAj9AAAAAAAABc3EAfgAJcHBwdAAFMDAwMDBwcHQAA+aXoHBwcHB0AAYwMDAwMDB0ACBjYzBkZTdjZWQ4MzU4ZjI0ZDU0NWE2OGNhMTQ4NWQxMHQACzE1ODIxMjM1NjY1cHQAD+i2hee6p+euoeeQhuWRmHQAIGI4MGE4NmVmNGMxNDYyYTQ0YTQ3ZTU3MDFiM2FmYmRkdAABMXB4eAB3AQFxAH4AJXh4eA==', '000000', 'sessionManager', '2018-05-20 09:05:46', null, '2018-05-20 09:05:46', '1');
INSERT INTO `t_sys_session` VALUES ('7ce49cdc-76cf-420f-a278-8e9da122c5ed', '0:0:0:0:0:0:0:1', '2018-05-22 10:02:35.933000', '7ce49cdc-76cf-420f-a278-8e9da122c5ed', 'rO0ABXNyACpvcmcuYXBhY2hlLnNoaXJvLnNlc3Npb24ubWd0LlNpbXBsZVNlc3Npb26dHKG41YxibgMAAHhwdwIA23QAJDdjZTQ5Y2RjLTc2Y2YtNDIwZi1hMjc4LThlOWRhMTIyYzVlZHNyAA5qYXZhLnV0aWwuRGF0ZWhqgQFLWXQZAwAAeHB3CAAAAWOFlS5leHNxAH4AA3cIAAABY4WVeh14dxkAAAAAABt3QAAPMDowOjA6MDowOjA6MDoxc3IAEWphdmEudXRpbC5IYXNoTWFwBQfawcMWYNEDAAJGAApsb2FkRmFjdG9ySQAJdGhyZXNob2xkeHA/QAAAAAAADHcIAAAAEAAAAAV0AAh1c2VySW5mb3NyAB1jb20uYmFzZS51c2VyLm1vZGVsLlVzZXJCYXNpY7Qqb1hD1xeyAgANTAALY2hhbm5lbENvZGV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtjb21wYW55Q29kZXEAfgAKTAAIZGVwdENvZGVxAH4ACkwABWVtYWlscQB+AApMAAlncm91cENvZGVxAH4ACkwACWxvZ2luTmFtZXEAfgAKTAAIcGFzc3dvcmRxAH4ACkwABXBob25lcQB+AApMAAxwb3NpdGlvbkNvZGVxAH4ACkwACHJlYWxOYW1lcQB+AApMAARzYWx0cQB+AApMAANzZXhxAH4ACkwAA3RlbHEAfgAKeHIAI2NvbS5jb21tb24uZnJhbWV3b3JrLmJhc2UuQmFzZU1vZGVs/+GXXj8Q9ZICAAZMAAphY3RpdmVGbGFndAATTGphdmEvbGFuZy9JbnRlZ2VyO0wACGNyZWF0ZUJ5cQB+AApMAApjcmVhdGVEYXRldAAQTGphdmEvdXRpbC9EYXRlO0wAAmlkcQB+AApMAAh1cGRhdGVCeXEAfgAKTAAKdXBkYXRlRGF0ZXEAfgANeHBwcHB0AAUwMDAwMHBwdAAD5pegcHBwcHQABjAwMDAwMHQAIGNjMGRlN2NlZDgzNThmMjRkNTQ1YTY4Y2ExNDg1ZDEwdAALMTU4MjEyMzU2NjVwdAAP6LaF57qn566h55CG5ZGYdAAgYjgwYTg2ZWY0YzE0NjJhNDRhNDdlNTcwMWIzYWZiZGR0AAExcHQAEXNoaXJvU2F2ZWRSZXF1ZXN0c3IAJm9yZy5hcGFjaGUuc2hpcm8ud2ViLnV0aWwuU2F2ZWRSZXF1ZXN0r848rXmCyroCAANMAAZtZXRob2RxAH4ACkwAC3F1ZXJ5U3RyaW5ncQB+AApMAApyZXF1ZXN0VVJJcQB+AAp4cHQAA0dFVHB0ABAvYm1zL2Zhdmljb24uaWNvdAAJbG9naW5OYW1lcQB+ABF0AFBvcmcuYXBhY2hlLnNoaXJvLnN1YmplY3Quc3VwcG9ydC5EZWZhdWx0U3ViamVjdENvbnRleHRfQVVUSEVOVElDQVRFRF9TRVNTSU9OX0tFWXNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAF0AE1vcmcuYXBhY2hlLnNoaXJvLnN1YmplY3Quc3VwcG9ydC5EZWZhdWx0U3ViamVjdENvbnRleHRfUFJJTkNJUEFMU19TRVNTSU9OX0tFWXNyADJvcmcuYXBhY2hlLnNoaXJvLnN1YmplY3QuU2ltcGxlUHJpbmNpcGFsQ29sbGVjdGlvbqh/WCXGowhKAwABTAAPcmVhbG1QcmluY2lwYWxzdAAPTGphdmEvdXRpbC9NYXA7eHBzcgAXamF2YS51dGlsLkxpbmtlZEhhc2hNYXA0wE5cEGzA+wIAAVoAC2FjY2Vzc09yZGVyeHEAfgAGP0AAAAAAAAx3CAAAABAAAAABdAAiY29tLmNvbW1vbi5zaGlyby5BdXRoQ3VzdG9tUmVhbG1fMHNyABdqYXZhLnV0aWwuTGlua2VkSGFzaFNldNhs11qV3SoeAgAAeHIAEWphdmEudXRpbC5IYXNoU2V0ukSFlZa4tzQDAAB4cHcMAAAAED9AAAAAAAABcQB+AA54eAB3AQFxAH4AJXh4eA==', '000000', 'sessionManager', '2018-05-22 10:02:35', null, '2018-05-22 10:02:36', '1');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
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
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('00000', '无', '000000', '超级管理员', '1', null, '15821235665', null, 'cc0de7ced8358f24d545a68ca1485d10', null, null, null, 'b80a86ef4c1462a44a47e5701b3afbdd', null, null, '2018-05-17 11:31:04', null, null, '1');

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
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
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('6199CCF3BF7B481197AA6A749084DA8F', '000000', 'ROLE1522135810759', null, '000000', null, '000000', null, '1');
