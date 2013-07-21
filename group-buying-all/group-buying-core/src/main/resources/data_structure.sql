
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

DROP TABLE IF EXISTS `client_authorities`;

CREATE TABLE `client_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_client_authorities_clients` (`username`),
  CONSTRAINT `fk_client_authorities_clients` FOREIGN KEY (`username`) REFERENCES `clients` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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


DROP TABLE IF EXISTS `admin_authorities`;

CREATE TABLE `admin_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_admin_authorities_admins` (`username`),
  CONSTRAINT `fk_admin_authorities_admins` FOREIGN KEY (`username`) REFERENCES `admins` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

DROP TABLE IF EXISTS `seller_authorities`;

CREATE TABLE `seller_authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_seller_authorities_sellers` (`username`),
  CONSTRAINT `fk_seller_authorities_sellers` FOREIGN KEY (`username`) REFERENCES `sellers` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `city_id` varchar(50) DEFAULT NULL,
  `latitude` double NULL,
  `longitude` double NULL,
  PRIMARY KEY (`offer_id`),
  KEY `FK_OFFERS_USERS` (`username`),
  CONSTRAINT `FK_OFFERS_USERS` FOREIGN KEY (`username`) REFERENCES `sellers` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cities`;

CREATE TABLE `cities` (
  `city_id` varchar(50) NOT NULL,
  `name` varchar(150) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `paypal_transactions`;

CREATE TABLE `paypal_transactions` (
  `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  `offer_id` BIGINT(20) NOT NULL,
  `client_id` VARCHAR(64) NOT NULL,
  `transaction_token` VARCHAR(128) NOT NULL UNIQUE,
  `state` TINYINT(1) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `last_updated` TIMESTAMP NOT NULL,
  KEY `FK_paypal_transactions_offers` (`offer_id`),
  KEY `FK_paypal_transactions_clients` (`client_id`),
  CONSTRAINT `FK_paypal_transactions_clients` FOREIGN KEY (`client_id`) REFERENCES `clients` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_paypal_transactions_offers` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`offer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `coupons`;

CREATE TABLE `coupons` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(64) NOT NULL,
  `offer_id` BIGINT(20) NOT NULL,
  `use_date` TIMESTAMP NULL,
  `security_key` VARCHAR(64) NOT NULL,
  `coupon_state` TINYINT(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_client_offers_offers` (`offer_id`),
  KEY `FK_client_offers_clients` (`email`),
  CONSTRAINT `FK_client_offers_clients` FOREIGN KEY (`email`) REFERENCES `clients` (`email`) ON UPDATE CASCADE,
  CONSTRAINT `FK_client_offers_offers` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`offer_id`) ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `seller_oauth_access_token`;

CREATE TABLE `seller_oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `seller_oauth_refresh_token`;

CREATE TABLE `seller_oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `android_registered_devices`;

CREATE TABLE `android_registered_devices`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `registration_id` varchar(256) NOT NULL,
  `device_name` varchar(256) NOT NULL,
  `system_info` varchar(256) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
