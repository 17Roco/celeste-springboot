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

DROP TABLE if exists `tag`;
create table `tag`(
    tid			int         primary key auto_increment,
    title		varchar(64) not null unique ,
    info		varchar(64) default '',
    num         int         default 0
);
DROP TABLE if exists `link_aid_tid_tags`;
create table `link_aid_tid`(
    aid     int,
    tid     int,
    primary key (aid,tid)
);


select * from user;
insert into user (username, password)
values ('zsd','$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG');
# password :123456 $2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG
update user set password = '$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG';


select * from article;
select create_time from article group by create_time ;
insert into article (uid, title, context)
values (1,'title','hello,world');

select * from `tag`;
insert into tag (title) values
('C/C++'),('Java'),('Python'),('Web'),('Vue'),('SpringBoot'),('go'),('kotlin'),('android'),('SQL'),('AI'),('C#'),('JavaScript'),('html'),('css'),('nodeJS'),('PHP'),('Linux'),('docker'),('IOS'),('windows'),('Unity'),('UE5'),('TypeScript'),('pinia'),('git'),('Qt'),('redis');


select * from `link_aid_tid`;
select tid from link_aid_tid where aid = 1;
delete from link_aid_tid;
insert into link_aid_tid values (1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(2,2),(2,12),(2,22),(2,32),(2,42);
