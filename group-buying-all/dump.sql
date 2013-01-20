/*
SQLyog Community v9.61 
MySQL - 5.5.15 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test`;

/*Table structure for table `authorities` */

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `authorities` */

insert  into `authorities`(`username`,`authority`) values ('aaa','ROLE_ADMIN');

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `categories` */

insert  into `categories`(`category_id`,`name`) values (1,'Gastronomia'),(2,'Turystyka'),(3,'Zdrowie');

/*Table structure for table `clients` */

DROP TABLE IF EXISTS `clients`;

CREATE TABLE `clients` (
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `phone_number` varchar(64) NOT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `street` varchar(150) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clients` */

insert  into `clients`(`username`,`password`,`email`,`phone_number`,`first_name`,`last_name`,`street`,`postal_code`,`city`) values ('aaa','bbb','ccc','ddd',NULL,NULL,NULL,NULL,NULL),('das','aa','asaa','eee','das','sad','sss','dd','ee'),('pzawad','aaa','zawadz88@gmail.com','48668185174','Piotr','Zawadzki','OrÄ?Å¼na 48','02-938','Warszawa');

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(25) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `employees` */

insert  into `employees`(`username`,`password`,`enabled`,`salt`) values ('aaa','b1545258a36b0ff3476d16ac256c17d185306407',1,'7.538346486422531E7');

/*Table structure for table `group_authorities` */

DROP TABLE IF EXISTS `group_authorities`;

CREATE TABLE `group_authorities` (
  `group_id` bigint(20) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_group_authorities_group` (`group_id`),
  CONSTRAINT `fk_group_authorities_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `group_authorities` */

insert  into `group_authorities`(`group_id`,`authority`) values (1,'ROLE_USER'),(2,'ROLE_USER'),(2,'ROLE_ADMIN');

/*Table structure for table `group_members` */

DROP TABLE IF EXISTS `group_members`;

CREATE TABLE `group_members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_members_group` (`group_id`),
  CONSTRAINT `fk_group_members_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `group_members` */

insert  into `group_members`(`id`,`username`,`group_id`) values (1,'guest',1),(2,'admin',2),(3,'abcc',1),(4,'aaa',1),(5,'rrr',1),(6,'bbb',1);

/*Table structure for table `groups` */

DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `groups` */

insert  into `groups`(`id`,`group_name`) values (1,'Users'),(2,'Administrators');

/*Table structure for table `offers` */

DROP TABLE IF EXISTS `offers`;

CREATE TABLE `offers` (
  `offer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `street` varchar(200) NOT NULL,
  `city` varchar(200) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `image_url` varchar(300) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `state` char(1) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`offer_id`),
  KEY `FK_OFFERS_USERS` (`username`),
  KEY `FK_OFFERS_CATEGORIES` (`category_id`),
  CONSTRAINT `FK_OFFERS_CATEGORIES` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OFFERS_USERS` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `offers` */

insert  into `offers`(`offer_id`,`title`,`description`,`street`,`city`,`postal_code`,`image_url`,`category_id`,`price`,`start_date`,`end_date`,`state`,`username`) values (9,'oferta1','opis','OrÃ???Ã??Ã?Â¼na 48','Warszawa','02-938','sd',1,12,'2012-04-09 00:00:00','2012-04-11 00:00:00','0','abcc'),(10,'as','ads','OrÃ?Â??Ã?Â?Ã?Â¼na 48','KrakÃ?Â³w','02-938','sd',2,12,'2012-04-11 00:00:00','2012-04-15 00:00:00','0','abcc');

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `persistent_logins` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `trade` varchar(150) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `street` varchar(200) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`,`salt`,`email`,`phone_number`,`trade`,`description`,`street`,`postal_code`,`city`) values ('aaa','b1545258a36b0ff3476d16ac256c17d185306407',1,'7.538346486422531E7','sadds','4444',NULL,NULL,NULL,NULL,NULL),('abcc','43b2eea48195900e4215510d7c31946d672ad84a',1,'6.688753589356239E8','dsd','333',NULL,NULL,NULL,NULL,NULL),('admin','ddb8a6109572f4dd527a286431039b7d9d21300f',1,'247817698.16560456','admin@admin.pl',NULL,NULL,NULL,NULL,NULL,NULL),('bbb','1dbfdeadc4c1f2f9b36f2c10b1b0948bce1336cf',1,'3.500407539000406E8','sad','das','asd','das','as','as','sss'),('guest','2b345e2773c2dc4ef7b3449042dd46cbc563238a',1,'247428045.83853862','guest@guest.pl','+48666666666',NULL,NULL,NULL,NULL,NULL),('rrr','d97be9388ac6ae23683f54bf24230cad02e7b2d3',1,'7.003731448893902E8','222','222',NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
