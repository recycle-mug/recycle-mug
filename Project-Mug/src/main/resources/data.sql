INSERT INTO AUTHORITY values 'ROLE_CUSTOMER';
INSERT INTO AUTHORITY values 'ROLE_PARTNER';
INSERT INTO AUTHORITY values 'ROLE_ADMIN';

insert into user (activated, email, nickname, password, phone_number, point, profile_picture, role, user_id) values (TRUE, 'admin', NULL, '{noop}admin', NULL, 1, NULL, 'A', 100);
insert into admin (user_id, admin_name) values (100, 'WE-CUP');
insert into user_authority (user_id, authority_name) values (100, 'ROLE_ADMIN');

select * from user;
select * from user_authority;
select * from admin;