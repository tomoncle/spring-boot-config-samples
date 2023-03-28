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
    `role_id`       BIGINT(20) NOT NULL,
    `permission_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`permission_id`, `role_id`),
    KEY `FK3fja4p32a5d36jajnsovb6br0` (`role_id`),
    CONSTRAINT `FK22mw9nytkra4rx56p3s0m15a1` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
    CONSTRAINT `FK3fja4p32a5d36jajnsovb6br0` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `i_role_permission` */

INSERT INTO `i_role_permission`(`role_id`, `permission_id`)
VALUES (1, 1),
       (3, 3);

/*Table structure for table `i_role_user` */

DROP TABLE IF EXISTS `i_role_user`;

CREATE TABLE `i_role_user`
(
    `user_id` BIGINT(20) NOT NULL,
    `role_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`role_id`, `user_id`),
    KEY `FKn3pn0hq5o3nteo6tto8667a0v` (`user_id`),
    CONSTRAINT `FKn3pn0hq5o3nteo6tto8667a0v` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `FKnjhh4suah2k3luqy7c0ui8j0y` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `i_role_user` */

INSERT INTO `i_role_user`(`user_id`, `role_id`)
VALUES (1, 1),
       (2, 3);

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission`
(
    `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
    `description`   VARCHAR(255) DEFAULT NULL,
    `pattern`       VARCHAR(255) DEFAULT NULL,
    `icon_cls`      VARCHAR(255) DEFAULT NULL,
    `pid`           INT(11)      DEFAULT NULL,
    `resource_type` VARCHAR(255) DEFAULT NULL,
    `state`         VARCHAR(255) DEFAULT NULL,
    `permission_id` BIGINT(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKqnwkq32j26nxf4ewmrs8jme3y` (`permission_id`),
    CONSTRAINT `FKqnwkq32j26nxf4ewmrs8jme3y` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `t_permission` */

INSERT INTO `t_permission`(`id`, `description`, `pattern`, `icon_cls`, `pid`, `resource_type`, `state`, `permission_id`)
VALUES (1, '所有路径', '/**', NULL, NULL, NULL, NULL, NULL),
       (3, 'HOME页', '/home', NULL, NULL, NULL, NULL, NULL);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
    `code`        VARCHAR(255) DEFAULT NULL,
    `name`        VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `t_role` */

INSERT INTO `t_role`(`id`, `code`, `name`, `description`)
VALUES (1, NULL, 'admin', '系统管理员'),
       (3, NULL, 'simple', '普通用户');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user`
(
    `id`        BIGINT(20) NOT NULL AUTO_INCREMENT,
    `locked`    TINYINT(1)   DEFAULT '1',
    `nick_name` VARCHAR(255) DEFAULT NULL,
    `password`  VARCHAR(255) DEFAULT NULL,
    `status`    TINYINT(4)   DEFAULT '1',
    `username`  VARCHAR(255) DEFAULT NULL,
    `enabled`   TINYINT(1)   DEFAULT '1',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_jhib4legehrm4yscx9t3lirqi` (`username`)
) ENGINE = INNODB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

/*Data for the table `t_user` */

INSERT INTO `t_user`(`id`, `locked`, `nick_name`, `password`, `status`, `username`, `enabled`)
VALUES (1, 1, '超级管理员', '$2a$10$VQQQvEH3fBvKtDVghVtzsegTR9bkOP.H4grgV5bUMp4FtY6s0z4JG', 1, 'admin', 1),
       (2, 1, '用户jack', '$2a$10$VQQQvEH3fBvKtDVghVtzsegTR9bkOP.H4grgV5bUMp4FtY6s0z4JG', 1, 'jack', 1);

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
