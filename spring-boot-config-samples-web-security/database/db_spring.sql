/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.28 : Database - db_spring
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS */`db_spring` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_spring`;

/*Table structure for table `i_role_permission` */

DROP TABLE IF EXISTS `i_role_permission`;

CREATE TABLE `i_role_permission`
(
    `role_id`       bigint(20) NOT NULL,
    `permission_id` bigint(20) NOT NULL,
    PRIMARY KEY (`permission_id`, `role_id`),
    KEY `FK3fja4p32a5d36jajnsovb6br0` (`role_id`),
    CONSTRAINT `FK22mw9nytkra4rx56p3s0m15a1` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
    CONSTRAINT `FK3fja4p32a5d36jajnsovb6br0` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `i_role_permission` */

insert into `i_role_permission`(`role_id`, `permission_id`)
values (1, 1),
       (3, 2),
       (3, 4),
       (3, 5),
       (3, 6);

/*Table structure for table `i_role_user` */

DROP TABLE IF EXISTS `i_role_user`;

CREATE TABLE `i_role_user`
(
    `user_id` bigint(20) NOT NULL,
    `role_id` bigint(20) NOT NULL,
    PRIMARY KEY (`role_id`, `user_id`),
    KEY `FKn3pn0hq5o3nteo6tto8667a0v` (`user_id`),
    CONSTRAINT `FKn3pn0hq5o3nteo6tto8667a0v` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `FKnjhh4suah2k3luqy7c0ui8j0y` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `i_role_user` */

insert into `i_role_user`(`user_id`, `role_id`)
values (1, 1),
       (2, 3);

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `pattern`       varchar(128) DEFAULT NULL COMMENT '资源路径匹配, 例如：/api/v1/users/**',
    `authorize`     varchar(128) DEFAULT NULL COMMENT '按钮匹配, 例如：sys:user:list',
    `name`          varchar(64)  DEFAULT NULL COMMENT '权限名称',
    `description`   varchar(128) DEFAULT NULL COMMENT '权限描述',
    `icon_cls`      varchar(128) DEFAULT NULL COMMENT '权限图标路径',
    `resource_type` varchar(16)  DEFAULT 'PAGE' COMMENT '资源类型：MENU, PAGE, BUTTON',
    `state`         varchar(16)  DEFAULT 'closed' COMMENT '权限状态：open, closed',
    `permission_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKqnwkq32j26nxf4ewmrs8jme3y` (`permission_id`),
    CONSTRAINT `FKqnwkq32j26nxf4ewmrs8jme3y` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `t_permission` */

insert into `t_permission`(`id`, `pattern`, `authorize`, `name`, `description`, `icon_cls`, `resource_type`, `state`,
                           `permission_id`)
values (1, '/**', '**', '所有权限', NULL, NULL, 'PAGE', 'closed', NULL),
       (2, '/home', NULL, '首页权限', NULL, NULL, 'PAGE', 'closed', NULL),
       (3, '/api/**', NULL, 'api权限', NULL, NULL, 'PAGE', 'closed', NULL),
       (4, '/sample/**', NULL, 'sample权限', NULL, NULL, 'PAGE', 'closed', NULL),
       (5, NULL, 'sample:get', '查询', NULL, NULL, 'BUTTON', 'closed', 4),
       (6, NULL, 'sample:post', '添加', NULL, NULL, 'BUTTON', 'closed', 4),
       (7, NULL, 'sample:put', '修改', NULL, NULL, 'BUTTON', 'closed', 4),
       (8, NULL, 'sample:delete', '删除', NULL, NULL, 'BUTTON', 'closed', 4);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `code`        varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `t_role` */

insert into `t_role`(`id`, `code`, `name`, `description`)
values (1, 'admin', 'admin', '系统管理员'),
       (3, 'sample', 'sample', '普通用户');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `locked`    tinyint(1)   DEFAULT '1',
    `nick_name` varchar(255) DEFAULT NULL,
    `password`  varchar(255) DEFAULT NULL,
    `status`    tinyint(4)   DEFAULT '1',
    `username`  varchar(255) DEFAULT NULL,
    `enabled`   tinyint(1)   DEFAULT '1',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_jhib4legehrm4yscx9t3lirqi` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `t_user` */

insert into `t_user`(`id`, `locked`, `nick_name`, `password`, `status`, `username`, `enabled`)
values (1, 1, '超级管理员', '$2a$10$VQQQvEH3fBvKtDVghVtzsegTR9bkOP.H4grgV5bUMp4FtY6s0z4JG', 1, 'admin', 1),
       (2, 1, '用户jack', '$2a$10$VQQQvEH3fBvKtDVghVtzsegTR9bkOP.H4grgV5bUMp4FtY6s0z4JG', 1, 'jack', 1);

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
