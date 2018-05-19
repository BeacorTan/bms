-- Create table
create table MAPP_PLATFORM.T_USER_BASIC
(
  id               VARCHAR2(64) not null,
  channel_code     VARCHAR2(30) not null,
  login_name       VARCHAR2(30) not null,
  real_name        VARCHAR2(30),
  sex              VARCHAR2(4),
  tel              VARCHAR2(30),
  phone            VARCHAR2(30),
  email            VARCHAR2(50),
  password         VARCHAR2(50),
  is_frozen        VARCHAR2(1) not null,
  err_count_pw     NUMBER default 0 not null,
  reserve1         VARCHAR2(100),
  reserve2         VARCHAR2(100),
  reserve3         VARCHAR2(100),
  create_time      NUMBER(20),
  create_user      VARCHAR2(64) not null,
  create_user_name VARCHAR2(30),
  is_active        VARCHAR2(1) not null,
  oprat_time       TIMESTAMP(6),
  oprator          NVARCHAR2(30),
  salt             VARCHAR2(64),
  position_code    VARCHAR2(50)
)
tablespace MOBILEAPP2
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table MAPP_PLATFORM.T_USER_BASIC
  is '控台-操作员管理表';
-- Add comments to the columns
comment on column MAPP_PLATFORM.T_USER_BASIC.id
  is '主键';
comment on column MAPP_PLATFORM.T_USER_BASIC.channel_code
  is '无任何意义';
comment on column MAPP_PLATFORM.T_USER_BASIC.login_name
  is '登录账号';
comment on column MAPP_PLATFORM.T_USER_BASIC.real_name
  is '登录者姓名';
comment on column MAPP_PLATFORM.T_USER_BASIC.sex
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.tel
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.phone
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.email
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.password
  is '登录者密码';
comment on column MAPP_PLATFORM.T_USER_BASIC.is_frozen
  is '暂不用（默认为0）';
comment on column MAPP_PLATFORM.T_USER_BASIC.err_count_pw
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.reserve1
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.reserve2
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.reserve3
  is '暂不用';
comment on column MAPP_PLATFORM.T_USER_BASIC.create_time
  is '创建时间';
comment on column MAPP_PLATFORM.T_USER_BASIC.create_user
  is '创建者';
comment on column MAPP_PLATFORM.T_USER_BASIC.create_user_name
  is '创建者姓名';
comment on column MAPP_PLATFORM.T_USER_BASIC.is_active
  is '状态（0-有效；1-无效）';
comment on column MAPP_PLATFORM.T_USER_BASIC.oprat_time
  is '操作时间';
comment on column MAPP_PLATFORM.T_USER_BASIC.oprator
  is '操作者';
comment on column MAPP_PLATFORM.T_USER_BASIC.position_code
  is '客户编码';
-- Create/Recreate primary, unique and foreign key constraints
alter table MAPP_PLATFORM.T_USER_BASIC
  add primary key (ID)
  using index
  tablespace MOBILEAPP2
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Grant/Revoke object privileges



create table T_SYS_DEPTT
(
  ID           VARCHAR2(255) not null,
  ACTIVE_FLAG  NUMBER(11),
  CREATE_BY    VARCHAR2(60),
  CREATE_DATE  TIMESTAMP(6),
  UPDATE_BY    VARCHAR2(60),
  UPDATE_DATE  TIMESTAMP(6),
  DEP_LEADER   VARCHAR2(32),
  DEP_NAME     VARCHAR2(30) not null,
  DEP_TYPE     VARCHAR2(32),
  DESCRIPTION  VARCHAR2(120),
  ORDER_NUMBER NUMBER(11),
  PARENT_ID    VARCHAR2(32) not null,
  DEP_CODE     VARCHAR2(32),
  PRIMARY KEY (ID)
)
comment on table T_SYS_DEPT is '部门表';
comment on column T_SYS_DEPT.ID is '主键';
comment on column T_SYS_DEPT.CREATE_BY is '创建用户';
comment on column T_SYS_DEPT.CREATE_DATE is '创建时间';
comment on column T_SYS_DEPT.UPDATE_BY is '修改用户';
comment on column T_SYS_DEPT.UPDATE_DATE is '修改时间';
comment on column T_SYS_DEPT.DEP_NAME is '部门名称';
comment on column T_SYS_DEPT.DEP_TYPE is '部门类型';
comment on column T_SYS_DEPT.DESCRIPTION is '部门描述';
comment on column T_SYS_DEPT.PARENT_ID is '部门父id';
comment on column T_SYS_DEPT.DEP_CODE is '部门编码';


