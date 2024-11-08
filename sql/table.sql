# create database celeste;


# user          用户
# article       文章
# tag           标签
# link_user_follow 用户关注表
# link_article_like 文章点赞表
# link_article_tag  文章-标签连接表

# select * from user;
# select * from article;
# select * from `link_aid_tid`;
# select * from `tag`;

# ----------------------------------------------------------------------------------------------------------------------
# user
# article
# tag
# comment
DROP TABLE IF EXISTS `user`;
create table user(
    uid      int          primary key auto_increment,
    sex      int          default 1         comment '性别',
    username varchar(64)  not null unique   comment '用户名',
    birthday date         default null      comment '生日',
    sign     varchar(200) default ''        comment '个性签名',
    password varchar(64)  not null          default '$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG',
    img      varchar(64)  not null          default '/static/logo.svg',
    phone    varchar(64)  default null      unique ,
    email    varchar(64)  default null      unique ,
    status   int          default 1         comment '1:正常，2:封禁',
    follow   int          default 0,
    followed int          default 0,
    def_flag int          default 0
);

DROP TABLE IF EXISTS `article`;
create table article(
    aid         int         primary key auto_increment,
    uid         int         not null ,
    title       varchar(64) not null ,
    context     text        not null ,
    img         varchar(64) default '',
    create_time datetime    default now(),
    update_time datetime    default now(),
    watch       int         default 1000,
    likee       int         default 1000,
    status      int         default 1 comment '1:public,2:private',
    def_flag    int         default 0
);

DROP TABLE if exists `tag`;
create table `tag`(
    tid			int         primary key auto_increment,
    title		varchar(64) not null unique ,
    info		varchar(64) default '',
    num         int         default 0
);

DROP TABLE if exists `comment`;
create table `comment`(
    cid     int         primary      key auto_increment,
    pid     int         default 0    comment '父id',
    type    int         default 1    comment '类型，0：children，1：article',
    uid     int         not null     ,
    text    text        not null     ,
    likee   int         default 0    ,
    time    datetime    default now(),
    def_flag    int     default 0
);

# ----------------------------------------------------------------------------------------------------------------------
# link_user_follow
# link_article_like
# link_article_tag
#
DROP TABLE if exists `link_user_follow`;
create table `link_user_follow`(
    id     int,
    uid     int,
    primary key (id,uid)
);
DROP TABLE if exists `link_article_like`;
create table `link_article_like`(
                                    aid     int,
                                    uid     int,
                                    primary key (aid,uid)
);
DROP TABLE if exists link_article_tag;
create table `link_article_tag`(
    aid     int,
    tid     int,
    primary key (aid,tid)
);
