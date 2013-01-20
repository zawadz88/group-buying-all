insert into groups values (0, 'Users');
insert into groups values (1, 'Administrators');

insert into group_authorities(group_id, authority) select id,'ROLE_USER' from groups where group_name='Users'; 
insert into group_authorities(group_id, authority) select id,'ROLE_USER' from groups where group_name='Administrators'; 
insert into group_authorities(group_id, authority) select id,'ROLE_ADMIN' from groups where group_name='Administrators'; 

--insert into users(username, password, enabled) values ('admin','admin',true);
--insert into users(username, password, enabled) values ('guest','guest',true);

-- Ch 4 Salted User
insert into users(username, password, enabled, salt) values ('admin','admin',1,CAST(dbms_random.value(1,1000000000) AS varchar2(25)));
insert into users(username, password, enabled, salt) values ('guest','guest',1,CAST(dbms_random.value(1,1000000000) AS varchar2(25)));

insert into group_members(id, group_id, username) select 0,id,'guest' from groups where group_name='Users';
insert into group_members(id, group_id, username) select 1,id,'admin' from groups where group_name='Administrators';

commit;