create table T_SYS_FILE
(
  PARENT_ID   VARCHAR2(32) not null,
  ID          VARCHAR2(32) not null,
  NAME        VARCHAR2(100),
  "SIZE"        NUMBER(11),
  EXT         VARCHAR2(100),
  FILE_PATH   VARCHAR2(100) not null,
  CREATE_BY   VARCHAR2(32) not null,
  CREATE_DATE TIMESTAMP(6) not null,
  UPDATE_BY   VARCHAR2(32),
  UPDATE_DATE TIMESTAMP(6),
  ACTIVE_FLAG CHAR(1) not null,
  TYPE        VARCHAR2(60)
)
comment on table T_SYS_FILE is '用户头像';
comment on column T_SYS_FILE.ID is '主键';
comment on column T_SYS_FILE.PARENT_ID is '父id';

create table T_SYS_FUNCTION
(
  ID           VARCHAR2(255) not null,
  ACTIVE_FLAG  NUMBER(11),
  CREATE_BY    VARCHAR2(60),
  CREATE_DATE  TIMESTAMP(6),
  UPDATE_BY    VARCHAR2(60),
  UPDATE_DATE  TIMESTAMP(6),
  FUN_CODE     VARCHAR2(32) not null,
  FUN_NAME     VARCHAR2(60) not null,
  ORDER_NUMBER NUMBER(11),
  PARENT_ID    VARCHAR2(32),
  URL          VARCHAR2(60),
  DESCRIPTION  VARCHAR2(255),
  ICON         VARCHAR2(32),
  TYPE         NUMBER(11) not null,
  PRIMARY KEY (ID)
)
comment on table T_SYS_FUNCTION is '权限表、菜单表';
comment on column T_SYS_FUNCTION.ID is '主键';
comment on column T_SYS_FUNCTION.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_FUNCTION.CREATE_BY is '创建者';
comment on column T_SYS_FUNCTION.CREATE_DATE is '创建时间';
comment on column T_SYS_FUNCTION.UPDATE_BY is '修改者';
comment on column T_SYS_FUNCTION.UPDATE_DATE is '修改时间';
comment on column T_SYS_FUNCTION.FUN_CODE is '权限code';
comment on column T_SYS_FUNCTION.FUN_NAME is '权限名称';
comment on column T_SYS_FUNCTION.ORDER_NUMBER is '排序序号';
comment on column T_SYS_FUNCTION.PARENT_ID is '父id';
comment on column T_SYS_FUNCTION.DESCRIPTION is '描述';
comment on column T_SYS_FUNCTION.ICON is '菜单图标样式';
comment on column T_SYS_FUNCTION.TYPE is '权限类型（1：菜单、0：按钮）';
comment on column T_SYS_FUNCTION.URL is '请求路径';





create table T_SYS_SCHEDULER
(
  ID          VARCHAR2(32) not null,
  ACTIVE_FLAG NUMBER(11) not null,
  CREATE_BY   VARCHAR2(32) not null,
  CREATE_DATE TIMESTAMP(6) not null,
  UPDATE_BY   VARCHAR2(32),
  UPDATE_DATE TIMESTAMP(6),
  JOB_NAME    VARCHAR2(100),
  JOB_CLASS   VARCHAR2(100),
  CRON        VARCHAR2(100),
  IS_START    CHAR(1),
  NOTES       VARCHAR2(100),
  JOB_PARAMS  VARCHAR2(1000),
    PRIMARY KEY (ID)
)
comment on table T_SYS_SCHEDULER is '定时任务调度表';

create table T_SYS_SESSION
(
  ID               VARCHAR2(64) not null,
  ACTIVE_FLAG      NUMBER(11),
  CREATE_BY        VARCHAR2(255),
  CREATE_DATE      TIMESTAMP(6),
  UPDATE_BY        VARCHAR2(255),
  UPDATE_DATE      TIMESTAMP(6),
  IP               VARCHAR2(20),
  LAST_ACCESS_TIME TIMESTAMP(6),
  SESSION_ID       VARCHAR2(64),
  SESSION_VALUE    VARCHAR2(3000),
  LOGIN_NAME       VARCHAR2(64),
  PRIMARY KEY (ID)
)
comment on table T_T_SYS_SESSION is '会话表';
comment on column T_T_SYS_SESSION.CREATE_BY is '创建者';
comment on column T_T_SYS_SESSION.CREATE_DATE is '创建时间';
comment on column T_T_SYS_SESSION.UPDATE_BY is '修改者';
comment on column T_T_SYS_SESSION.UPDATE_DATE is '修改时间';
comment on column T_T_SYS_SESSION.ACTIVE_FLAG is '数据标识，是否有效 1：有效';


