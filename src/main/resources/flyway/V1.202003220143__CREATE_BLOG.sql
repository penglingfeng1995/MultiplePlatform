create table t_blog
(
    id          bigint auto_increment,
    title       varchar(255) null,
    description varchar(255) null,
    content     text         null,
    refer_id    bigint       null,
    createdAt   datetime     null,
    updatedAt   datetime     null,
    constraint table_name_pk
        primary key (id)
);

