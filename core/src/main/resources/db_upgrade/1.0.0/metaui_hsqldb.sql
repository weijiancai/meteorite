drop table if exists mu_code_tpl;
drop table if exists mu_db_datasource;
drop table if exists mu_db_mobile_number;
drop table if exists mu_db_version;
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
drop table if exists mu_view_config;
drop table if exists mu_view_prop;

drop index iux_dzCategory_name if exists;
drop index idx_view_config_prop if exists;

/*==============================================================*/
/* Table: mu_code_tpl                                      */
/*==============================================================*/
create table mu_code_tpl
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    description                      varchar(1024)   ,
    project_id                       varchar(32)     not null,
    file_name                        varchar(128)    not null,
    file_path                        varchar(256)    ,
    tpl_file                         varchar(1024)   ,
    tpl_content                      clob            ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_code_tpl is '代码模板';
comment on column mu_code_tpl.id is '模板ID';
comment on column mu_code_tpl.name is '模板名称';
comment on column mu_code_tpl.display_name is '显示名';
comment on column mu_code_tpl.description is '描述';
comment on column mu_code_tpl.project_id is '项目ID';
comment on column mu_code_tpl.file_name is '文件名';
comment on column mu_code_tpl.file_path is '文件路径';
comment on column mu_code_tpl.tpl_file is '模板文件';
comment on column mu_code_tpl.tpl_content is '模板内容';
comment on column mu_code_tpl.is_valid is '是否有效';
comment on column mu_code_tpl.sort_num is '排序号';
comment on column mu_code_tpl.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_db_datasource                                      */
/*==============================================================*/
create table mu_db_datasource
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(128)    ,
    type                             varchar(32)     not null,
    description                      varchar(1024)   ,
    url                              varchar(1024)   ,
    host                             varchar(64)     ,
    port                             int             ,
    user_name                        varchar(64)     ,
    pwd                              varchar(64)     ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_db_datasource is '数据源';
comment on column mu_db_datasource.name is '名称';
comment on column mu_db_datasource.display_name is '显示名';
comment on column mu_db_datasource.type is '类型';
comment on column mu_db_datasource.description is '描述';
comment on column mu_db_datasource.url is 'URL';
comment on column mu_db_datasource.host is '主机';
comment on column mu_db_datasource.port is '端口';
comment on column mu_db_datasource.user_name is '用户名';
comment on column mu_db_datasource.pwd is '密码';
comment on column mu_db_datasource.is_valid is '是否有效';
comment on column mu_db_datasource.sort_num is '排序号';
comment on column mu_db_datasource.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_db_mobile_number                                      */
/*==============================================================*/
create table mu_db_mobile_number
(
    code                             char(11)        not null,
    province                         varchar(64)     ,
    city                             varchar(64)     ,
    card_type                        varchar(64)     ,
    operators                        varchar(64)     ,
    code_segment                     varchar(11)     ,
    primary key (code)
);
comment on table mu_db_mobile_number is '手机号码';
comment on column mu_db_mobile_number.code is '手机号码';
comment on column mu_db_mobile_number.province is '省';
comment on column mu_db_mobile_number.city is '城市';
comment on column mu_db_mobile_number.card_type is '手机卡类型';
comment on column mu_db_mobile_number.operators is '运营商';
comment on column mu_db_mobile_number.code_segment is '号段';

/*==============================================================*/
/* Table: mu_db_version                                      */
/*==============================================================*/
create table mu_db_version
(
    sys_name                         varchar(64)     not null,
    db_version                       varchar(11)     ,
    input_date                       date            not null,
    memo                             varchar(1024)   ,
    primary key (sys_name)
);
comment on table mu_db_version is '系统版本信息';
comment on column mu_db_version.sys_name is '系统名称';
comment on column mu_db_version.db_version is '数据库版本';
comment on column mu_db_version.input_date is '发布日期';
comment on column mu_db_version.memo is '备注';

/*==============================================================*/
/* Table: mu_dz_category                                      */
/*==============================================================*/
create table mu_dz_category
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    description                      varchar(1024)   ,
    pid                              varchar(32)     ,
    db_table                         varchar(128)    ,
    table_id_col                     varchar(64)     ,
    table_name_col                   varchar(64)     ,
    sql_text                         varchar(1024)   ,
    is_system                        char(1)         not null,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_dz_category is '字典类别';
