/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/8/21 22:52:19                           */
/*==============================================================*/


drop table if exists sys_db_mobile_number;

drop table if exists sys_db_version;

call del_idx('sys_dz_category','iux_name');

drop table if exists sys_dz_category;

drop table if exists sys_dz_code;

call del_idx('sys_layout','IUX_NAME');

drop table if exists sys_layout;

drop table if exists sys_layout_prop;

drop table if exists sys_meta;

drop table if exists sys_meta_field;

drop table if exists sys_meta_obj;

drop table if exists sys_meta_obj_value;

drop table if exists sys_meta_reference;

drop table if exists sys_module;

drop table if exists sys_nav_menu;

drop table if exists sys_view;

call del_idx('sys_view_config','idx_view_config_prop');

drop table if exists sys_view_config;

drop table if exists sys_view_prop;

/*==============================================================*/
/* Table: sys_db_mobile_number                                  */
/*==============================================================*/
create table sys_db_mobile_number
(
   code                 char(11) not null comment '手机号码',
   province             varchar(64) comment '省',
   city                 varchar(64) comment '城市',
   card_type            varchar(64) comment '手机卡类型',
   operators            varchar(64) comment '运营商',
   code_segment         varchar(11) comment '号段',
   primary key (code)
);

alter table sys_db_mobile_number comment '手机号码';

/*==============================================================*/
/* Table: sys_db_version                                        */
/*==============================================================*/
create table sys_db_version
(
   sys_name             varchar(64) not null comment '系统名称',
   db_version           varchar(11) comment '数据库版本',
   input_date           datetime not null comment '发布日期',
   memo                 varchar(1024) comment '备注',
   primary key (sys_name)
);

alter table sys_db_version comment '系统版本信息';

