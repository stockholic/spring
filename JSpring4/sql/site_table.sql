DROP TABLE IF EXISTS `auth_master`;
CREATE TABLE `auth_master` (
  `role_cd` varchar(10) NOT NULL COMMENT '권한코드',
  `role_nm` varchar(100) DEFAULT NULL COMMENT '권한설명',
  `role_stp` int(4) unsigned DEFAULT NULL COMMENT '순서',
  PRIMARY KEY (`role_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한마스터';

insert into `auth_master`(`role_cd`,`role_nm`,`role_stp`) values ('ADM','어드민',1);
insert into `auth_master`(`role_cd`,`role_nm`,`role_stp`) values ('SYS','시스템 관리자',2);
insert into `auth_master`(`role_cd`,`role_nm`,`role_stp`) values ('MNG','관리자',3);
insert into `auth_master`(`role_cd`,`role_nm`,`role_stp`) values ('USR','일반 유저',4);
insert into `auth_master`(`role_cd`,`role_nm`,`role_stp`) values ('GUS','게스트',5);


DROP TABLE IF EXISTS `auth_member`;
CREATE TABLE `auth_member` (
  `user_id` varchar(30) NOT NULL DEFAULT '' COMMENT '유저아이디',
  `passwd` varchar(200) DEFAULT NULL COMMENT '패스워드',
  `user_nm` varchar(30) DEFAULT NULL COMMENT '이름',
  `email` varchar(200) DEFAULT NULL COMMENT '메일',
  `receive` enum('Y','N') DEFAULT 'Y' COMMENT '메일수신여부(Y:수신, N:미수신)',
  `use_yn` char(1) CHARACTER SET latin1 DEFAULT 'N',
  `reg_date` datetime NOT NULL COMMENT '등록일',
  PRIMARY KEY (`user_id`),
  KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회원정보';

insert into `auth_member`(`user_id`,`passwd`,`user_nm`,`email`,`receive`,`use_yn`,`reg_date`) values ('stock','$2a$10$7c.ThXY8pNrdiBCVUCIUqO2ep4jMNHCG64xQkR8E4qTkCiP9AbXwu','메롱',null,null,'Y',sysdate());


DROP TABLE IF EXISTS `auth_member_role`;
CREATE TABLE `auth_member_role` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_id` varchar(30) DEFAULT NULL COMMENT '유저아이디',
  `role_cd` varchar(10) DEFAULT NULL COMMENT '권한코드',
  `modify_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='유저별 권한';

insert into `auth_member_role`(`seq`,`user_id`,`role_cd`,`modify_dt`) values (1,'stock','ADM',sysdate());
insert into `auth_member_role`(`seq`,`user_id`,`role_cd`,`modify_dt`) values (2,'stock','USR',sysdate());


DROP TABLE IF EXISTS `com_board`;
CREATE TABLE `com_board` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `fid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '그룹아이디',
  `lev` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '깊이',
  `stp` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '순서',
  `user_id` varchar(30) NOT NULL COMMENT '아이디',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `content` text COMMENT '내용',
  `ip` varchar(15) DEFAULT NULL COMMENT '아이피',
  `reg_date` datetime COMMENT '등록일',
  `read_cnt` int(10) unsigned DEFAULT '0' COMMENT '조회수',
  `flag` varchar(20) NOT NULL COMMENT '구분',
  PRIMARY KEY (`seq`),
  KEY `IDX_fid` (`fid`) ,
  KEY `IDX_flag` (`flag`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시판';



DROP TABLE IF EXISTS `com_board_file`;
CREATE TABLE `com_board_file` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `com_board_seq` int(10) unsigned DEFAULT '0',
  `file_sys_name` varchar(50) DEFAULT NULL,
  `file_real_name` varchar(100) DEFAULT NULL,
  `file_ext` varchar(10) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL COMMENT 'F:일반, M:이미지',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='파일목록';