comment on column mu_dz_category.id is '类别ID';
comment on column mu_dz_category.name is '类别名称';
comment on column mu_dz_category.description is '描述';
comment on column mu_dz_category.pid is '父类别ID';
comment on column mu_dz_category.db_table is '数据库表';
comment on column mu_dz_category.table_id_col is '表ID列';
comment on column mu_dz_category.table_name_col is '表显示名列';
comment on column mu_dz_category.sql_text is 'SQL语句';
comment on column mu_dz_category.is_system is '是否系统内置';
comment on column mu_dz_category.is_valid is '是否有效';
comment on column mu_dz_category.sort_num is '排序号';
comment on column mu_dz_category.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_dz_code                                      */
/*==============================================================*/
create table mu_dz_code
(
    id                               varchar(32)     not null,
    category_id                      varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(128)    not null,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    primary key (id)
);
comment on table mu_dz_code is '字典代码';
comment on column mu_dz_code.id is '代码ID';
comment on column mu_dz_code.category_id is '类别ID';
comment on column mu_dz_code.name is '代码名称';
comment on column mu_dz_code.display_name is '代码显示名';
comment on column mu_dz_code.description is '描述';
comment on column mu_dz_code.is_valid is '是否有效';
comment on column mu_dz_code.input_date is '录入时间';
comment on column mu_dz_code.sort_num is '排序号';

/*==============================================================*/
/* Table: mu_layout                                      */
/*==============================================================*/
create table mu_layout
(
    id                               varchar(32)     not null,
    pid                              varchar(32)     ,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    description                      varchar(1024)   ,
    ref_id                           varchar(32)     ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    primary key (id)
);
comment on table mu_layout is '布局管理器';
comment on column mu_layout.id is '布局ID';
comment on column mu_layout.pid is '布局父ID';
comment on column mu_layout.name is '布局名称';
comment on column mu_layout.display_name is '布局显示名';
comment on column mu_layout.description is '描述';
comment on column mu_layout.ref_id is '引用布局ID';
comment on column mu_layout.is_valid is '是否有效';
comment on column mu_layout.input_date is '录入时间';
comment on column mu_layout.sort_num is '排序号';

/*==============================================================*/
/* Table: mu_layout_prop                                      */
/*==============================================================*/
create table mu_layout_prop
(
    id                               varchar(32)     not null,
    layout_type                      varchar(32)     not null,
    layout_id                        varchar(32)     ,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    default_value                    varchar(128)    ,
    prop_type                        char(2)         not null,
    description                      varchar(1024)   ,
    sort_num                         int             not null,
    primary key (id)
);
comment on table mu_layout_prop is '布局属性';
comment on column mu_layout_prop.id is '布局属性ID';
comment on column mu_layout_prop.layout_type is '布局类型';
comment on column mu_layout_prop.layout_id is '布局ID';
comment on column mu_layout_prop.name is '属性名称';
comment on column mu_layout_prop.display_name is '显示名';
comment on column mu_layout_prop.default_value is '默认值';
comment on column mu_layout_prop.prop_type is '属性类型';
comment on column mu_layout_prop.description is '描述';
comment on column mu_layout_prop.sort_num is '排序号';

/*==============================================================*/
/* Table: mu_meta                                      */
/*==============================================================*/
create table mu_meta
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(128)    ,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    rs_id                            varchar(128)    ,
    primary key (id)
);
comment on table mu_meta is '元数据';
comment on column mu_meta.id is '元数据ID';
comment on column mu_meta.name is '元数据名称';
comment on column mu_meta.display_name is '元数据显示名';
comment on column mu_meta.description is '描述';
comment on column mu_meta.is_valid is '是否有效';
comment on column mu_meta.input_date is '录入时间';
comment on column mu_meta.sort_num is '排序号';
comment on column mu_meta.rs_id is '资源ID';