/*==============================================================*/
/* Table: sys_dz_category                                       */
/*==============================================================*/
create table sys_dz_category
(
   id                   varchar(32) not null comment '类别ID',
   name                 varchar(64) not null comment '类别名称',
   `desc`               varchar(1024) comment '描述',
   is_system            char(1) not null comment '是否系统内置',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table sys_dz_category comment '字典类别';

/*==============================================================*/
/* Index: iux_name                                              */
/*==============================================================*/
create unique index iux_name on sys_dz_category
(
   name
);

/*==============================================================*/
/* Table: sys_dz_code                                           */
/*==============================================================*/
create table sys_dz_code
(
   id                   varchar(32) not null comment '代码ID',
   category_id          varchar(32) not null comment '类别ID',
   name                 varchar(128) not null comment '代码名称',
   display_name         varchar(128) not null comment '代码显示名',
   `desc`               varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   primary key (id)
);

alter table sys_dz_code comment '字典代码';

/*==============================================================*/
/* Table: sys_layout                                            */
/*==============================================================*/
create table sys_layout
(
   id                   varchar(32) not null comment '布局ID',
   pid                  varchar(32) comment '布局父ID',
   name                 varchar(64) not null comment '布局名称',
   display_name         varchar(128) not null comment '布局显示名',
   `desc`               varchar(1024) comment '描述',
   ref_id               varchar(32) comment '引用布局ID',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   primary key (id)
);

alter table sys_layout comment '布局管理器';

/*==============================================================*/
/* Index: IUX_NAME                                              */
/*==============================================================*/
create unique index IUX_NAME on sys_layout
(
   name
);

/*==============================================================*/
/* Table: sys_layout_prop                                       */
/*==============================================================*/
create table sys_layout_prop
(
   id                   varchar(32) not null comment '布局属性ID',
   layout_type          varchar(32) not null comment '布局类型',
   layout_id            varchar(32) not null comment '布局ID',
   name                 varchar(64) not null comment '属性名称',
   display_name         varchar(128) not null comment '显示名',
   default_value        varchar(128) comment '默认值',
   prop_type            char(2) not null comment '属性类型',
   `desc`               varchar(1024) comment '描述',
   sort_num             int not null comment '排序号',
   primary key (id)
);

alter table sys_layout_prop comment '布局属性';

/*==============================================================*/
/* Table: sys_meta                                              */
/*==============================================================*/
create table sys_meta
(
   id                   varchar(32) not null comment '元数据ID',
   name                 varchar(128) not null comment '元数据名称',
   display_name         varchar(128) comment '元数据显示名',
   `desc`               varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   ds_name              varchar(128) not null comment '数据源名称',
   primary key (id)
);

alter table sys_meta comment '元数据';

/*==============================================================*/
/* Table: sys_meta_field                                        */
/*==============================================================*/
create table sys_meta_field
(
   id                   varchar(32) not null comment '元数据字段ID',
   meta_id              varchar(32) not null comment '元数据ID',
   name                 varchar(128) not null comment '字段名称',
   display_name         varchar(64) comment '显示名',
   data_type            varchar(64) not null comment '数据类型',
   `desc`               varchar(1024) comment '描述',
   default_value        varchar(256) not null comment '默认值',
   dict_id              varchar(32) comment '数据字典',
   db_column            varchar(128) comment '对应数据库列',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table sys_meta_field comment '元字段信息';

/*==============================================================*/
/* Table: sys_meta_obj                                          */
/*==============================================================*/
create table sys_meta_obj
(
   id                   varchar(32) not null comment '对象ID',
   meta_id              varchar(32) not null comment '元数据ID',
   primary key (id)
);

alter table sys_meta_obj comment '元数据对象';

/*==============================================================*/
/* Table: sys_meta_obj_value                                    */
/*==============================================================*/
create table sys_meta_obj_value
(
   meta_obj_id          varchar(32) not null comment '元对象ID',
   meta_field_id        varchar(32) not null comment '元字段ID',
   value                varchar(1024) comment '元字段值'
);

alter table sys_meta_obj_value comment '元对象值';

/*==============================================================*/
/* Table: sys_meta_reference                                    */
/*==============================================================*/
create table sys_meta_reference
(
   id                   varchar(32) not null comment '引用ID',
   pk_meta_id           varchar(32) not null comment '主元数据ID',
   pk_meta_field_id     varchar(32) not null comment '主元数据列ID',
   fk_meta_id           varchar(32) not null comment '引用元数据ID',
   fk_meta_field_id     varchar(32) not null comment '引用元数据列ID',
   primary key (id)
);

alter table sys_meta_reference comment '元数据引用';

/*==============================================================*/
/* Table: sys_module                                            */
/*==============================================================*/
create table sys_module
(
   id                   varchar(32) not null comment '模块ID',
   name                 varchar(64) not null comment '名称',
   display_name         varchar(64) not null comment '显示名',
   pid                  varchar(32) comment '父模块',
   `desc`               varchar(1024) comment '描述',
   view_id              varchar(32) comment '视图ID',
   is_valid             char(1) not null comment '是否有效',
   sort_num             int not null comment '排序号',
   input_date           datetime not null comment '录入时间',
   primary key (id)
);

alter table sys_module comment '系统模块';

/*==============================================================*/
/* Table: sys_nav_menu                                          */
/*==============================================================*/
create table sys_nav_menu
(
   id                   varchar(32) not null comment '菜单ID',
   name                 varchar(64) not null comment '菜单名称',
   diaplay_name         varchar(128) not null comment '显示名',
   icon                 varchar(1024) comment '图标',
   url                  varchar(1024) comment 'URL',
   pid                  varchar(32) not null comment '父菜单ID',
   level                int not null comment '菜单级别',
   primary key (id)
);

alter table sys_nav_menu comment '导航菜单';

/*==============================================================*/
/* Table: sys_view                                              */
/*==============================================================*/
create table sys_view
(
   id                   varchar(32) not null comment '视图ID',
   name                 varchar(64) not null comment '视图名称',
   display_name         varchar(128) comment '显示名',
   `desc`               varchar(1024) comment '描述',
   is_valid             char(1) not null comment '是否有效',
   input_date           datetime not null comment '录入时间',
   sort_num             int not null comment '排序号',
   meta_id              varchar(32) not null comment '元数据ID',
   primary key (id)
);

alter table sys_view comment '视图';

/*==============================================================*/
/* Table: sys_view_config                                       */
/*==============================================================*/
create table sys_view_config
(
   id                   varchar(32) not null comment '视图配置ID',
   prop_id              varchar(32) not null comment '属性ID',
   meta_field_id        varchar(32) comment '元字段ID',
   value                varchar(128) comment '属性值',
   primary key (id)
);

alter table sys_view_config comment '视图配置';

/*==============================================================*/
/* Index: idx_view_config_prop                                  */
/*==============================================================*/
create unique index idx_view_config_prop on sys_view_config
(
   prop_id,
   meta_field_id
);

/*==============================================================*/
/* Table: sys_view_prop                                         */
/*==============================================================*/
create table sys_view_prop
(
   id                   varchar(32) not null comment '视图属性ID',
   view_id              varchar(32) not null comment '视图ID',
   layout_prop_id       varchar(32) not null comment '布局属性ID',
   meta_field_id        varchar(32) comment '元字段ID',
   value                varchar(128) comment '属性值',
   primary key (id)
);

alter table sys_view_prop comment '视图属性';

alter table sys_dz_code add constraint FK_code_categoryId foreign key (category_id)
      references sys_dz_category (id) on delete cascade on update cascade;

alter table sys_layout_prop add constraint FK_layout_prop_layoutId foreign key (layout_id)
      references sys_layout (id) on delete cascade on update cascade;

alter table sys_meta_field add constraint FK_meta_field_metaId foreign key (meta_id)
      references sys_meta (id) on delete cascade on update cascade;

alter table sys_meta_obj add constraint FK_meta_obj_metaId foreign key (meta_id)
      references sys_meta (id) on delete restrict on update restrict;

alter table sys_meta_obj_value add constraint FK_meta_field_value_metaField foreign key (meta_field_id)
      references sys_meta_field (id) on delete restrict on update restrict;

alter table sys_meta_obj_value add constraint FK_meta_obj_value_metaObjId foreign key (meta_obj_id)
      references sys_meta_obj (id) on delete restrict on update restrict;

alter table sys_meta_reference add constraint FK_meta_reference_fkMetaFieldId foreign key (fk_meta_field_id)
      references sys_meta_field (id) on delete cascade on update cascade;

alter table sys_meta_reference add constraint FK_meta_reference_fkMetaId foreign key (fk_meta_id)
      references sys_meta (id) on delete cascade on update cascade;

alter table sys_meta_reference add constraint FK_meta_reference_pkMetaFieldId foreign key (pk_meta_field_id)
      references sys_meta_field (id) on delete cascade on update cascade;

alter table sys_meta_reference add constraint FK_meta_reference_pkMetaId foreign key (pk_meta_id)
      references sys_meta (id) on delete restrict on update restrict;

alter table sys_module add constraint FK_module_viewId foreign key (view_id)
      references sys_view (id) on delete restrict on update restrict;

alter table sys_view add constraint FK_view_metaId foreign key (meta_id)
      references sys_meta (id) on delete cascade on update cascade;

alter table sys_view_config add constraint FK_view_config_layoutPropId foreign key (prop_id)
      references sys_layout_prop (id) on delete cascade on update cascade;

alter table sys_view_config add constraint FK_view_config_metaFieldId foreign key (meta_field_id)
      references sys_meta_field (id) on delete cascade on update cascade;

alter table sys_view_prop add constraint FK_view_prop_layoutPropId foreign key (layout_prop_id)
      references sys_layout_prop (id) on delete cascade on update cascade;

alter table sys_view_prop add constraint FK_view_prop_metaFieldId foreign key (meta_field_id)
      references sys_meta_field (id) on delete cascade on update cascade;

alter table sys_view_prop add constraint FK_view_prop_viewId foreign key (view_id)
      references sys_view (id) on delete cascade on update cascade;

