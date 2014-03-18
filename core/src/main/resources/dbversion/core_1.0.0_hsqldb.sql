-- 删除表
drop table if exists sys_db_version;
drop table if exists sys_meta_field;
drop table if exists sys_meta;
drop table if exists sys_layout_prop;
drop table if exists sys_layout;

/*==============================================================*/
/* Table: sys_db_version                                      */
/*==============================================================*/
create table sys_db_version
(
  sys_name           VARCHAR(64)         not null,
  db_version         VARCHAR(11),
  input_date         DATE                 not null,
  memo               VARCHAR(1024),
  constraint PK_SYS_DB_VERSION primary key (sys_name)
);

comment on TABLE sys_db_version is '系统版本信息';
comment on COLUMN sys_db_version.sys_name is '系统名称';
comment on COLUMN sys_db_version.db_version is '数据库版本';
comment on COLUMN sys_db_version.input_date is '发布日期';
comment on COLUMN sys_db_version.memo is '备注';

/*==============================================================*/
/* Table: sys_meta                                              */
/*==============================================================*/
create table sys_meta
(
  id                   varchar(32) not null,
  name                 varchar(128) not null,
  display_name         varchar(128),
  desc               varchar(1024),
  is_valid             char(1) not null,
  sort_num             int not null,
  input_date           datetime not null,
  primary key (id)
);
comment on TABLE sys_meta is '元数据';
comment on COLUMN sys_meta.id is '元数据ID';
comment on COLUMN sys_meta.name is '元数据名称';
comment on COLUMN  sys_meta.display_name is '元数据显示名';
comment on COLUMN  sys_meta.desc is '描述';
comment on COLUMN  sys_meta.is_valid is '是否有效';
comment on COLUMN  sys_meta.sort_num is '排序号';
comment on COLUMN  sys_meta.input_date is '录入时间';

/*==============================================================*/
/* Table: sys_meta_field                                        */
/*==============================================================*/
create table sys_meta_field
(
   id                   varchar(32) not null,
   meta_id              varchar(32) not null,
   name                 varchar(128) not null,
   display_name         varchar(64),
   data_type            varchar(64) not null,
   desc               varchar(1024),
   default_value        varchar(1024),
   dict_id              varchar(32),
   db_column            varchar(128),
   is_valid             char(1) not null,
   sort_num             int not null,
   input_date           datetime not null,
   primary key (id)
);

comment on table sys_meta_field is '元字段信息';
comment on column sys_meta_field.id is '元数据字段ID';
comment on column sys_meta_field.meta_id is '元数据ID';
comment on column sys_meta_field.name is '字段名称';
comment on column sys_meta_field.display_name is '显示名';
comment on column sys_meta_field.data_type is '数据类型';
comment on column sys_meta_field.desc is '描述';
comment on column sys_meta_field.default_value is '默认值';
comment on column sys_meta_field.dict_id is '数据字典';
comment on column sys_meta_field.db_column is '对应数据库列';
comment on column sys_meta_field.is_valid is '是否有效';
comment on column sys_meta_field.sort_num is '排序号';
comment on column sys_meta_field.input_date is '录入时间';


/*==============================================================*/
/* Table: sys_layout                                            */
/*==============================================================*/
create table sys_layout
(
   id                   varchar(32) not null,
   pid                  varchar(32),
   name                 varchar(64) not null,
   display_name         varchar(128) not null,
   desc               varchar(1024),
   sort_num             int not null,
   primary key (id)
);

comment on table sys_layout is '布局管理器';
comment on column sys_layout.id is '布局ID';
comment on column sys_layout.pid is '布局父ID';
comment on column sys_layout.name is '布局名称';
comment on column sys_layout.display_name is '布局显示名';
comment on column sys_layout.desc is '描述';
comment on column sys_layout.sort_num is '排序号';


/*==============================================================*/
/* Table: sys_layout_prop                                       */
/*==============================================================*/
create table sys_layout_prop
(
   id                   varchar(32) not null,
   layout_id            varchar(32) not null,
   name                 varchar(64) not null,
   display_name         varchar(128) not null,
   default_value        varchar(128),
   desc        varchar(1024),
   sort_num             int not null,
   primary key (id)
);

comment on table sys_layout_prop is '布局属性';
comment on column sys_layout_prop.id is '布局属性ID';
comment on column sys_layout_prop.layout_id is '布局ID';
comment on column sys_layout_prop.name is '属性名称';
comment on column sys_layout_prop.display_name is '显示名';
comment on column sys_layout_prop.default_value is '默认值';
comment on column sys_layout_prop.desc is '描述';
comment on column sys_layout_prop.sort_num is '排序号';

-- 约束
alter table sys_meta_field add constraint FK_meta_id foreign key (meta_id)
      references sys_meta (id) on delete cascade on update cascade;

alter table sys_layout_prop add constraint FK_layout_id foreign key (layout_id)
      references sys_layout (id) on delete cascade on update cascade;
-- 索引
create unique index IUX_NAME on sys_layout
(
   name
);

-- 插入升级语句
insert into sys_db_version (sys_name, db_version, input_date, memo) values('core', '1.0.0', CURDATE(), '系统自动升级到1.0.0');