/*==============================================================*/
/* Table: mu_meta_field                                      */
/*==============================================================*/
create table mu_meta_field
(
    id                               varchar(32)     not null,
    meta_id                          varchar(32)     not null,
    meta_item_id                     varchar(32)     ,
    name                             varchar(128)    not null,
    display_name                     varchar(64)     ,
    data_type                        varchar(64)     not null,
    dict_id                          varchar(32)     ,
    original_name                    varchar(128)    ,
    max_length                       int             ,
    is_pk                            char(1)         not null,
    is_fk                            char(1)         not null,
    is_require                       char(1)         not null,
    description                      varchar(1024)   ,
    default_value                    varchar(256)    ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_meta_field is '元字段信息';
comment on column mu_meta_field.id is '元数据字段ID';
comment on column mu_meta_field.meta_id is '元数据ID';
comment on column mu_meta_field.meta_item_id is '数据项ID';
comment on column mu_meta_field.name is '字段名称';
comment on column mu_meta_field.display_name is '显示名';
comment on column mu_meta_field.data_type is '数据类型';
comment on column mu_meta_field.dict_id is '数据字典';
comment on column mu_meta_field.original_name is '原名称';
comment on column mu_meta_field.max_length is '最大长度';
comment on column mu_meta_field.is_pk is '是否主键';
comment on column mu_meta_field.is_fk is '是否外键';
comment on column mu_meta_field.is_require is '是否必须';
comment on column mu_meta_field.description is '描述';
comment on column mu_meta_field.default_value is '默认值';
comment on column mu_meta_field.is_valid is '是否有效';
comment on column mu_meta_field.sort_num is '排序号';
comment on column mu_meta_field.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_meta_item                                      */
/*==============================================================*/
create table mu_meta_item
(
    id                               varchar(32)     not null,
    name                             varchar(128)    not null,
    display_name                     varchar(64)     not null,
    data_type                        varchar(64)     not null,
    category                         varchar(32)     ,
    max_length                       int             ,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_meta_item is '元数据项';
comment on column mu_meta_item.id is '数据项ID';
comment on column mu_meta_item.name is '名称';
comment on column mu_meta_item.display_name is '显示名';
comment on column mu_meta_item.data_type is '数据类型';
comment on column mu_meta_item.category is '分类';
comment on column mu_meta_item.max_length is '最大长度';
comment on column mu_meta_item.description is '描述';
comment on column mu_meta_item.is_valid is '是否有效';
comment on column mu_meta_item.sort_num is '排序号';
comment on column mu_meta_item.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_meta_obj                                      */
/*==============================================================*/
create table mu_meta_obj
(
    id                               varchar(32)     not null,
    meta_id                          varchar(32)     not null,
    primary key (id)
);
comment on table mu_meta_obj is '元数据对象';
comment on column mu_meta_obj.id is '对象ID';
comment on column mu_meta_obj.meta_id is '元数据ID';

/*==============================================================*/
/* Table: mu_meta_obj_value                                      */
/*==============================================================*/
create table mu_meta_obj_value
(
    meta_obj_id                      varchar(32)     not null,
    meta_field_id                    varchar(32)     not null,
    value                            varchar(1024)   
);
comment on table mu_meta_obj_value is '元对象值';
comment on column mu_meta_obj_value.meta_obj_id is '元对象ID';
comment on column mu_meta_obj_value.meta_field_id is '元字段ID';
comment on column mu_meta_obj_value.value is '元字段值';

/*==============================================================*/
/* Table: mu_meta_reference                                      */
/*==============================================================*/
create table mu_meta_reference
(
    id                               varchar(32)     not null,
    pk_meta_id                       varchar(32)     not null,
    pk_meta_field_id                 varchar(32)     not null,
    fk_meta_id                       varchar(32)     not null,
    fk_meta_field_id                 varchar(32)     not null,
    primary key (id)
);
comment on table mu_meta_reference is '元数据引用';
comment on column mu_meta_reference.id is '引用ID';
comment on column mu_meta_reference.pk_meta_id is '主元数据ID';
comment on column mu_meta_reference.pk_meta_field_id is '主元数据列ID';
comment on column mu_meta_reference.fk_meta_id is '引用元数据ID';
comment on column mu_meta_reference.fk_meta_field_id is '引用元数据列ID';

/*==============================================================*/
/* Table: mu_meta_sql                                      */
/*==============================================================*/
create table mu_meta_sql
(
    meta_id                          varchar(32)     not null,
    sql_text                         varchar(8000)   not null,
    primary key (meta_id)
);
comment on table mu_meta_sql is '元数据Sql语句';
comment on column mu_meta_sql.meta_id is '元数据ID';
comment on column mu_meta_sql.sql_text is 'Sql语句';

/*==============================================================*/
/* Table: mu_module                                      */
/*==============================================================*/
create table mu_module
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(64)     not null,
    pid                              varchar(32)     ,
    description                      varchar(1024)   ,
    view_id                          varchar(32)     ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_module is '系统模块';
comment on column mu_module.id is '模块ID';
comment on column mu_module.name is '名称';
comment on column mu_module.display_name is '显示名';
comment on column mu_module.pid is '父模块';
comment on column mu_module.description is '描述';
comment on column mu_module.view_id is '视图ID';
comment on column mu_module.is_valid is '是否有效';
comment on column mu_module.sort_num is '排序号';
comment on column mu_module.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_nav_menu                                      */
/*==============================================================*/
create table mu_nav_menu
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    not null,
    icon                             varchar(1024)   ,
    url                              varchar(1024)   ,
    pid                              varchar(32)     not null,
    level                            int             not null,
    project_id                       varchar(32)     not null,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_nav_menu is '导航菜单';
comment on column mu_nav_menu.id is '菜单ID';
comment on column mu_nav_menu.name is '菜单名称';
comment on column mu_nav_menu.display_name is '显示名';
comment on column mu_nav_menu.icon is '图标';
comment on column mu_nav_menu.url is 'URL';
comment on column mu_nav_menu.pid is '父菜单ID';
comment on column mu_nav_menu.level is '菜单级别';
comment on column mu_nav_menu.project_id is '项目ID';
comment on column mu_nav_menu.is_valid is '是否有效';
comment on column mu_nav_menu.sort_num is '排序号';
comment on column mu_nav_menu.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_pm_user                                      */
/*==============================================================*/
create table mu_pm_user
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    ,
    pwd                              varchar(64)     ,
    email                            varchar(64)     ,
    mobile_number                    varchar(64)     ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    primary key (id)
);
comment on table mu_pm_user is '用户信息';
comment on column mu_pm_user.name is '名称';
comment on column mu_pm_user.display_name is '显示名';
comment on column mu_pm_user.pwd is '密码';
comment on column mu_pm_user.email is '邮箱';
comment on column mu_pm_user.mobile_number is '手机号';
comment on column mu_pm_user.is_valid is '是否有效';
comment on column mu_pm_user.input_date is '录入时间';

/*==============================================================*/
/* Table: mu_profile_setting                                      */
/*==============================================================*/
create table mu_profile_setting
(
    conf_section                     varchar(64)     not null,
    conf_key                         varchar(64)     not null,
    conf_value                       varchar(128)    ,
    sort_num                         int             ,
    memo                             varchar(1024)   ,
    primary key (conf_section,conf_key)
);
comment on table mu_profile_setting is '参数配置';
comment on column mu_profile_setting.conf_section is '配置类型';
comment on column mu_profile_setting.conf_key is '属性名';
comment on column mu_profile_setting.conf_value is '属性值';
comment on column mu_profile_setting.sort_num is '排序号';
comment on column mu_profile_setting.memo is '备注';

/*==============================================================*/
/* Table: mu_project_define                                      */
/*==============================================================*/
create table mu_project_define
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(64)     not null,
    description                      varchar(1024)   ,
    package_name                     varchar(64)     ,
    is_valid                         char(1)         not null,
    sort_num                         int             not null,
    input_date                       date            not null,
    project_url                      varchar(1024)   ,
    primary key (id)
);
comment on table mu_project_define is '项目定义';
comment on column mu_project_define.id is '项目内码';
comment on column mu_project_define.name is '名称';
comment on column mu_project_define.display_name is '显示名';
comment on column mu_project_define.description is '描述';
comment on column mu_project_define.package_name is '包名';
comment on column mu_project_define.is_valid is '是否有效';
comment on column mu_project_define.sort_num is '排序号';
comment on column mu_project_define.input_date is '录入时间';
comment on column mu_project_define.project_url is '项目url';

