-- 删除表
drop table if exists sys_view_prop;
drop table if exists sys_view_config;
drop table if exists sys_db_version;
drop table if exists sys_dz_code;
drop table if exists sys_dz_category;
drop table if exists sys_module;
drop table if exists sys_layout_prop;
drop table if exists sys_view_layout;
drop table if exists sys_layout;
drop table if exists sys_view;
drop table if exists sys_meta_field;
drop table if exists sys_meta;


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
/* Table: sys_dz_category                                       */
/*==============================================================*/
create table sys_dz_category
(
   id                   varchar(32) not null,
   name                 varchar(64) not null,
   desc               varchar(1024),
   is_valid             char(1) not null,
   is_system            char(1) not null,
   sort_num             int not null,
   input_date           datetime not null,
   primary key (id)
);

comment on table sys_dz_category is '字典类别';
comment on column sys_dz_category.id is '类别ID';
comment on column sys_dz_category.name is '类别名称';
comment on column sys_dz_category.desc is '描述';
comment on column sys_dz_category.is_valid is '是否有效';
comment on column sys_dz_category.is_system is '是否系统内置';
comment on column sys_dz_category.sort_num is '排序号';
comment on column sys_dz_category.input_date is '录入时间';

/*==============================================================*/
/* Table: sys_dz_code                                           */
/*==============================================================*/
create table sys_dz_code
(
   id                   varchar(32) not null,
   category_id          varchar(32) not null,
   display_name         varchar(128) not null,
   name                 varchar(128) not null,
   desc               varchar(1024),
   is_valid             char(1) not null,
   input_date           datetime not null,
   sort_num             int not null,
   primary key (id)
);

comment on table sys_dz_code is '字典代码';
comment on column sys_dz_code.id is '代码ID';
comment on column sys_dz_code.category_id is '类别ID';
comment on column sys_dz_code.display_name is '代码显示名';
comment on column sys_dz_code.name is '代码名称';
comment on column sys_dz_code.desc is '描述';
comment on column sys_dz_code.is_valid is '是否有效';
comment on column sys_dz_code.input_date is '录入时间';
comment on column sys_dz_code.sort_num is '排序号';

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
  ds_name              varchar(128) not null,
  primary key (id)
);
comment on TABLE sys_meta is '元数据';
comment on COLUMN sys_meta.id is '元数据ID';
comment on COLUMN sys_meta.name is '元数据名称';
comment on COLUMN sys_meta.display_name is '元数据显示名';
comment on COLUMN sys_meta.desc is '描述';
comment on COLUMN sys_meta.is_valid is '是否有效';
comment on COLUMN sys_meta.sort_num is '排序号';
comment on COLUMN sys_meta.input_date is '录入时间';
comment on COLUMN sys_meta.ds_name is '数据源名称';

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
   desc                 varchar(1024),
   ref_id               varchar(32),
   is_valid             char(1) not null,
   input_date           datetime not null,
   sort_num             int not null,
   primary key (id)
);

comment on table sys_layout is '布局管理器';
comment on column sys_layout.id is '布局ID';
comment on column sys_layout.pid is '布局父ID';
comment on column sys_layout.name is '布局名称';
comment on column sys_layout.display_name is '布局显示名';
comment on column sys_layout.desc is '描述';
comment on column sys_layout.ref_id is '引用布局ID';
comment on column sys_layout.is_valid is '是否有效';
comment on column sys_layout.input_date is '录入时间';
comment on column sys_layout.sort_num is '排序号';


/*==============================================================*/
/* Table: sys_layout_prop                                       */
/*==============================================================*/
create table sys_layout_prop
(
   id                   varchar(32) not null,
   layout_type          varchar(32) not null,
   layout_id            varchar(32),
   name                 varchar(64) not null,
   display_name         varchar(128) not null,
   prop_type            char(2) not null,
   default_value        varchar(128),
   desc        varchar(1024),
   sort_num             int not null,
   primary key (id)
);

comment on table sys_layout_prop is '布局属性';
comment on column sys_layout_prop.layout_type is '布局类型';
comment on column sys_layout_prop.id is '布局属性ID';
comment on column sys_layout_prop.layout_id is '布局ID';
comment on column sys_layout_prop.name is '属性名称';
comment on column sys_layout_prop.display_name is '显示名';
comment on column sys_layout_prop.default_value is '默认值';
comment on column sys_layout_prop.prop_type is '属性类型';
comment on column sys_layout_prop.desc is '描述';
comment on column sys_layout_prop.sort_num is '排序号';

