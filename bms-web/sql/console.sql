/*
Navicat MySQL Data Transfer

Source Server         : mysql-me
Source Server Version : 50633
Source Host           : 101.132.33.136:3306
Source Database       : console

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2018-07-26 17:01:33
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
  `CREATE_DATE` datetime DEFAULT NULL,
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
  `AUTHOR` varchar(100) DEFAULT NULL COMMENT '结束时间',
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
INSERT INTO `t_system_bulletin` VALUES ('63786FC6B742473682CBE2A33C1F6FDF', '1', '3', '000000', '1', '000000', '2018-07-12 18:17:47', '000000', '2018-07-13 11:01:11', '1');

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
  `CREATE_DATE` datetime NOT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime NOT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_config_code` (`CONFIG_CODE`)
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
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime(6) DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of t_sys_data_dictionary
-- ----------------------------
INSERT INTO `t_sys_data_dictionary` VALUES ('031DC73DA7E84D948CF26BDAA2417720', '1', '111', '11', '11111', '000000', '2018-07-26 16:36:30', '000000', '2018-07-26 16:47:18.719000', '1');
INSERT INTO `t_sys_data_dictionary` VALUES ('F2E75CBD25B74738BB1496E23A40E7BC', '1', '1-1', '1-1', '222', '000000', '2018-07-12 17:25:38', '000000', '2018-07-26 16:47:10.895000', '1');

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `ID` varchar(100) NOT NULL COMMENT '主键',
  `DEPT_CODE` varchar(64) NOT NULL COMMENT '部门编码',
  `DEPT_NAME` varchar(64) NOT NULL COMMENT '部门名称',
  `DEPT_TYPE` varchar(32) DEFAULT NULL COMMENT '部门类型',
  `PARENT_CODE` varchar(100) DEFAULT NULL COMMENT '部门父id',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父部门名称',
  `tree_names` varchar(2000) DEFAULT NULL COMMENT '全节点名',
  `parent_codes` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `tree_leaf` char(1) DEFAULT NULL COMMENT '是否最末级',
  `tree_level` tinyint(4) DEFAULT NULL COMMENT '层次级别',
  `tree_sort` decimal(10,0) DEFAULT NULL COMMENT '本级排序号（升序）',
  `LEADER` varchar(32) DEFAULT NULL COMMENT '部门领导',
  `phone` varchar(100) DEFAULT NULL COMMENT '办公电话',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `email` varchar(300) DEFAULT NULL COMMENT '电子邮箱',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `CREATE_BY` varchar(60) DEFAULT NULL COMMENT '创建者',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(60) DEFAULT NULL COMMENT '修改者',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  `ACTIVE_FLAG` int(1) DEFAULT NULL COMMENT '部门表',
  PRIMARY KEY (`ID`),
  KEY `idx_dept_code` (`DEPT_CODE`),
  KEY `idx_dept_parent_code` (`PARENT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------
INSERT INTO `t_sys_dept` VALUES ('E9C9ED0021E140A6B3BF9C402B48D201', '2', '上海黄浦分公司', 'company', '1', null, null, null, '0', '1', '1', null, null, null, null, null, null, '000000', '2018-05-25 14:03:09', '000000', '2018-05-25 15:17:44', '1');
INSERT INTO `t_sys_dept` VALUES ('E9C9ED0021E140A6B3BF9C402B48D202', '3', '上海浦东分公司', 'company', '1', null, null, null, '1', '1', '1', null, null, null, null, null, null, '000000', '2018-05-25 14:03:09', '000000', '2018-05-25 15:17:44', '1');
INSERT INTO `t_sys_dept` VALUES ('E9C9ED0021E140A6B3BF9C402B48D203', '4', '上海静安分公司', 'company', '1', null, null, null, '1', '1', '1', null, null, null, null, null, null, '000000', '2018-05-25 14:03:09', '000000', '2018-05-25 15:17:44', '1');
INSERT INTO `t_sys_dept` VALUES ('E9C9ED0021E140A6B3BF9C402B48D2D2', '1', '上海公司', 'company', '0', null, null, null, '0', '0', '1', '啊啊啊', null, null, null, null, null, '000000', '2018-05-25 14:03:09', '000000', '2018-07-10 17:02:42', '1');
INSERT INTO `t_sys_dept` VALUES ('E9C9ED0021E140A6B3BF9C402B48D301', '5', '上海打浦桥分公司', 'company', '2', null, null, null, '1', '2', '1', null, null, null, null, null, null, '000000', '2018-05-25 14:03:09', '000000', '2018-07-12 11:18:49', '1');

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
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of t_sys_dictionary
-- ----------------------------
INSERT INTO `t_sys_dictionary` VALUES ('60EC1DF5CCCD4F2A9C30D915833C1E22', '1', '1', '2', '000000', '2018-07-12 15:34:20', '000000', '2018-07-12 15:22:34', '1');
INSERT INTO `t_sys_dictionary` VALUES ('A7B7409EA78F4237B6222C3B1DB49C2A', '2', '2', '2', '000000', '2018-07-12 16:47:02', '000000', '2018-07-12 16:47:02', '1');

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
  `CREATE_DATE` datetime DEFAULT NULL,
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
  `FUN_CODE` varchar(32) NOT NULL,
  `FUN_NAME` varchar(60) DEFAULT NULL,
  `PARENT_NAME` varchar(100) DEFAULT NULL COMMENT '父菜单名称',
  `PARENT_CODES` varchar(255) DEFAULT NULL COMMENT '所有父级编号',
  `PARENT_CODE` varchar(100) DEFAULT NULL COMMENT '父菜单code',
  `FUN_HREF` varchar(60) DEFAULT NULL COMMENT '请求路径',
  `FUN_ICON` varchar(32) DEFAULT NULL COMMENT '菜单图标',
  `FUN_COLOR` varchar(255) DEFAULT NULL COMMENT '菜单字体颜色',
  `TREE_LEAF` tinyint(4) DEFAULT '1' COMMENT '是否最末级 1:是 0：否',
  `TREE_LEVEL` tinyint(4) DEFAULT NULL COMMENT '层次级别',
  `TREE_SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `TYPE` tinyint(1) DEFAULT '1' COMMENT '菜单类型 1：菜单  0：权限按钮',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `CREATE_BY` varchar(60) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(60) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uni_funtion_code` (`FUN_CODE`),
  KEY `idx_function_parent_code` (`PARENT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of t_sys_function
-- ----------------------------
INSERT INTO `t_sys_function` VALUES ('01560316E7064540A041FB3C7FCEE344', 'select', '选择器', null, null, 'FUNCATIONCODE1515906197208', 'select/main', 'icon-globe', null, '1', '1', '7', '1', null, '000000', '2018-06-27 15:29:33', '000000', '2018-06-12 10:56:58', '1');
INSERT INTO `t_sys_function` VALUES ('07453972A9AF4F5B886D8D6A9274D79E', 'FUNCATIONCODE1521167224892', '组织管理', '首页', 'null/1', '1', 'position', 'icon-grid', null, '0', '0', '2', '1', null, '000000', '2018-07-09 15:32:35', '000000', '2018-07-09 15:32:35', '1');
INSERT INTO `t_sys_function` VALUES ('0764057339294FECB026049D4595C761', 'function:btn:add', '新增', '菜单管理', 'null/FUNCATIONCODE1515913865281', 'FUNCATIONCODE1515913865281', '/function/profile', null, null, '1', '2', '1', '0', null, '000000', '2018-07-09 15:35:20', '000000', '2018-07-09 15:35:20', '1');
INSERT INTO `t_sys_function` VALUES ('123', '1', '首页', null, null, '0', '/index', null, null, '0', '-1', '1', '1', '首页', '123', '2018-07-09 15:30:56', null, '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('1B4102D742C84443960F401A1035BB46', 'FUNCATIONCODE1515913865281', '菜单管理', null, null, 'FUNCATIONCODE1515906197208', 'function/main', 'icon-settings', null, '0', '1', '40', '1', null, 'admin', '2018-07-09 15:35:28', 'admin', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('2333F83C26C04A6683EC324333422CDA', 'crmSysConfig', '系统配置', '系统管理', 'null/FUNCATIONCODE1515906197208', 'FUNCATIONCODE1515906197208', 'config/main', 'icon-settings', null, '1', '1', '40', '1', null, '000000', '2018-07-12 11:44:57', '000000', '2018-07-12 11:44:57', '1');
INSERT INTO `t_sys_function` VALUES ('290F5050CCF84ECB94206F337B778D62', 'SCHEDULER', '定时任务', '系统管理', 'null/FUNCATIONCODE1515906197208', 'FUNCATIONCODE1515906197208', 'scheduler/main', 'icon-arrow-right', null, '1', '1', null, '1', null, '000000', '2018-07-13 11:44:18', '000000', '2018-07-13 11:44:18', '1');
INSERT INTO `t_sys_function` VALUES ('32348ADA43334872A5BF369AD7A1C678', 'FUNCATIONCODE1522822105221', '部门管理', '组织管理', 'null/FUNCATIONCODE1521167224892', 'FUNCATIONCODE1521167224892', 'dept/main', 'icon-emoticon-smile', null, '1', '1', '2', '1', null, '000000', '2018-07-12 15:19:40', '000000', '2018-07-12 15:19:40', '1');
INSERT INTO `t_sys_function` VALUES ('478CE9F7710343898FAA92EE38E3F22D', 'FUNCATIONCODE1521167305642', '职位管理', null, null, 'FUNCATIONCODE1521167224892', 'position/main', 'icon-star', null, '1', '1', null, '1', null, '000000', '2018-06-27 15:29:33', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('D0FE02D3169742DAA0372D792488237E', 'FUNCATIONCODE1522822087887', '用户管理', null, null, 'FUNCATIONCODE1521167224892', 'user/main', 'icon-user-follow', null, '1', '1', '1', '1', null, '000000', '2018-06-27 15:29:33', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('D38460BFC0334888B553904C19737A8D', 'bulletin', '公告', null, null, 'FUNCATIONCODE1515906197208', 'bulletin/main', 'icon-map', null, '1', '1', '6', '1', null, '000000', '2018-06-27 15:29:33', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('D7E68611F5004E6B8CABA7165F49F8CB', 'roleCode', '角色管理', null, null, 'FUNCATIONCODE1515906197208', 'role/main', 'icon-bulb', null, '1', '1', '30', '1', null, 'admin', '2018-06-27 15:29:33', 'admin', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('E42BB0914BBB43D4BB2DCCCD2D21DEC9', 'FUNCATIONCODE1515906197208', '系统管理', null, null, '1', 'system', 'icon-grid', null, '0', '0', '90', '1', null, 'admin', '2018-06-27 15:28:37', '000000', '2018-05-17 10:37:04', '1');
INSERT INTO `t_sys_function` VALUES ('F563E3EABBD144DD8C9A4E0E364F4BD1', 'FUNCATIONCODE1522031992915', '字典管理', null, null, 'FUNCATIONCODE1515906197208', 'dict/main', 'icon-book-open', null, '1', '1', '2', '1', null, '000000', '2018-06-27 15:29:33', '000000', '2018-05-17 10:37:04', '1');

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
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(60) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_position_code` (`POSITION_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位表';

-- ----------------------------
-- Records of t_sys_position
-- ----------------------------
INSERT INTO `t_sys_position` VALUES ('4F1E98D1C2B7414983F924E760F608D7', 'POST1532425835867', 'qweqe', 'qeqe', '000000', '2018-07-24 17:50:36', '000000', '2018-07-24 17:50:36', '1');
INSERT INTO `t_sys_position` VALUES ('9E6282F45D764F47893982DEE5BEA83B', 'POST1532588696237', '123123', '123132', '000000', '2018-07-26 15:04:56', '000000', '2018-07-26 15:04:56', '1');
INSERT INTO `t_sys_position` VALUES ('B723A8DAE8834B50973792FAC13CB41B', 'POST1532423973804', null, null, '000000', '2018-07-24 17:19:34', '000000', '2018-07-24 17:19:34', '1');
INSERT INTO `t_sys_position` VALUES ('BEB221ADE98C414EBF19E32AEE97DB49', 'POST1527042566471', '经理', '', '000000', '2018-05-23 10:29:26', '000000', '2018-05-23 10:29:26', '1');
INSERT INTO `t_sys_position` VALUES ('D2FFD424C5FB419EB4E4E35996857936', 'POST1527042559767', '总经理', '', '000000', '2018-05-23 10:29:20', '000000', '2018-05-23 10:29:20', '1');

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
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_role_code` (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('3E653BD21D3A4F09AB082C76552B523F', '超级管理员', 'ROLE1522135810759', '拥有系统绝对权限', 'admin', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:31', '1');
INSERT INTO `t_sys_role` VALUES ('663E3443761B469C958A94B246960DC9', '11111', 'ROLE1531301966302', '11111', '000000', '2018-07-11 17:39:26', '000000', '2018-07-11 17:39:26', '1');
INSERT INTO `t_sys_role` VALUES ('B3566A417A4646BB82E73D28156FC738', '管理员', 'ROLE1526981472938', null, '000000', '2018-05-22 17:31:13', '000000', '2018-05-22 17:31:13', '1');

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
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(32) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_role_data_role_code` (`ROLE_CODE`),
  KEY `idx_role_data_CTRL_DATA` (`CTRL_DATA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色数据权限表';

-- ----------------------------
-- Records of t_sys_role_data
-- ----------------------------
INSERT INTO `t_sys_role_data` VALUES ('7CC1B6B037DF4BD38E620F06C8F7D839', 'ROLE1531301966302', null, '1', '000000', '2018-07-11 17:39:26', '000000', '2018-07-11 17:39:26', '1');
INSERT INTO `t_sys_role_data` VALUES ('A746195DF6034046ADC813E141A30A7E', 'ROLE1531301966302', null, 'FUNCATIONCODE1515906197208', '000000', '2018-07-11 17:39:26', '000000', '2018-07-11 17:39:26', '1');

-- ----------------------------
-- Table structure for t_sys_role_function
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_function`;
CREATE TABLE `t_sys_role_function` (
  `ID` varchar(64) NOT NULL,
  `ROLE_CODE` varchar(64) DEFAULT NULL,
  `FUNCTION_CODE` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(64) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_role_function_role_code` (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_sys_role_function
-- ----------------------------
INSERT INTO `t_sys_role_function` VALUES ('1AFE6AA6A88C4ACFBB40C43F6783DE21', 'ROLE1522135810759', '1', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('1FE881EC7CF047FDADF6C76811E60F1F', 'ROLE1522135810759', 'roleCode', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('20879BB53D73485A85B84464381558D6', 'ROLE1522135810759', 'SCHEDULER', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('21076EDC52524F839654D05DB5D18718', 'ROLE1522135810759', 'FUNCATIONCODE1522031992915', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('2491344CE258409CBB4B9996F34DF319', 'ROLE1531301966302', '1', '000000', '2018-07-11 17:39:26', '000000', '2018-07-11 17:39:26', '1');
INSERT INTO `t_sys_role_function` VALUES ('2E1F8B4B83AC4C3E989F0C3FF3D4CB16', 'ROLE1531301966302', 'FUNCATIONCODE1515906197208', '000000', '2018-07-11 17:39:26', '000000', '2018-07-11 17:39:26', '1');
INSERT INTO `t_sys_role_function` VALUES ('322E563B9FD6435E9334DA34B8289D32', 'ROLE1522135810759', 'bulletin', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('5459DB2437F64CAFA998F5D9A47C2CC2', 'ROLE1522135810759', 'FUNCATIONCODE1522822087887', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('66EE30D26E8F4E669987C6303B2C2572', 'ROLE1522135810759', 'FUNCATIONCODE1521167305642', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('7BB672C94BC143A0A45B6690C33002BE', 'ROLE1522135810759', 'FUNCATIONCODE1522822105221', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('8EEF9268A03E442C9D6348EDC809251A', 'ROLE1522135810759', 'select', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('9074C1951349487D8CF0DDF403D946AD', 'ROLE1522135810759', 'FUNCATIONCODE1515913865281', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('A75D382F068B4EFB912ADE4EEF5AACDA', 'ROLE1522135810759', 'FUNCATIONCODE1515906197208', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('B03BAE95BB1E49538AB6C581D5FBD898', 'ROLE1522135810759', 'crmSysConfig', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('D230001800E94D44A8CEA44E30A9A1AB', 'ROLE1522135810759', 'function:btn:add', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');
INSERT INTO `t_sys_role_function` VALUES ('D7546F7FA55E45709DC60E20452D146A', 'ROLE1531301966302', 'bulletin', '000000', '2018-07-11 17:39:26', '000000', '2018-07-11 17:39:26', '1');
INSERT INTO `t_sys_role_function` VALUES ('F0C8F2D906C2469C93C2627EB85D2930', 'ROLE1522135810759', 'FUNCATIONCODE1521167224892', '000000', '2018-07-13 11:43:37', '000000', '2018-07-13 11:43:37', '1');

-- ----------------------------
-- Table structure for t_sys_scheduler
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_scheduler`;
CREATE TABLE `t_sys_scheduler` (
  `ID` varchar(64) NOT NULL,
  `JOB_NAME` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `JOB_CLASS` varchar(100) DEFAULT NULL COMMENT '全额类名',
  `CRON` varchar(100) DEFAULT NULL COMMENT 'cron 表达式',
  `URL` varchar(255) DEFAULT NULL COMMENT '手动触发url',
  `IS_START` tinyint(1) DEFAULT NULL COMMENT '启动状态 1：启动 0：停止',
  `NOTES` varchar(100) DEFAULT NULL COMMENT '描述',
  `JOB_PARAMS` varchar(1000) DEFAULT NULL COMMENT '任务参数',
  `CREATE_BY` varchar(32) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(32) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of t_sys_scheduler
-- ----------------------------
INSERT INTO `t_sys_scheduler` VALUES ('F2B7AE6EA91944B1913B6EE0F0303D0D', '测试', 'com.common.quartz.jobs.SchedulerTest', '0/10 * * * * ?', null, '0', null, null, null, '2018-07-13 17:49:46', null, '2018-07-13 14:43:34', '1');

-- ----------------------------
-- Table structure for t_sys_session
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_session`;
CREATE TABLE `t_sys_session` (
  `ID` varchar(64) NOT NULL,
  `IP` varchar(20) DEFAULT NULL,
  `LAST_ACCESS_TIME` timestamp(6) NULL DEFAULT NULL,
  `SESSION_ID` varchar(64) DEFAULT NULL,
  `SESSION_VALUE` varchar(3000) DEFAULT NULL,
  `LOGIN_NAME` varchar(64) DEFAULT NULL,
  `CREATE_BY` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(255) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话表';

-- ----------------------------
-- Records of t_sys_session
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` varchar(64) NOT NULL,
  `login_name` varchar(30) DEFAULT NULL COMMENT '登录账号',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别 1:男 0：女',
  `phone` varchar(30) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(30) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `dept_code` varchar(100) DEFAULT NULL,
  `dept_name` varchar(100) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
  `avatar` varchar(1000) DEFAULT NULL COMMENT '头像路径',
  `position_code` varchar(50) DEFAULT NULL COMMENT '职位编码',
  `last_login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `nick_name` varchar(1000) DEFAULT NULL COMMENT '昵称',
  `mgr_type` char(1) DEFAULT NULL COMMENT '管理员类型（0非管理员 1系统管理员  2二级管理员）',
  `sign` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `active_flag` int(11) DEFAULT NULL COMMENT '数据有效标识 1：有效 0：无效',
  PRIMARY KEY (`id`),
  KEY `idx_user_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('00000', '000000', '超级管理员', '1', '15821235665', '15821235665', '2429516553@qq.com', 'fe5b1066ddf4f9c4f0f9d17afdf7b25a', '1', '上海公司', 'b230735497f16f70d209cc3666463e30', null, 'POST1527042559767', '11111', '2018-07-12 11:08:02', null, null, '22222', null, null, '2018-07-12 11:08:02', '000000', '2018-07-17 13:37:34', '1');

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
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `ACTIVE_FLAG` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `T_SYS_USER_ROLE_LOGINNAME` (`LOGIN_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('0352F6613FD74C2DAB7717E692237DFE', '000000', 'ROLE1526981472938', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 11:07:03', '1');
INSERT INTO `t_sys_user_role` VALUES ('03E8FB128C9E4DFDBAC8255E142F9366', '000000', 'ROLE1526981472938', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 10:51:20', '1');
INSERT INTO `t_sys_user_role` VALUES ('24FC1DA0E63B4BE5BCC76F6B675586A3', '000000', 'ROLE1522135810759', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 10:51:20', '1');
INSERT INTO `t_sys_user_role` VALUES ('6199CCF3BF7B481197AA6A749084DA8F', '000000', 'ROLE1522135810759', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 10:51:20', '1');
INSERT INTO `t_sys_user_role` VALUES ('6AAAF9769608428CAA45C4FAA66C91F4', '000000', 'ROLE1522135810759', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 11:07:03', '1');
INSERT INTO `t_sys_user_role` VALUES ('E3E4CA2E028C4A858E6B183C1A7B896F', '000000', 'ROLE1522135810759', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 10:51:20', '1');
INSERT INTO `t_sys_user_role` VALUES ('F2F8585D67A045368BCF46599A4CE5CB', '000000', 'ROLE1522135810759', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 10:51:20', '1');
INSERT INTO `t_sys_user_role` VALUES ('F6A8B83E832245658A4484F53C31C870', '000000', 'ROLE1522135810759', null, '000000', '2018-07-12 11:09:07', '000000', '2018-07-12 10:51:20', '1');