create table T_SYS_ROLE
(
  ID          VARCHAR2(64) not null,
  ROLE_NAME   VARCHAR2(100) not null,
  ROLE_CODE   VARCHAR2(100) not null,
  CREATE_BY   VARCHAR2(64) not null,
  CREATE_DATE TIMESTAMP(6) not null,
  UPDATE_BY   VARCHAR2(64) not null,
  UPDATE_DATE TIMESTAMP(6) not null,
  REMARKS     VARCHAR2(255),
  ACTIVE_FLAG CHAR(1) not null,
  PRIMARY KEY (id)
)
comment on table T_SYS_ROLE is '角色表';
comment on column T_SYS_ROLE.id is '主键';
comment on column T_SYS_ROLE.CREATE_BY is '创建者';
comment on column T_SYS_ROLE.CREATE_DATE is '创建时间';
comment on column T_SYS_ROLE.UPDATE_BY is '修改者';
comment on column T_SYS_ROLE.UPDATE_DATE is '修改时间';
comment on column T_SYS_ROLE.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_ROLE.ROLE_NAME is '角色名称';
comment on column T_SYS_ROLE.ROLE_CODE is '角色编码';



 -- 用户角色表
create table T_SYS_USER_ROLE
(
  ID          VARCHAR2(64) not null,
  LOGIN_NAME  VARCHAR2(64),
  ROLE_CODE   VARCHAR2(64),
  CREATE_BY   VARCHAR2(64),
  CREATE_DATE TIMESTAMP(6),
  UPDATE_BY   VARCHAR2(64),
  UPDATE_DATE TIMESTAMP(6),
  REMARKS     VARCHAR2(255),
  ACTIVE_FLAG NUMBER(1) ,
  PRIMARY KEY (id)
)
comment on table T_SYS_USER_ROLE is '用户角色表';
comment on column T_SYS_USER_ROLE.id is '主键';
comment on column T_SYS_USER_ROLE.CREATE_BY is '创建者';
comment on column T_SYS_USER_ROLE.CREATE_DATE is '创建时间';
comment on column T_SYS_USER_ROLE.UPDATE_BY is '修改者';
comment on column T_SYS_USER_ROLE.UPDATE_DATE is '修改时间';
comment on column T_SYS_USER_ROLE.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_USER_ROLE.LOGIN_NAME is '用户名';
comment on column T_SYS_USER_ROLE.ROLE_CODE is '角色编码';



create table T_SYS_ROLE_FUNCTION
(
  ID            VARCHAR2(64) not null,
  ROLE_CODE     VARCHAR2(64),
  FUNCTION_CODE VARCHAR2(64),
  CREATE_BY     VARCHAR2(64),
  CREATE_DATE   TIMESTAMP(6),
  UPDATE_BY     VARCHAR2(64),
  UPDATE_DATE   TIMESTAMP(6),
  ACTIVE_FLAG   NUMBER(1),
  PRIMARY KEY (id)
)
comment on table T_SYS_ROLE_FUNCTION is '角色权限表';
comment on column T_SYS_ROLE_FUNCTION.id is '主键';
comment on column T_SYS_ROLE_FUNCTION.CREATE_BY is '创建者';
comment on column T_SYS_ROLE_FUNCTION.CREATE_DATE is '创建时间';
comment on column T_SYS_ROLE_FUNCTION.UPDATE_BY is '修改者';
comment on column T_SYS_ROLE_FUNCTION.UPDATE_DATE is '修改时间';
comment on column T_SYS_ROLE_FUNCTION.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_ROLE_FUNCTION.FUNCTION_CODE is '权限编码';
comment on column T_SYS_ROLE_FUNCTION.ROLE_CODE is '角色编码';




create table T_SYS_DICTIONARY
(
  ID            VARCHAR2(64) not null,
  CODE     VARCHAR2(64),
  NAME  VARCHAR2(64),
  CREATE_BY     VARCHAR2(64),
  CREATE_DATE   TIMESTAMP(6),
  UPDATE_BY     VARCHAR2(64),
  UPDATE_DATE   TIMESTAMP(6),
  ACTIVE_FLAG   NUMBER(1),
  MARKS   VARCHAR2(64),
  PRIMARY KEY (id)
)
comment on table T_SYS_DICTIONARY is '字典表';
comment on column T_SYS_DICTIONARY.id is '主键';
comment on column T_SYS_DICTIONARY.CREATE_BY is '创建者';
comment on column T_SYS_DICTIONARY.CREATE_DATE is '创建时间';
comment on column T_SYS_DICTIONARY.UPDATE_BY is '修改者';
comment on column T_SYS_DICTIONARY.UPDATE_DATE is '修改时间';
comment on column T_SYS_DICTIONARY.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_DICTIONARY.NAME is '名称';


