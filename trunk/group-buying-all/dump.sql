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
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
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

insert  into `client_authorities`(`username`,`authority`) values ('zawadz88@gmail.com','ROLE_USER'),('zawadz88a@gmail.com','ROLE_USER'),('qqq','ROLE_USER');

/*Table structure for table `clients` */

DROP TABLE IF EXISTS `clients`;

CREATE TABLE `clients` (
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
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

insert  into `clients`(`email`,`password`,`enabled`,`salt`,`phone_number`,`first_name`,`last_name`,`street`,`postal_code`,`city`) values ('qqq','59ef1b30337370964392714cea8f5665a5489792',1,'4.0098423241752E8','24444','Piotr','Zawadzki','Orężna 48','02-938','Warsaw'),('zawadz88@gmail.com','7c2c606721d27e38a168ee6a0ca47862f0416da2',1,'9.279226179353936E8','233336','Piotr','Zawadzki','Or??na 48','02-938','Warsaw'),('zawadz88a@gmail.com','72f7c121ef66cb0403a8a31184fdc2e47f4465f8',1,'5.853564610400964E8','qqqqqq','qqqq','qqqq','Or??na 48','02-938','Warsaw');

/*Table structure for table `coupons` */

DROP TABLE IF EXISTS `coupons`;

CREATE TABLE `coupons` (
  `id` bigint(20) NOT NULL,
  `email` varchar(64) NOT NULL,
  `offer_id` bigint(20) NOT NULL,
  `use_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `security_key` varchar(64) NOT NULL,
  `coupon_state` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_client_offers_offers` (`offer_id`),
  KEY `FK_client_offers_clients` (`email`),
  CONSTRAINT `FK_client_offers_clients` FOREIGN KEY (`email`) REFERENCES `clients` (`email`) ON UPDATE CASCADE,
  CONSTRAINT `FK_client_offers_offers` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`offer_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `coupons` */

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

insert  into `oauth_access_token`(`token_id`,`token`,`authentication_id`,`user_name`,`client_id`,`authentication`,`refresh_token`) values ('43a776db6d4adf9fc0abfec3179771fc','��\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken��6$��\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.Collections$EmptyMapY6�Z���\0\0xpsr\0java.util.Datehj�KYt\0\0xpw\0\0=U��xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSet�l�Z��*\0\0xr\0java.util.HashSet�D�����4\0\0xpw\0\0\0?@\0\0\0\0\0t\0readt\0writext\0bearert\0$2670cba0-858e-431e-94c3-d376eebbd263','32fa397764054149d77c968e3f8db3f8','qqq','tonr','��\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2Authentication�@bR\0L\0clientAuthenticationt\0CLorg/springframework/security/oauth2/provider/AuthorizationRequest;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationTokenӪ(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList�%1��\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0��^�\0L\0cq\0~\0xpsr\0java.util.ArrayListx����a�\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\06\0L\0rolet\0Ljava/lang/String;xpt\0	ROLE_USERxq\0~\0psr\0Horg.springframework.security.oauth2.provider.DefaultAuthorizationRequest�����>��\0Z\0approvedL\0approvalParameterst\0Ljava/util/Map;L\0authoritiesq\0~\0L\0authorizationParametersq\0~\0L\0resolvedRedirectUriq\0~\0L\0resourceIdst\0Ljava/util/Set;L\0scopeq\0~\0xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0\0xsr\0java.util.HashSet�D�����4\0\0xpw\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0ROLE_CLIENTxsq\0~\0?@\0\0\0\0\0w\0\0\0\0\0\0t\0usernamet\0qqqt\0\rclient_secrett\0secrett\0scopet\0\nread writet\0\ngrant_typet\0passwordt\0	client_idt\0tonrt\0passwordt\0qqqxpsq\0~\0w\0\0\0?@\0\0\0\0\0t\0\rgreenhouseApixsr\0java.util.LinkedHashSet�l�Z��*\0\0xq\0~\0w\0\0\0?@\0\0\0\0\0t\0readt\0writexsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\06\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0xq\0~\01ppsr\03pl.edu.pw.eiti.groupbuying.security.core.SaltedUser�տ\n���\0L\0saltq\0~\0xr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\06\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSet��я��U\0\0xq\0~\0	sr\0java.util.TreeSetݘP���[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\06\0\0xpw\0\0\0q\0~\0xpt\0qqqt\04.0098423241752E8',NULL);

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
  `lead` varchar(400) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `conditions` varchar(1000) NOT NULL,
  `category` varchar(20) NOT NULL,
  `city` varchar(200) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `image_url` varchar(300) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price` double NOT NULL,
  `price_before_discount` double NOT NULL,
  `state` char(1) NOT NULL,
  `street` varchar(200) NOT NULL,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `expiration_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`offer_id`),
  KEY `FK_OFFERS_USERS` (`username`),
  KEY `FK_OFFERS_CATEGORIES` (`category`),
  CONSTRAINT `FK_OFFERS_USERS` FOREIGN KEY (`username`) REFERENCES `sellers` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `offers` */

insert  into `offers`(`offer_id`,`title`,`lead`,`description`,`conditions`,`category`,`city`,`postal_code`,`image_url`,`start_date`,`price`,`price_before_discount`,`state`,`street`,`end_date`,`expiration_date`,`username`) values (17,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(18,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(19,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(20,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(21,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(22,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(23,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(24,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(25,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(26,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(27,'Wielkie ','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-23 13:33:35',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(28,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(29,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(30,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(31,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(32,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(33,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(34,'Wielkie Żarci','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-23 13:33:37',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(35,'Wielkie Żarcie, January 5, 2013','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-17 22:53:49',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(36,'Wielkie Ża','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-23 13:33:32',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(37,'Wielkie Ża','asddsadsasa','opis','ąśężź','SHOPPING','Warsaw','02-938','http://3.bp.blogspot.com/-NoPqXqTtPsI/TxLsCiWHFFI/AAAAAAAAAJk/OHZS3rkT1Sg/s1600/12jedzenie7.jpg','2013-03-23 13:33:30',0,0,'1','Orężna 482','2013-04-11 00:00:00','2013-02-25 22:13:26','ttt'),(38,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(39,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt'),(40,'Sylwek Val d\'Allos 2012/13','asddsadsasaąę','errewr','ąśężź','TRAVEL','Warsaw','02-938','http://linemed.pl/upload/41312_hamburger-i-frytki.jpg','2013-03-17 22:54:22',0,0,'1','Orężna 48','2013-04-11 00:00:00','2013-02-25 20:02:33','ttt');

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

insert  into `seller_authorities`(`username`,`authority`) values ('zawadz88@gmail.com','ROLE_USER'),('ttt','ROLE_USER');

/*Table structure for table `sellers` */

DROP TABLE IF EXISTS `sellers`;

CREATE TABLE `sellers` (
  `email` varchar(150) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
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

insert  into `sellers`(`email`,`password`,`enabled`,`salt`,`phone_number`,`trade`,`description`,`street`,`postal_code`,`city`,`nip`,`name`) values ('ttt','dc5d9c3e70b7b844cc24bf16cb670cca41033002',1,'4.692781723418692E8','23333','?????óaaa','opis','Or??na 48','02-938','Warsaw','1234566778','F-D'),('zawadz88@gmail.com','935465469b6a216c7213f27b75071706bbee3145',1,'2.215009137433447E8','23333','?????óaaa','saddadsa','Or??na 48','02-938','Warsaw','1234566778','seeeellerrrr');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
