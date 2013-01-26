/*
SQLyog Community v9.61 
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
  `username` varchar(50) CHARACTER SET latin1 NOT NULL,
  `authority` varchar(50) CHARACTER SET latin1 NOT NULL,
  KEY `fk_admin_authorities_admins` (`username`),
  CONSTRAINT `fk_admin_authorities_admins` FOREIGN KEY (`username`) REFERENCES `admins` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin_authorities` */

insert  into `admin_authorities`(`username`,`authority`) values ('aaa','ROLE_ADMIN'),('bbb','ROLE_ADMIN');

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `username` varchar(50) CHARACTER SET latin1 NOT NULL,
  `password` varchar(50) CHARACTER SET latin1 NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(25) CHARACTER SET latin1 NOT NULL,
  `email` varchar(50) CHARACTER SET latin1 NOT NULL,
  `phone_number` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admins` */

insert  into `admins`(`username`,`password`,`enabled`,`salt`,`email`,`phone_number`) values ('aaa','b1545258a36b0ff3476d16ac256c17d185306407',1,'7.538346486422531E7','admin@admin.pl','+48666666666'),('bbb','b1545258a36b0ff3476d16ac256c17d185306407',1,'7.538346486422531E7','bbb@admin.pl','+48666666666');

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `categories` */

insert  into `categories`(`category_id`,`name`) values (1,'Gastronomia'),(2,'Turystyka'),(3,'Zdrowie');

/*Table structure for table `client_authorities` */

DROP TABLE IF EXISTS `client_authorities`;

CREATE TABLE `client_authorities` (
  `username` varchar(50) CHARACTER SET latin1 NOT NULL,
  `authority` varchar(50) CHARACTER SET latin1 NOT NULL,
  KEY `fk_client_authorities_clients` (`username`),
  CONSTRAINT `fk_client_authorities_clients` FOREIGN KEY (`username`) REFERENCES `clients` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `client_authorities` */

insert  into `client_authorities`(`username`,`authority`) values ('zawadz88@gmail.com','ROLE_USER'),('zawadz88a@gmail.com','ROLE_USER');

/*Table structure for table `clients` */

DROP TABLE IF EXISTS `clients`;

CREATE TABLE `clients` (
  `email` varchar(64) CHARACTER SET latin1 NOT NULL,
  `password` varchar(64) CHARACTER SET latin1 NOT NULL,
  `salt` varchar(50) NOT NULL,
  `phone_number` varchar(64) CHARACTER SET latin1 NOT NULL,
  `first_name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `last_name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `street` varchar(150) CHARACTER SET latin1 NOT NULL,
  `postal_code` varchar(20) CHARACTER SET latin1 NOT NULL,
  `city` varchar(100) CHARACTER SET latin1 NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_access_token` */

insert  into `oauth_access_token`(`token_id`,`token`,`authentication_id`,`user_name`,`client_id`,`authentication`,`refresh_token`) values ('86ab80a0feab57b1829f5515a73f8b8f','¨Ì\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken≤û6$˙Œ\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6ÖZ‹Á–\0\0xpsr\0java.util.DatehjÅKYt\0\0xpw\0\0<ó2ÃËxpsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0readt\0writext\0bearert\0$f9a37875-82a9-4db8-b1c3-60b9c97c3c1a','1a28bced3bb8391ffb32f9f5f2285b93','paul','tonr','¨Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0clientAuthenticationt\0CLorg/springframework/security/oauth2/provider/AuthorizationRequest;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µÏé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0ÄÀ^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ“ô«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\06\0L\0rolet\0Ljava/lang/String;xpt\0	ROLE_USERxq\0~\0psr\0Horg.springframework.security.oauth2.provider.DefaultAuthorizationRequest∂õü¸>òÿ\0Z\0approvedL\0approvalParameterst\0Ljava/util/Map;L\0authoritiesq\0~\0L\0authorizationParametersq\0~\0L\0resolvedRedirectUriq\0~\0L\0resourceIdst\0Ljava/util/Set;L\0scopeq\0~\0xpsr\0java.util.HashMap⁄¡√`—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0\0xsr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0ROLE_CLIENTxsq\0~\0?@\0\0\0\0\0w\0\0\0\0\0\0t\0usernamet\0pault\0\rclient_secrett\0secrett\0scopet\0\nread writet\0\ngrant_typet\0passwordt\0	client_idt\0tonrt\0passwordt\0emuxpsq\0~\0w\0\0\0?@\0\0\0\0\0t\0\rgreenhouseApixsr\0java.util.LinkedHashSetÿl◊Zï›*\0\0xq\0~\0w\0\0\0?@\0\0\0\0\0t\0readt\0writexsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\06\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\01ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\06\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.TreeSet›òPìïÌá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\06\0\0xpw\0\0\0q\0~\0xpt\0paul',NULL);

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `offers` */