create table T_SYS_DATA_DICTIONARY
(
  ID            VARCHAR2(64) not null,
  DICT_CODE  VARCHAR2(64),
  CODE     VARCHAR2(64),
  NAME  VARCHAR2(255),
  CREATE_BY     VARCHAR2(64),
  CREATE_DATE   TIMESTAMP(6),
  UPDATE_BY     VARCHAR2(64),
  UPDATE_DATE   TIMESTAMP(6),
  ACTIVE_FLAG   NUMBER(1),
  MARKS   VARCHAR2(64),
  PRIMARY KEY (id)
)
comment on table T_SYS_DATA_DICTIONARY is '数据字典表';
comment on column T_SYS_DATA_DICTIONARY.id is '主键';
comment on column T_SYS_DATA_DICTIONARY.CREATE_BY is '创建者';
comment on column T_SYS_DATA_DICTIONARY.CREATE_DATE is '创建时间';
comment on column T_SYS_DATA_DICTIONARY.UPDATE_BY is '修改者';
comment on column T_SYS_DATA_DICTIONARY.UPDATE_DATE is '修改时间';
comment on column T_SYS_DATA_DICTIONARY.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_DATA_DICTIONARY.NAME is '名称';


create table T_SYS_POSITION
(
  ID   VARCHAR2(60) not null,
  POSITION_CODE  VARCHAR2(50),
  POSITION_NAME  VARCHAR2(50),
  CREATE_BY    VARCHAR2(60),
  CREATE_DATE  TIMESTAMP(6),
  UPDATE_BY    VARCHAR2(60),
  UPDATE_DATE  TIMESTAMP(6),
  MARKS VARCHAR2(255),
  ACTIVE_FLAG number(1),
  PRIMARY KEY (ID)
);
comment on table T_SYS_POSITION is '职位表';
comment on column T_SYS_POSITION.id is '主键';
comment on column T_SYS_POSITION.POSITION_CODE is '职位编码';
comment on column T_SYS_POSITION.POSITION_NAME is '职位名称';
comment on column T_SYS_POSITION.CREATE_BY is '创建者';
comment on column T_SYS_POSITION.CREATE_DATE is '创建时间';
comment on column T_SYS_POSITION.UPDATE_BY is '修改者';
comment on column T_SYS_POSITION.UPDATE_DATE is '修改时间';
comment on column T_SYS_POSITION.MARKS is '备注';
comment on column T_SYS_POSITION.ACTIVE_FLAG is '数据标识，是否有效 1：有效';



create table T_SYS_ROLE_DATA
(
  ID            VARCHAR2(64) not null,
  ROLE_CODE     VARCHAR2(64),
  CTRL_TYPE VARCHAR2(64),
  CTRL_DATA VARCHAR2(64),
  CREATE_BY     VARCHAR2(64),
  CREATE_DATE   TIMESTAMP(6),
  UPDATE_BY     VARCHAR2(64),
  UPDATE_DATE   TIMESTAMP(6),
  ACTIVE_FLAG   NUMBER(1),
  PRIMARY KEY (id)
);
comment on table T_SYS_ROLE_DATA is '角色数据权限表';
comment on column T_SYS_ROLE_DATA.id is '主键';
comment on column T_SYS_ROLE_DATA.CREATE_BY is '创建者';
comment on column T_SYS_ROLE_DATA.CREATE_DATE is '创建时间';
comment on column T_SYS_ROLE_DATA.UPDATE_BY is '修改者';
comment on column T_SYS_ROLE_DATA.UPDATE_DATE is '修改时间';
comment on column T_SYS_ROLE_DATA.ACTIVE_FLAG is '数据标识，是否有效 1：有效';
comment on column T_SYS_ROLE_DATA.CTRL_TYPE is '控制类型：公司、部门、组';
comment on column T_SYS_ROLE_DATA.CTRL_DATA is '控制数据';
comment on column T_SYS_ROLE_DATA.ROLE_CODE is '角色编码';


