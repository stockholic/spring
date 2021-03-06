CREATE TABLE auth_member (
  user_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  user_id varchar(20) NOT NULL COMMENT '유저아이디',
  password varchar(200) DEFAULT NULL COMMENT '패스워드',
  user_nm varchar(50) DEFAULT NULL COMMENT '이름',
  email varchar(50) DEFAULT NULL COMMENT '메일',
  use_yn char(1) DEFAULT 'N' COMMENT '사용여부(Y:사용,N:비사용)',
  reg_date datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (user_srl),
  UNIQUE KEY UQ_USER_ID (user_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT='유저정보';

insert into auth_member(user_srl,user_id,password,user_nm,email,use_yn,reg_date) values (1,'admin','$2a$10$3pfLylekH08DS6IO7RY4s.oHHhq5V1pOpiLeq3lKXz.jbfSzqNvZC','관리','','Y',SYSDATE());



CREATE TABLE auth_member_role (
  user_role_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  user_id varchar(20) DEFAULT NULL COMMENT '유저아이디',
  role_cd varchar(10) DEFAULT NULL COMMENT '권한코드',
  PRIMARY KEY (user_role_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='유저별 권한';


insert into auth_member_role(user_role_srl,user_id,role_cd) values (1,'admin','ADM');



CREATE TABLE auth_menu (
  menu_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  parent_srl int(10) DEFAULT NULL COMMENT '참조번호',
  menu_nm varchar(100) DEFAULT NULL COMMENT '메뉴명',
  menu_url varchar(100) DEFAULT NULL COMMENT '경로',
  menu_stp int(4) DEFAULT NULL COMMENT '순서',
  use_yn char(1) DEFAULT 'Y' COMMENT '사용여부',
  PRIMARY KEY (menu_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴관리';


insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (1,0,'사이트관리','',1,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (2,1,'권한관리','/adm/role/list',1,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (3,1,'사용자관리','/adm/user/list',2,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (4,1,'메뉴관리','',3,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (5,4,'메뉴등록','/adm/menu/list',1,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (6,4,'메뉴설정','/adm/menu/role/list',2,'Y');
insert into auth_menu(menu_srl,parent_srl,menu_nm,menu_url,menu_stp,use_yn) values (7,0,'게시판관리','',2,'Y');



CREATE TABLE auth_menu_role (
  role_cd varchar(10) DEFAULT NULL COMMENT '권한코드',
  menu_srl int(11) DEFAULT NULL COMMENT '참조번호'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한별메뉴';


insert into auth_menu_role(role_cd,menu_srl) values ('ADM',1);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',2);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',3);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',4);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',5);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',6);
insert into auth_menu_role(role_cd,menu_srl) values ('ADM',7);


CREATE TABLE auth_role (
  role_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  role_cd varchar(10) DEFAULT NULL COMMENT '권한코드',
  role_nm varchar(50) DEFAULT NULL COMMENT '권한',
  use_yn char(1) CHARACTER SET latin1 DEFAULT 'Y',
  PRIMARY KEY (role_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한코드';


insert into auth_role(role_srl,role_cd,role_nm,use_yn) values (1,'ADM','관리자','Y');
insert into auth_role(role_srl,role_cd,role_nm,use_yn) values (2,'USR','사용자','Y');

CREATE TABLE auth_login_log (
  log_srl int(10) unsigned NOT NULL AUTO_INCREMENT,
  try_id varchar(100) DEFAULT NULL,
  success_yn char(1) DEFAULT NULL,
  reg_date datetime DEFAULT NULL,
  remote_addr varchar(20) DEFAULT NULL,
  comment varchar(100) DEFAULT NULL,
  PRIMARY KEY (log_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='로그인 이력';

CREATE TABLE com_board (
  board_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  fid int(10) unsigned NOT NULL DEFAULT '0' COMMENT '그룹아이디',
  lev int(4) unsigned NOT NULL DEFAULT '0' COMMENT '깊이',
  stp int(4) unsigned NOT NULL DEFAULT '0' COMMENT '순서',
  user_id varchar(20) NOT NULL COMMENT '아이디',
  title varchar(200) DEFAULT NULL COMMENT '제목',
  content text COMMENT '내용',
  ip varchar(15) DEFAULT NULL COMMENT '아이피',
  read_cnt int(10) unsigned DEFAULT '0' COMMENT '조회수',
  flag varchar(10) NOT NULL COMMENT '구분',
  reg_date datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (board_srl),
  KEY IDX_BOARD_FLAG (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시판';

CREATE TABLE com_board_reply (
  board_reply_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  board_srl int(10) unsigned DEFAULT '0' COMMENT '참조번호',
  user_id varchar(20) DEFAULT NULL COMMENT '유저아이디',
  content text COMMENT '내용',
  ip varchar(20) DEFAULT NULL COMMENT '아이피',
  reg_date datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (board_reply_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE com_file_data (
  file_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  file_ref_srl int(10) unsigned DEFAULT '0' COMMENT '파일참조번호',
  file_path varchar(100) DEFAULT NULL,
  file_sys_nm varchar(50) DEFAULT NULL,
  file_real_nm varchar(100) DEFAULT NULL,
  file_size varchar(10) DEFAULT NULL,
  file_tp varchar(10) DEFAULT NULL COMMENT '파일구분',
  reg_dt datetime DEFAULT NULL,
  PRIMARY KEY (file_srl),
  KEY file_tp (file_tp,file_ref_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일목록';

CREATE TABLE stock_code (
  code_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  stock_cd char(6) DEFAULT NULL COMMENT '주식코드',
  stock_nm varchar(50) DEFAULT NULL COMMENT '주식명',
  stock_tp char(1) DEFAULT NULL COMMENT 'P:코스피/D:코스닥',
  reg_dt datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (code_srl)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='주식종목';

CREATE TABLE stock_trade (
  trade_srl int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  stock_cd char(6) CHARACTER SET latin1 NOT NULL COMMENT '주식코드',
  trade_buy_prc int(11) DEFAULT '0' COMMENT '매입',
  trade_sell_prc int(11) DEFAULT '0' COMMENT '매도',
  trade_ea int(11) DEFAULT '0' COMMENT '거래 주식수',
  trade_tp char(1) DEFAULT NULL COMMENT 'B:매수/S:매도',
  tax int(11) DEFAULT '0' COMMENT '세금',
  fee int(11) DEFAULT '0' COMMENT '수수료',
  memo text COMMENT '메모',
  ip varchar(20) DEFAULT NULL,
  user_id varchar(20) DEFAULT NULL,
  point int(5) DEFAULT '0' COMMENT '평가',
  trade_dt datetime DEFAULT NULL COMMENT '거래일자',
  reg_dt datetime DEFAULT NULL COMMENT '등록일',
  upt_dt datetime DEFAULT NULL COMMENT '수정일',
  PRIMARY KEY (trade_srl)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주식거래';



