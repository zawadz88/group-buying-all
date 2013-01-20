-- ref. Appendix A of Spring Sec 3.0 manual
  CREATE TABLE users(
      username VARCHAR(50) NOT NULL PRIMARY KEY,
      PASSWORD VARCHAR(50) NOT NULL,
-- Ch 4 Custom Salt exercise
--      enabled boolean not null
      enabled BOOLEAN NOT NULL,
      salt VARCHAR(25) NOT NULL,
      email VARCHAR(50) NOT NULL,
      phone_number VARCHAR(15)
      );

  CREATE TABLE authorities (
      username VARCHAR(50) NOT NULL,
      authority VARCHAR(50) NOT NULL,
      CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username));
      CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

CREATE TABLE groups (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, 
  group_name VARCHAR(50) NOT NULL);

CREATE TABLE group_authorities (
  group_id BIGINT NOT NULL, 
  authority VARCHAR(50) NOT NULL, 
  CONSTRAINT fk_group_authorities_group FOREIGN KEY(group_id) REFERENCES groups(id));

CREATE TABLE group_members (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, 
  username VARCHAR(50) NOT NULL, 
  group_id BIGINT NOT NULL, 
  CONSTRAINT fk_group_members_group FOREIGN KEY(group_id) REFERENCES groups(id));
  
  CREATE TABLE persistent_logins (
  username VARCHAR(64) NOT NULL, 
  series VARCHAR(64) PRIMARY KEY,
  token VARCHAR(64) NOT NULL, 
  last_used TIMESTAMP NOT NULL);
  
INSERT INTO groups(group_name) VALUES ('Users');
INSERT INTO groups(group_name) VALUES ('Administrators');
INSERT INTO group_authorities(group_id, authority) SELECT id,'ROLE_USER' FROM groups WHERE group_name='Users'; 
INSERT INTO group_authorities(group_id, authority) SELECT id,'ROLE_USER' FROM groups WHERE group_name='Administrators'; 
INSERT INTO group_authorities(group_id, authority) SELECT id,'ROLE_ADMIN' FROM groups WHERE group_name='Administrators'; 

INSERT INTO users(username, PASSWORD, enabled, salt) VALUES ('admin','admin',TRUE,CONVERT(RAND()*1000000000, CHAR(25)));
INSERT INTO users(username, PASSWORD, enabled, salt) VALUES ('guest','guest',TRUE,CONVERT(RAND()*1000000000, CHAR(25)));

INSERT INTO group_members(group_id, username) SELECT id,'guest' FROM groups WHERE group_name='Users';
INSERT INTO group_members(group_id, username) SELECT id,'admin' FROM groups WHERE group_name='Administrators';


create table clients(
  username VARCHAR(64) PRIMARY KEY, 
  password VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL, 
  phone_number VARCHAR(64) NOT NULL
);

create table offers(
  offer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL, 
  description VARCHAR(1000) NOT NULL,
  street VARCHAR(64), 
  city VARCHAR(64)
);


