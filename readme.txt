# mysql
grant all privileges on *.* to 'root'@'localhost' identified by '11111111' with grant option;
grant all privileges on *.* to 'root'@'%' identified by '11111111' with grant option;
flush privileges;

show variables like '%character%';
set character_set_client=utf8;
set character_set_connection=utf8;
set character_set_database=utf8;
set character_set_results=utf8;
set character_set_server=utf8;

# 数据库
drop database if exists shiro;
create database if not exists shiro default character set utf8 collate utf8_general_ci;

# 用户表
drop table if exists shiro.t_user;
create table if not exists shiro.t_user
(
    uid bigint unsigned not null auto_increment primary key,
    username varchar(256) not null,
    password char(64) not null comment 'Hashed by SHA256'
)engine=innodb default charset=utf8;
create index uuid on shiro.t_user(uid);
describe shiro.t_user;

# 角色表
drop table if exists shiro.t_role;
create table if not exists shiro.t_role
(
    rid bigint unsigned not null auto_increment primary key,
    role varchar(256) not null default 'admin',
    remark varchar(2048) default null
)engine=innodb default charset=utf8;
create index uuid on shiro.t_role(rid);
describe shiro.t_role;

# 权限表
drop table if exists shiro.t_permission;
create table if not exists shiro.t_permission
(
    pid bigint unsigned not null auto_increment primary key,
    permission varchar(256) not null,
    remark varchar(2048) default null
)engine=innodb default charset=utf8;
create index uuid on shiro.t_permission(pid);
describe shiro.t_permission;

# 用户&角色表
drop table if exists shiro.t_user_role;
create table if not exists shiro.t_user_role
(
    urid bigint unsigned not null auto_increment,
    uid bigint unsigned not null,
    rid bigint unsigned not null,
    primary key(urid),
    foreign key(uid) references shiro.t_user(uid),
    foreign key(rid) references shiro.t_role(rid)
)engine=innodb default charset=utf8;
create index uuid on shiro.t_user_role(urid);
describe shiro.t_user_role;

# 角色&权限表
drop table if exists shiro.t_role_permission;
create table if not exists shiro.t_role_permission
(
    rpid bigint unsigned not null auto_increment,
    rid bigint unsigned not null,
    pid bigint unsigned not null,
    primary key(rpid),
    foreign key(rid) references shiro.t_role(rid),
    foreign key(pid) references shiro.t_permission(pid)
)engine=innodb default charset=utf8;
create index uuid on shiro.t_role_permission(rpid);
describe shiro.t_role_permission;

insert into shiro.t_user (username, password) values ("admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
insert into shiro.t_user (username, password) values ("auditor", "ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1");
insert into shiro.t_user (username, password) values ("operator", "ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1");
insert into shiro.t_user (username, password) values ("operator1", "ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1");
insert into shiro.t_user (username, password) values ("operator2", "ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1");

insert into shiro.t_role (role, remark) values ("admin", "管理员");
insert into shiro.t_role (role, remark) values ("auditor", "审计员");
insert into shiro.t_role (role, remark) values ("operator", "操作员");

insert into shiro.t_user_role (uid, rid) values ("1", "1");
insert into shiro.t_user_role (uid, rid) values ("2", "2");
insert into shiro.t_user_role (uid, rid) values ("3", "3");
insert into shiro.t_user_role (uid, rid) values ("4", "3");
insert into shiro.t_user_role (uid, rid) values ("5", "3");
