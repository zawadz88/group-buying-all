/*
SQLyog Community v10.51 
MySQL - 5.5.27 : Database - test
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

/*Table structure for table `admin_authorities` */

DROP TABLE IF EXISTS `admin_authorities`;

CREATE TABLE `admin_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_admin_authorities_admins` (`username`),
  CONSTRAINT `fk_admin_authorities_admins` FOREIGN KEY (`username`) REFERENCES `admins` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin_authorities` */

insert  into `admin_authorities`(`username`,`authority`) values ('aaa','ROLE_ADMIN'),('bbb','ROLE_ADMIN');

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admins` */

insert  into `admins`(`username`,`password`,`enabled`,`salt`,`email`,`phone_number`) values ('aaa','b1545258a36b0ff3476d16ac256c17d185306407',1,'7.538346486422531E7','admin@admin.pl','+48666666666'),('bbb','b1545258a36b0ff3476d16ac256c17d185306407',1,'7.538346486422531E7','bbb@admin.pl','+48666666666');

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `categories` */

insert  into `categories`(`category_id`,`name`) values (1,'Gastronomia'),(2,'Turystyka'),(3,'Zdrowie');

/*Table structure for table `client_authorities` */

DROP TABLE IF EXISTS `client_authorities`;

CREATE TABLE `client_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_client_authorities_clients` (`username`),
  CONSTRAINT `fk_client_authorities_clients` FOREIGN KEY (`username`) REFERENCES `clients` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `client_authorities` */

insert  into `client_authorities`(`username`,`authority`) values ('zawadz88@gmail.com','ROLE_USER'),('zawadz88a@gmail.com','ROLE_USER');

/*Table structure for table `clients` */

DROP TABLE IF EXISTS `clients`;

CREATE TABLE `clients` (
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(50) NOT NULL,
  `phone_number` varchar(64) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `street` varchar(150) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `city` varchar(100) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `clients` */

insert  into `clients`(`email`,`password`,`salt`,`phone_number`,`first_name`,`last_name`,`street`,`postal_code`,`city`) values ('zawadz88@gmail.com','7c2c606721d27e38a168ee6a0ca47862f0416da2','9.279226179353936E8','233336','Piotr','Zawadzki','Or??na 48','02-938','Warsaw'),('zawadz88a@gmail.com','72f7c121ef66cb0403a8a31184fdc2e47f4465f8','5.853564610400964E8','qqqqqq','qqqq','qqqq','Or??na 48','02-938','Warsaw');

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_refresh_token` */

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
  `lead` varchar(400) NOT NULL,
  `price_before_discount` double NOT NULL,
  PRIMARY KEY (`offer_id`),
  KEY `FK_OFFERS_USERS` (`username`),
  KEY `FK_OFFERS_CATEGORIES` (`category_id`),
  CONSTRAINT `FK_OFFERS_CATEGORIES` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OFFERS_USERS` FOREIGN KEY (`username`) REFERENCES `sellers` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `offers` */

insert  into `offers`(`offer_id`,`title`,`description`,`street`,`city`,`postal_code`,`image_url`,`category_id`,`price`,`start_date`,`end_date`,`state`,`username`,`lead`,`price_before_discount`) values (13,'oferta1','opis','Or??na 48','Warsaw','02-938','http://aaa.pl',1,2,'2012-04-09 00:00:00','2012-04-11 00:00:00','0','zawadz88@gmail.com','asddsadsasa',2);

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `persistent_logins` */

/*Table structure for table `seller_authorities` */

DROP TABLE IF EXISTS `seller_authorities`;

CREATE TABLE `seller_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_seller_authorities_sellers` (`username`),
  CONSTRAINT `fk_seller_authorities_sellers` FOREIGN KEY (`username`) REFERENCES `sellers` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `seller_authorities` */

insert  into `seller_authorities`(`username`,`authority`) values ('zawadz88@gmail.com','ROLE_USER');

/*Table structure for table `sellers` */

DROP TABLE IF EXISTS `sellers`;

CREATE TABLE `sellers` (
  `email` varchar(150) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(25) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `trade` varchar(150) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `street` varchar(200) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `city` varchar(200) NOT NULL,
  `nip` varchar(100) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sellers` */

insert  into `sellers`(`email`,`password`,`enabled`,`salt`,`phone_number`,`trade`,`description`,`street`,`postal_code`,`city`,`nip`,`name`) values ('zawadz88@gmail.com','935465469b6a216c7213f27b75071706bbee3145',1,'2.215009137433447E8','23333','?????óaaa','saddadsa','Or??na 48','02-938','Warsaw','1234566778','seeeellerrrr');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
