drop table if exists t_mp_user;
create table t_mp_user
(
    id        bigint auto_increment,
    username  varchar(255) not null,
    password  varchar(255) not null,
    avatar    varchar(255) null,
    createdAt varchar(255) null,
    updatedAt varchar(255) null,
    constraint t_mp_user_pk
        primary key (id)
)
    comment '用户表';
create unique index t_mp_user_username_uindex
    on t_mp_user (username);