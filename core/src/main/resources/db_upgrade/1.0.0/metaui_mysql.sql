/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/10/5 0:09:36                            */
/*==============================================================*/


drop table if exists mu_code_tpl;

drop table if exists mu_db_datasource;

drop table if exists mu_db_mobile_number;

drop table if exists mu_db_version;

drop index iux_dzCategory_name on mu_dz_category;

drop table if exists mu_dz_category;

drop table if exists mu_dz_code;

drop table if exists mu_layout;

drop table if exists mu_layout_prop;

drop table if exists mu_meta;

drop table if exists mu_meta_field;

drop table if exists mu_meta_item;

drop table if exists mu_meta_obj;

drop table if exists mu_meta_obj_value;

drop table if exists mu_meta_reference;

drop table if exists mu_meta_sql;

drop table if exists mu_module;

drop table if exists mu_nav_menu;

drop table if exists mu_pm_user;

drop table if exists mu_profile_setting;

drop table if exists mu_project_define;

drop table if exists mu_view;

drop index idx_view_config_prop on mu_view_config;

drop table if exists mu_view_config;

drop table if exists mu_view_prop;

/*==============================================================*/
/* Table: mu_code_tpl                                           */
/*==============================================================*/
create table mu_code_tpl
(
   id                   varchar(32) not null comment '模板ID',
   name                 varchar(64) not null comment '模板名称',
   display_name         varchar(128) not null comment '显示名',
   description          varchar(1024) comment '描述',
   project_id           varchar(32) not null comment '项目ID',
   file_name            varchar(128) not null comment '文件名',
   file_path            varchar(256) comment '文件路径',
   tpl_content          text not null comment '模板内容',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_code_tpl comment '代码模板';

/*==============================================================*/
/* Table: mu_db_datasource                                      */
/*==============================================================*/
create table mu_db_datasource
(
   id                   varchar(32) not null,
   name                 varchar(128) not null comment '名称',
   display_name         varchar(128) comment '显示名',
   type                 varchar(32) not null comment '类型',
   description          varchar(1024) comment '描述',
   url                  varchar(1024) comment 'URL',
   host                 varchar(64) comment '主机',
   port                 int comment '端口',
   user_name            varchar(64) comment '用户名',
   pwd                  varchar(64) comment '密码',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_db_datasource comment '数据源';

/*==============================================================*/
/* Table: mu_db_mobile_number                                   */
/*==============================================================*/
create table mu_db_mobile_number
(
   code                 char(11) not null comment '手机号码',
   province             varchar(64) comment '省',
   city                 varchar(64) comment '城市',
   card_type            varchar(64) comment '手机卡类型',
   operators            varchar(64) comment '运营商',
   code_segment         varchar(11) comment '号段',
   primary key (code)
);

alter table mu_db_mobile_number comment '手机号码';

/*==============================================================*/
/* Table: mu_db_version                                         */
/*==============================================================*/
create table mu_db_version
(
   sys_name             varchar(64) not null comment '系统名称',
   db_version           varchar(11) comment '数据库版本',
   input_date           datetime not null comment '发布日期',
   memo                 varchar(1024) comment '备注',
   primary key (sys_name)
);

alter table mu_db_version comment '系统版本信息';

/*==============================================================*/
/* Table: mu_dz_category                                        */
/*==============================================================*/
create table mu_dz_category
(
   id                   varchar(32) not null comment '类别ID',
   name                 varchar(64) not null comment '类别名称',
   description          varchar(1024) comment '描述',
   pid                  varchar(32) comment '父类别ID',
   is_system            char(1) not null comment '是否系统内置',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_dz_category comment '字典类别';

/*==============================================================*/
/* Index: iux_dzCategory_name                                   */
/*==============================================================*/
create unique index iux_dzCategory_name on mu_dz_category
(
   name
);

/*==============================================================*/
/* Table: mu_dz_code                                            */
/*==============================================================*/
create table mu_dz_code
(
   id                   varchar(32) not null comment '代码ID',
   category_id          varchar(32) not null comment '类别ID',
   name                 varchar(128) not null comment '代码名称',
   display_name         varchar(128) not null comment '代码显示名',
   description          varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   primary key (id)
);

alter table mu_dz_code comment '字典代码';

/*==============================================================*/
/* Table: mu_layout                                             */
/*==============================================================*/
create table mu_layout
(
   id                   varchar(32) not null comment '布局ID',
   pid                  varchar(32) comment '布局父ID',
   name                 varchar(64) not null comment '布局名称',
   display_name         varchar(128) not null comment '布局显示名',
   description          varchar(1024) comment '描述',
   ref_id               varchar(32) comment '引用布局ID',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   primary key (id)
);

alter table mu_layout comment '布局管理器';

/*==============================================================*/
/* Table: mu_layout_prop                                        */
/*==============================================================*/
create table mu_layout_prop
(
   id                   varchar(32) not null comment '布局属性ID',
   layout_type          varchar(32) not null comment '布局类型',
   layout_id            varchar(32) comment '布局ID',
   name                 varchar(64) not null comment '属性名称',
   display_name         varchar(128) not null comment '显示名',
   default_value        varchar(128) comment '默认值',
   prop_type            char(2) not null comment '属性类型',
   description          varchar(1024) comment '描述',
   sort_num             int not null comment '排序号',
   primary key (id)
);

alter table mu_layout_prop comment '布局属性';

/*==============================================================*/
/* Table: mu_meta                                               */
/*==============================================================*/
create table mu_meta
(
   id                   varchar(32) not null comment '元数据ID',
   name                 varchar(128) not null comment '元数据名称',
   display_name         varchar(128) comment '元数据显示名',
   description          varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   rs_id                varchar(128) comment '资源ID',
   primary key (id)
);

alter table mu_meta comment '元数据';

/*==============================================================*/
/* Table: mu_meta_field                                         */
/*==============================================================*/
create table mu_meta_field
(
   id                   varchar(32) not null comment '元数据字段ID',
   meta_id              varchar(32) not null comment '元数据ID',
   meta_item_id         varchar(32) comment '数据项ID',
   name                 varchar(128) not null comment '字段名称',
   display_name         varchar(64) comment '显示名',
   data_type            varchar(64) not null comment '数据类型',
   dict_id              varchar(32) comment '数据字典',
   original_name        varchar(128) comment '原名称',
   max_length           int comment '最大长度',
   is_pk                char(1) not null comment '是否主键',
   is_fk                char(1) not null comment '是否外键',
   is_require           char(1) not null comment '是否必须',
   description          varchar(1024) comment '描述',
   default_value        varchar(256) comment '默认值',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_meta_field comment '元字段信息';

/*==============================================================*/
/* Table: mu_meta_item                                          */
/*==============================================================*/
create table mu_meta_item
(
   id                   varchar(32) not null comment '数据项ID',
   name                 varchar(128) not null comment '名称',
   display_name         varchar(64) not null comment '显示名',
   data_type            varchar(64) not null comment '数据类型',
   category             varchar(32) comment '分类',
   max_length           int comment '最大长度',
   description          varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_meta_item comment '元数据项';

/*==============================================================*/
/* Table: mu_meta_obj                                           */
/*==============================================================*/
create table mu_meta_obj
(
   id                   varchar(32) not null comment '对象ID',
   meta_id              varchar(32) not null comment '元数据ID',
   primary key (id)
);

alter table mu_meta_obj comment '元数据对象';

/*==============================================================*/
/* Table: mu_meta_obj_value                                     */
/*==============================================================*/
create table mu_meta_obj_value
(
   meta_obj_id          varchar(32) not null comment '元对象ID',
   meta_field_id        varchar(32) not null comment '元字段ID',
   value                varchar(1024) comment '元字段值'
);

alter table mu_meta_obj_value comment '元对象值';

/*==============================================================*/
/* Table: mu_meta_reference                                     */
/*==============================================================*/
create table mu_meta_reference
(
   id                   varchar(32) not null comment '引用ID',
   pk_meta_id           varchar(32) not null comment '主元数据ID',
   pk_meta_field_id     varchar(32) not null comment '主元数据列ID',
   fk_meta_id           varchar(32) not null comment '引用元数据ID',
   fk_meta_field_id     varchar(32) not null comment '引用元数据列ID',
   primary key (id)
);

alter table mu_meta_reference comment '元数据引用';

/*==============================================================*/
/* Table: mu_meta_sql                                           */
/*==============================================================*/
create table mu_meta_sql
(
   meta_id              varchar(32) not null comment '元数据ID',
   sql_text             varchar(8000) not null comment 'Sql语句',
   primary key (meta_id)
);

alter table mu_meta_sql comment '元数据Sql语句';

/*==============================================================*/
/* Table: mu_module                                             */
/*==============================================================*/
create table mu_module
(
   id                   varchar(32) not null comment '模块ID',
   name                 varchar(64) not null comment '名称',
   display_name         varchar(64) not null comment '显示名',
   pid                  varchar(32) comment '父模块',
   description          varchar(1024) comment '描述',
   view_id              varchar(32) comment '视图ID',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_module comment '系统模块';

/*==============================================================*/
/* Table: mu_nav_menu                                           */
/*==============================================================*/
create table mu_nav_menu
(
   id                   varchar(32) not null comment '菜单ID',
   name                 varchar(64) not null comment '菜单名称',
   display_name         varchar(128) not null comment '显示名',
   icon                 varchar(1024) comment '图标',
   url                  varchar(1024) comment 'URL',
   pid                  varchar(32) not null comment '父菜单ID',
   level                int not null comment '菜单级别',
   project_id           varchar(32) not null comment '项目ID',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_nav_menu comment '导航菜单';

/*==============================================================*/
/* Table: mu_pm_user                                            */
/*==============================================================*/
create table mu_pm_user
(
   id                   varchar(32) not null,
   name                 varchar(64) not null comment '名称',
   display_name         varchar(128) comment '显示名',
   pwd                  varchar(64) comment '密码',
   email                varchar(64) comment '邮箱',
   mobile_number        varchar(64) comment '手机号',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table mu_pm_user comment '用户信息';

/*==============================================================*/
/* Table: mu_profile_setting                                    */
/*==============================================================*/
create table mu_profile_setting
(
   conf_section         varchar(64) not null comment '配置类型',
   conf_key             varchar(64) not null comment '属性名',
   conf_value           varchar(128) comment '属性值',
   sort_num             int comment '排序号',
   memo                 varchar(1024) comment '备注',
   primary key (conf_section, conf_key)
);

alter table mu_profile_setting comment '参数配置';

/*==============================================================*/
/* Table: mu_project_define                                     */
/*==============================================================*/
create table mu_project_define
(
   id                   varchar(32) not null comment '项目内码',
   name                 varchar(64) not null comment '名称',
   display_name         varchar(64) not null comment '显示名',
   description          varchar(1024) comment '描述',
   package_name         varchar(64) comment '包名',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int(11) not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   project_url          varchar(1024) comment '项目url',
   primary key (id)
);

alter table mu_project_define comment '项目定义';

/*==============================================================*/
/* Table: mu_view                                               */
/*==============================================================*/
create table mu_view
(
   id                   varchar(32) not null comment '视图ID',
   name                 varchar(64) not null comment '视图名称',
   display_name         varchar(128) comment '显示名',
   description          varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   meta_id              varchar(32) not null comment '元数据ID',
   primary key (id)
);

alter table mu_view comment '视图';

/*==============================================================*/
/* Table: mu_view_config                                        */
/*==============================================================*/
create table mu_view_config
(
   id                   varchar(32) not null comment '视图配置ID',
   prop_id              varchar(32) not null comment '属性ID',
   meta_field_id        varchar(32) comment '元字段ID',
   value                varchar(128) comment '属性值',
   primary key (id)
);

alter table mu_view_config comment '视图配置';

/*==============================================================*/
/* Index: idx_view_config_prop                                  */
/*==============================================================*/
create unique index idx_view_config_prop on mu_view_config
(
   prop_id,
   meta_field_id
);

/*==============================================================*/
/* Table: mu_view_prop                                          */
/*==============================================================*/
create table mu_view_prop
(
   id                   varchar(32) not null comment '视图属性ID',
   view_id              varchar(32) not null comment '视图ID',
   layout_prop_id       varchar(32) not null comment '布局属性ID',
   meta_field_id        varchar(32) comment '元字段ID',
   value                varchar(128) comment '属性值',
   primary key (id)
);

alter table mu_view_prop comment '视图属性';

alter table mu_code_tpl add constraint FK_code_tpl_projectId foreign key (project_id)
      references mu_project_define (id) on delete cascade on update cascade;

alter table mu_dz_code add constraint FK_code_categoryId foreign key (category_id)
      references mu_dz_category (id) on delete cascade on update cascade;

alter table mu_layout_prop add constraint FK_layout_prop_layoutId foreign key (layout_id)
      references mu_layout (id) on delete cascade on update cascade;

alter table mu_meta_field add constraint FK_metaField_metaItem foreign key (meta_item_id)
      references mu_meta_item (id) on delete restrict on update restrict;

alter table mu_meta_field add constraint FK_meta_field_metaId foreign key (meta_id)
      references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_obj add constraint FK_meta_obj_metaId foreign key (meta_id)
      references mu_meta (id) on delete restrict on update restrict;

alter table mu_meta_obj_value add constraint FK_meta_field_value_metaField foreign key (meta_field_id)
      references mu_meta_field (id) on delete restrict on update restrict;

alter table mu_meta_obj_value add constraint FK_meta_obj_value_metaObjId foreign key (meta_obj_id)
      references mu_meta_obj (id) on delete restrict on update restrict;

alter table mu_meta_reference add constraint FK_meta_reference_fkMetaFieldId foreign key (fk_meta_field_id)
      references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_fkMetaId foreign key (fk_meta_id)
      references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_pkMetaFieldId foreign key (pk_meta_field_id)
      references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_pkMetaId foreign key (pk_meta_id)
      references mu_meta (id) on delete restrict on update restrict;

alter table mu_meta_sql add constraint FK_meta_sql_metaId foreign key (meta_id)
      references mu_meta (id) on delete cascade on update cascade;

alter table mu_module add constraint FK_module_viewId foreign key (view_id)
      references mu_view (id) on delete restrict on update restrict;

alter table mu_nav_menu add constraint FK_nav_menu_projectId foreign key (project_id)
      references mu_project_define (id) on delete restrict on update restrict;

alter table mu_view add constraint FK_view_metaId foreign key (meta_id)
      references mu_meta (id) on delete cascade on update cascade;

alter table mu_view_config add constraint FK_view_config_layoutPropId foreign key (prop_id)
      references mu_layout_prop (id) on delete cascade on update cascade;

alter table mu_view_config add constraint FK_view_config_metaFieldId foreign key (meta_field_id)
      references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_view_prop add constraint FK_view_prop_layoutPropId foreign key (layout_prop_id)
      references mu_layout_prop (id) on delete cascade on update cascade;

alter table mu_view_prop add constraint FK_view_prop_metaFieldId foreign key (meta_field_id)
      references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_view_prop add constraint FK_view_prop_viewId foreign key (view_id)
      references mu_view (id) on delete cascade on update cascade;

