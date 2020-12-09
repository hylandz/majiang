create table account
(
    id bigint(11) auto_increment primary key,
    user_name varchar(125) not null,
    password varchar(55) not null,
    email varchar(50),
    gmt_create bigint(11),
    gmt_modified bigint(11),
    phone varchar(13),
    gender int(1) default 1
);

comment on table account is '注册表';

comment on column account.gender is '1:男,2:女,0:保密';