/*==============================================================*/
/* Table: sys_view                                              */
/*==============================================================*/
create table sys_view
(
   id                   varchar(32) not null,
   name                 varchar(64) not null,
   display_name         varchar(128),
   desc                 varchar(1024),
   meta_id              varchar(32) not null,
   is_valid             char(1) not null,
   input_date           datetime not null,
   sort_num             int not null,
   primary key (id)
);

comment on table sys_view is '视图';
comment on column sys_view.id is '视图ID';
comment on column sys_view.name is '视图名称';
comment on column sys_view.display_name is '视图ID';
comment on column sys_view.desc is '描述';
comment on column sys_view.meta_id is '元数据ID';
comment on column sys_view.is_valid is '是否有效';
comment on column sys_view.input_date is '录入时间';
comment on column sys_view.sort_num is '排序号';

/*==============================================================*/
/* Table: sys_view_layout                                       */
/*==============================================================*/
create table sys_view_layout
(
  id                   varchar(32) not null,
  view_id              varchar(32) not null,
  layout_id            varchar(32) not null,
  meta_id              varchar(32),
  primary key (id)
);

comment on table sys_view_layout is '视图布局';
comment on column sys_view_layout.id is '视图布局ID';
comment on column sys_view_layout.view_id is '视图ID';
comment on column sys_view_layout.layout_id is '布局ID';
comment on column sys_view_layout.meta_id is '元数据ID';

/*==============================================================*/
/* Table: sys_view_config                                       */
/*==============================================================*/
create table sys_view_config
(
   id                   varchar(32) not null,
   view_layout_id       varchar(32) not null,
   prop_id              varchar(32) not null,
   meta_field_id        varchar(32),
   value                varchar(128),
   primary key (id)
);

comment on table sys_view_config is '视图配置';
comment on column sys_view_config.id is '视图配置ID';
comment on column sys_view_config.view_layout_id is '视图布局ID';
comment on column sys_view_config.prop_id is '属性ID';
comment on column sys_view_config.meta_field_id is '元字段ID';
comment on column sys_view_config.value is '属性值';

/*==============================================================*/
/* Table: sys_view_prop                                         */
/*==============================================================*/
create table sys_view_prop
(
   id                   varchar(32) not null,
   view_id              varchar(32) not null,
   layout_prop_id       varchar(32) not null,
   meta_field_id        varchar(32),
   value                varchar(128),
   primary key (id)
);

comment on table sys_view_prop is '视图属性';
comment on column sys_view_prop.id is '视图属性ID';
comment on column sys_view_prop.view_id is '视图ID';
comment on column sys_view_prop.layout_prop_id is '布局属性ID';
comment on column sys_view_prop.meta_field_id is '元字段ID';
comment on column sys_view_prop.value is '属性值';

-- 约束
alter table sys_dz_code add constraint FK_code_categoryId foreign key (category_id)
      references sys_dz_category (id) on delete cascade on update cascade;

alter table sys_meta_field add constraint FK_meta_field_metaId foreign key (meta_id)
      references sys_meta (id) on delete cascade on update cascade;

alter table sys_view_layout add constraint FK_view_layout_viewId foreign key (view_id)
      references sys_view (id) on delete restrict on update restrict;

alter table sys_view_layout add constraint FK_view_layout_metaId foreign key (meta_id)
      references sys_meta (id) on delete restrict on update restrict;

alter table sys_view_config add constraint FK_view_config_layoutPropId foreign key (prop_id)
      references sys_layout_prop (id) on delete cascade on update cascade;

alter table sys_view_config add constraint FK_view_config_metaFieldId foreign key (meta_field_id)
      references sys_meta_field (id) on delete cascade on update cascade;

alter table sys_view add constraint FK_view_metaId foreign key (meta_id)
      references sys_meta (id) on delete cascade on update cascade;

alter table sys_view_prop add constraint FK_view_prop_viewId foreign key (view_id)
      references sys_view (id) on delete cascade on update cascade;

alter table sys_view_prop add constraint FK_view_prop_layoutPropId foreign key (layout_prop_id)
      references sys_layout_prop (id) on delete cascade on update cascade;

alter table sys_view_prop add constraint FK_view_prop_metaFieldId foreign key (meta_field_id)
      references sys_meta_field (id) on delete cascade on update cascade;

-- 索引
create unique index IUX_NAME on sys_layout
(
   name
);

create unique index idx_view_config_prop on sys_view_config
(
   view_layout_id,
   prop_id,
   meta_field_id
);

-- 插入升级语句
insert into sys_db_version (sys_name, db_version, input_date, memo) values('core', '1.0.0', CURDATE(), '系统自动升级到1.0.0');
