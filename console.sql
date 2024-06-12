create database celeste;

DROP TABLE IF EXISTS `user`;
create table user(
    uid      int         primary key auto_increment,
    sex      int         default 1,
    username varchar(64) not null unique ,
    password varchar(64) not null ,
    img      varchar(64) default '',
    phone    varchar(64) default '',
    email    varchar(64) default '',
    status   int         default 1,
    def_flag int         default 0
);

DROP TABLE IF EXISTS `article`;
create table article(
    aid         int         primary key auto_increment,
    uid         int         not null ,
    title       varchar(64) not null ,
    context     text        not null ,
    create_time datetime    default now(),
    update_time datetime    default now(),
    watch       int         default 0,
    like_       int         default 0,
    status      int         default 1 comment '1:public,2:private,vip:3',
    def_flag    int         default 0
);
select * from user;
insert into user (username, password)
values ('zsd','$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG');
# password :123456 $2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG
update user set password = '$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG';


select * from article;
insert into article (uid, title, context)
values (1,'title','hello,world');