-- 系统配置
CREATE TABLE T_SYS_CONFIG
(
  ID          VARCHAR2(64) NOT NULL,
  CONFIG_CODE  VARCHAR2(64),
  CONFIG_NAME  VARCHAR2(200),
  CONFIG_DESC  VARCHAR2(300),
  CONFIG_VALUE  VARCHAR2(200),
  CREATE_BY   VARCHAR2(64),
  CREATE_DATE TIMESTAMP(6),
  UPDATE_BY   VARCHAR2(64),
  UPDATE_DATE TIMESTAMP(6),
  ACTIVE_FLAG NUMBER(1) ,
  PRIMARY KEY (ID)
);
COMMENT ON TABLE T_SYS_CONFIG IS '系统配置表';
COMMENT ON COLUMN T_SYS_CONFIG.ID IS '主键ID';
COMMENT ON COLUMN T_SYS_CONFIG.CONFIG_CODE IS '配置CODE,唯一';
COMMENT ON COLUMN T_SYS_CONFIG.CONFIG_NAME IS '标识名称';
COMMENT ON COLUMN T_SYS_CONFIG.CONFIG_DESC IS '备注';
COMMENT ON COLUMN T_SYS_CONFIG.CONFIG_VALUE IS '此CONFIG_CODE下对应的value值';
COMMENT ON COLUMN T_SYS_CONFIG.CREATE_BY IS '创建者';
COMMENT ON COLUMN T_SYS_CONFIG.CREATE_DATE IS '创建时间';
COMMENT ON COLUMN T_SYS_CONFIG.UPDATE_BY IS '更新者';
COMMENT ON COLUMN T_SYS_CONFIG.UPDATE_DATE IS '更新时间';
COMMENT ON COLUMN T_SYS_CONFIG.ACTIVE_FLAG IS '删除标记(1 未删除 0 删除)';
alter table T_SYS_CONFIG add constraint un_sys_config_config_code unique(CONFIG_CODE);


CREATE TABLE T_SYSTEM_BULLETIN
(
  ID          VARCHAR2(64) NOT NULL,
  BULLETIN_TITLE  VARCHAR2(200),
  BULLETIN_CONTENT  VARCHAR2(500),
  BEGIN_DATE  TIMESTAMP(6),
  END_DATE  TIMESTAMP(6),
  RELEASE_CHANNEL   VARCHAR2(200),

  CREATE_BY   VARCHAR2(64),
  CREATE_DATE TIMESTAMP(6),
  UPDATE_BY   VARCHAR2(64),
  UPDATE_DATE TIMESTAMP(6),
  ACTIVE_FLAG NUMBER(1) ,
  PRIMARY KEY (ID)
);
COMMENT ON TABLE T_SYSTEM_BULLETIN IS '系统公告';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.ID IS '主键';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.CREATE_BY IS '创建者';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.CREATE_DATE IS '创建时间';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.UPDATE_BY IS '修改者';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.UPDATE_DATE IS '修改时间';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.ACTIVE_FLAG IS '数据标识，是否有效 1：有效';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.BULLETIN_TITLE IS '公告标题';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.BULLETIN_CONTENT IS '公告内容';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.BEGIN_DATE IS '开始时间';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.END_DATE IS '结束时间';
COMMENT ON COLUMN T_SYSTEM_BULLETIN.RELEASE_CHANNEL IS '发布渠道';



CREATE TABLE T_READ_LOG
(
  ID          VARCHAR2(64) NOT NULL,
  BULLETIN_ID  VARCHAR2(64),
  READ_STATUS  VARCHAR2(20),
  LOGIN_NAME  VARCHAR2(64),

  CREATE_BY   VARCHAR2(64),
  CREATE_DATE TIMESTAMP(6),
  UPDATE_BY   VARCHAR2(64),
  UPDATE_DATE TIMESTAMP(6),
  ACTIVE_FLAG NUMBER(1) ,
  PRIMARY KEY (ID)
);
COMMENT ON TABLE T_READ_LOG IS '系统公告阅读状态表';
COMMENT ON COLUMN T_READ_LOG.ID IS '主键';
COMMENT ON COLUMN T_READ_LOG.CREATE_BY IS '创建者';
COMMENT ON COLUMN T_READ_LOG.CREATE_DATE IS '创建时间';
COMMENT ON COLUMN T_READ_LOG.UPDATE_BY IS '修改者';
COMMENT ON COLUMN T_READ_LOG.UPDATE_DATE IS '修改时间';
COMMENT ON COLUMN T_READ_LOG.ACTIVE_FLAG IS '数据标识，是否有效 1：有效';
COMMENT ON COLUMN T_READ_LOG.BULLETIN_ID IS '公告ID';
COMMENT ON COLUMN T_READ_LOG.READ_STATUS IS '公告阅读状态';
COMMENT ON COLUMN T_READ_LOG.LOGIN_NAME IS '用户名';
