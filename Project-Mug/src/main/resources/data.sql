INSERT INTO authority (authority_name) values ('ROLE_CUSTOMER');
INSERT INTO authority (authority_name) values ('ROLE_PARTNER');
INSERT INTO authority (authority_name) values ('ROLE_ADMIN');

insert into user (activated, email, nickname, password, phone_number, point, profile_picture_address, role, user_id) values (TRUE, 'admin', NULL, '{noop}admin', NULL, 1, "/home/ubuntu/images/users/default_user.jpg", 'A', 10000);
insert into admin (user_id, admin_name) values (10000, 'WE-CUP');
insert into user_authority (user_id, authority_name) values (10000, 'ROLE_ADMIN');

select * from user;
select * from user_authority;
select * from admin;