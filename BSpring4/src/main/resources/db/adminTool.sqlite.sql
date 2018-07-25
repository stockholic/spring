CREATE TABLE auth_member (
	user_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	user_id	varchar(20) UNIQUE,
	password	varchar(200),
	user_nm	varchar(50),
	email	varchar(50),
	use_yn	char(1),
	reg_date	datetime
);

insert into auth_member(user_srl,user_id,password,user_nm,email,use_yn,reg_date) values (1,'admin','$2a$10$3pfLylekH08DS6IO7RY4s.oHHhq5V1pOpiLeq3lKXz.jbfSzqNvZC','관리','','Y',datetime('now','localtime'));



CREATE TABLE auth_member_role (
	user_role_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	user_id	varchar(20),
	role_cd	varchar(10)
);

insert into auth_member_role(user_role_srl,user_id,role_cd) values (1,'admin','ADM');


CREATE TABLE auth_menu (
	menu_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	parent_srl	INTEGER,
	menu_nm	varchar(100),
	menu_url	varchar(100),
	menu_stp	INTEGER,
	use_yn	char(1)
);


insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (1,0,'사이트관리','',1,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (2,1,'권한관리','/adm/role/list',1,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (3,1,'사용자관리','/adm/user/list',2,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (4,1,'메뉴관리','',3,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (5,4,'메뉴등록','/adm/menu/list',1,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (6,4,'메뉴설정','/adm/menu/role/list',2,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (7,0,'게시판관리','',2,'Y');



CREATE TABLE auth_menu_role (
	role_cd	varchar(10),
	menu_srl	integer
);


insert into auth_menu_role(role_cd,menu_srl) values ('ADM',1);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',2);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',3);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',4);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',5);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',6);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',7);




CREATE TABLE auth_role (
	role_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	role_cd	varchar(10),
	role_nm	varchar(50),
	use_yn	char(1)
);


insert into auth_role(role_srl,role_cd,role_nm,use_yn) values (1,'ADM','관리자','Y');
insert into auth_role(role_srl,role_cd,role_nm,use_yn) values (2,'USR','사용자','Y');


CREATE TABLE com_board (
	board_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	fid	INTEGER,
	lev	INTEGER,
	stp	INTEGER,
	user_id	varchar(20),
	title	varchar(200),
	content	text,
	ip	varchar(15),
	reg_date	datetime,
	read_cnt	INTEGER,
	flag	varchar(10)
);

CREATE INDEX IDX_BOARD_FLAG ON com_board (flag );

CREATE TABLE com_board_file (
	board_file_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	board_srl	INTEGER,
	file_path	varchar ( 100 ),
	file_sys_nm	varchar ( 50 ),
	file_real_nm	varchar ( 100 ),
	file_size	varchar ( 10 ),
	reg_date	datetime
);

CREATE INDEX IDX_BOARD_FILE ON com_board_file (board_srl );


CREATE TABLE com_board_reply (
	board_reply_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	board_srl	INTEGER,
	user_id	varchar(20),
	content	text,
	ip	varchar(20),
	reg_date	datetime
);

CREATE INDEX IDX_COM_BOARD_REPLY ON com_board_reply (board_srl );



CREATE TABLE login_log (
	log_srl	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	try_id	varchar(100) NOT NULL,
	success_yn	varchar(1) not null,
	reg_date	datetime not null,
	remote_addr	varchar(20),
	comment	varchar(100)
);




