drop table if exists tb_itemcats;

/*==============================================================*/
/* Table: tb_itemcats                                           */
/*==============================================================*/
create table tb_itemcats
(
   cid                  int                            not null,
   parent_cid           int                            null,
   name                 varchar(254)                   null,
   is_parent            char(1)                        null,
   status               varchar(20)                    null,
   sort_order           int                            null,
   constraint PK_TB_ITEMCATS primary key (cid)
);

comment on table tb_itemcats is
'淘宝商品类目结构 ';

comment on column tb_itemcats.cid is
'商品所属类目ID';

comment on column tb_itemcats.parent_cid is
'父类目ID=0时，代表的是一级的类目';

comment on column tb_itemcats.name is
'类目名称';

comment on column tb_itemcats.is_parent is
'该类目是否为父类目(即：该类目是否还有子类目)';

comment on column tb_itemcats.status is
'状态。可选值:normal(正常),deleted(删除)';

comment on column tb_itemcats.sort_order is
'列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数';