/*==============================================================*/
/* Table: mu_view                                      */
/*==============================================================*/
create table mu_view
(
    id                               varchar(32)     not null,
    name                             varchar(64)     not null,
    display_name                     varchar(128)    ,
    description                      varchar(1024)   ,
    is_valid                         char(1)         not null,
    input_date                       date            not null,
    sort_num                         int             not null,
    meta_id                          varchar(32)     not null,
    primary key (id)
);
comment on table mu_view is '视图';
comment on column mu_view.id is '视图ID';
comment on column mu_view.name is '视图名称';
comment on column mu_view.display_name is '显示名';
comment on column mu_view.description is '描述';
comment on column mu_view.is_valid is '是否有效';
comment on column mu_view.input_date is '录入时间';
comment on column mu_view.sort_num is '排序号';
comment on column mu_view.meta_id is '元数据ID';

/*==============================================================*/
/* Table: mu_view_config                                      */
/*==============================================================*/
create table mu_view_config
(
    id                               varchar(32)     not null,
    prop_id                          varchar(32)     not null,
    meta_field_id                    varchar(32)     ,
    value                            varchar(128)    ,
    primary key (id)
);
comment on table mu_view_config is '视图配置';
comment on column mu_view_config.id is '视图配置ID';
comment on column mu_view_config.prop_id is '属性ID';
comment on column mu_view_config.meta_field_id is '元字段ID';
comment on column mu_view_config.value is '属性值';

