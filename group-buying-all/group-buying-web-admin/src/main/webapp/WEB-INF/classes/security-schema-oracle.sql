-- ref. Appendix A of Spring Sec 3.0 manual
  create table users(
      username varchar2(50) not null primary key,
      password varchar2(50) not null,
-- Ch 4 Custom Salt exercise
--      enabled boolean not null
      enabled integer not null,
      salt varchar2(25) not null
      );

  create table authorities (
      username varchar2(50) not null,
      authority varchar2(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username));
      create unique index ix_auth_username on authorities (username,authority);

create table groups (
  id number(10,0) primary key, 
  group_name varchar2(50) not null);

create table group_authorities (
  group_id number(10,0) not null, 
  authority varchar(50) not null, 
  constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
  id number(10,0) primary key, 
  username varchar2(50) not null, 
  group_id number(10,0) not null, 
  constraint fk_group_members_group foreign key(group_id) references groups(id));