DROP TABLE IF EXISTS `offers`;

CREATE TABLE `offers` (
  `offer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET latin1 NOT NULL,
  `description` varchar(1000) CHARACTER SET latin1 NOT NULL,
  `street` varchar(200) CHARACTER SET latin1 NOT NULL,
  `city` varchar(200) CHARACTER SET latin1 NOT NULL,
  `postal_code` varchar(20) CHARACTER SET latin1 NOT NULL,
  `image_url` varchar(300) CHARACTER SET latin1 NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `state` char(1) CHARACTER SET latin1 NOT NULL,
  `username` varchar(50) CHARACTER SET latin1 NOT NULL,
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
  `username` varchar(64) CHARACTER SET latin1 NOT NULL,
  `series` varchar(64) CHARACTER SET latin1 NOT NULL,
  `token` varchar(64) CHARACTER SET latin1 NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `persistent_logins` */

insert  into `persistent_logins`(`username`,`series`,`token`,`last_used`) values ('zawadz88@gmail.com','wKRk+9mSkfvbInLC4UjcLw==','hx4RmKjE2inct8CKGQ0Xxg==','2013-01-24 20:38:08');

/*Table structure for table `seller_authorities` */

DROP TABLE IF EXISTS `seller_authorities`;

CREATE TABLE `seller_authorities` (
  `username` varchar(50) CHARACTER SET latin1 NOT NULL,
  `authority` varchar(50) CHARACTER SET latin1 NOT NULL,
  KEY `fk_seller_authorities_sellers` (`username`),
  CONSTRAINT `fk_seller_authorities_sellers` FOREIGN KEY (`username`) REFERENCES `sellers` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `seller_authorities` */

insert  into `seller_authorities`(`username`,`authority`) values ('zawadz88@gmail.com','ROLE_USER');

/*Table structure for table `sellers` */

DROP TABLE IF EXISTS `sellers`;

CREATE TABLE `sellers` (
  `email` varchar(150) CHARACTER SET latin1 NOT NULL,
  `password` varchar(50) CHARACTER SET latin1 NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(25) CHARACTER SET latin1 NOT NULL,
  `phone_number` varchar(15) CHARACTER SET latin1 NOT NULL,
  `trade` varchar(150) CHARACTER SET latin1 NOT NULL,
  `description` varchar(1000) CHARACTER SET latin1 DEFAULT NULL,
  `street` varchar(200) CHARACTER SET latin1 NOT NULL,
  `postal_code` varchar(20) CHARACTER SET latin1 NOT NULL,
  `city` varchar(200) CHARACTER SET latin1 NOT NULL,
  `nip` varchar(100) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sellers` */

insert  into `sellers`(`email`,`password`,`enabled`,`salt`,`phone_number`,`trade`,`description`,`street`,`postal_code`,`city`,`nip`,`name`) values ('zawadz88@gmail.com','935465469b6a216c7213f27b75071706bbee3145',1,'2.215009137433447E8','23333','?????√≥aaa','saddadsa','Or??na 48','02-938','Warsaw','1234566778','seeeellerrrr');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