/*==============================================================*/
/* Table: mu_view_prop                                      */
/*==============================================================*/
create table mu_view_prop
(
    id                               varchar(32)     not null,
    view_id                          varchar(32)     not null,
    layout_prop_id                   varchar(32)     not null,
    meta_field_id                    varchar(32)     ,
    value                            varchar(128)    ,
    primary key (id)
);
comment on table mu_view_prop is '视图属性';
comment on column mu_view_prop.id is '视图属性ID';
comment on column mu_view_prop.view_id is '视图ID';
comment on column mu_view_prop.layout_prop_id is '布局属性ID';
comment on column mu_view_prop.meta_field_id is '元字段ID';
comment on column mu_view_prop.value is '属性值';


alter table mu_code_tpl add constraint FK_code_tpl_projectId foreign key (project_id)
    references mu_project_define (id) on delete cascade on update cascade;

alter table mu_layout_prop add constraint FK_layout_prop_layoutId foreign key (layout_id)
    references mu_layout (id) on delete cascade on update cascade;

alter table mu_meta_field add constraint FK_metaField_metaItem foreign key (meta_item_id)
    references mu_meta_item (id) on delete cascade on update cascade;

alter table mu_meta_field add constraint FK_meta_field_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_obj add constraint FK_meta_obj_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_obj_value add constraint FK_meta_field_value_metaField foreign key (meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_obj_value add constraint FK_meta_obj_value_metaObjId foreign key (meta_obj_id)
    references mu_meta_obj (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_fkMetaFieldId foreign key (fk_meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_fkMetaId foreign key (fk_meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_pkMetaFieldId foreign key (pk_meta_field_id)
    references mu_meta_field (id) on delete cascade on update cascade;

alter table mu_meta_reference add constraint FK_meta_reference_pkMetaId foreign key (pk_meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_meta_sql add constraint FK_meta_sql_metaId foreign key (meta_id)
    references mu_meta (id) on delete cascade on update cascade;

alter table mu_module add constraint FK_module_viewId foreign key (view_id)
    references mu_view (id) on delete cascade on update cascade;

alter table mu_nav_menu add constraint FK_nav_menu_projectId foreign key (project_id)
    references mu_project_define (id) on delete cascade on update cascade;

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


create  index iux_dzCategory_name on mu_dz_category
(
    name
);
create  index idx_view_config_prop on mu_view_config
(
    meta_field_id,
    prop_id
);
