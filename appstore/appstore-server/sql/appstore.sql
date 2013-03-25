-- Adminer 3.3.3 MySQL dump

SET NAMES utf8;
SET foreign_key_checks = 0;
SET time_zone = 'SYSTEM';
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';
use store;
DROP TABLE IF EXISTS `Application`;
CREATE TABLE `Application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `lastVersion_id` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Application_ApplicationVersion1` (`lastVersion_id`),
  CONSTRAINT `fk_Application_ApplicationVersion1` FOREIGN KEY (`lastVersion_id`) REFERENCES `ApplicationVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

INSERT INTO `Application` (`id`, `name`, `description`, `lastVersion_id`, `url`) VALUES
(1,	'Cilia',	'Cilia Distribution package',	1,	'http://repository-cilia.forge.cloudbees.com/release/fr/liglab/adele/cilia/cilia.dp/1.6.2/cilia.dp-1.6.2.dp'),
(3,	'Cilia Remote',	'Cilia Remote Distribution Package',	0,	'http://repository-cilia.forge.cloudbees.com/release/fr/liglab/adele/cilia/cilia-remote.dp/1.6.2/cilia-remote.dp-1.6.2.dp'),
(5,	'Cilia EA',	'Cilia Event Admin Distribution Package',	NULL,	'http://repository-cilia.forge.cloudbees.com/release/fr/liglab/adele/cilia/cilia-eventadmin.dp/1.6.2/cilia-eventadmin.dp-1.6.2.dp'),
(6,	'Cilia Joram',	'Cilia Joram Distribution Package',	NULL,	'http://repository-cilia.forge.cloudbees.com/release/fr/liglab/adele/cilia/cilia-joram.dp/1.6.2/cilia-joram.dp-1.6.2.dp');

DROP TABLE IF EXISTS `ApplicationVersion`;
CREATE TABLE `ApplicationVersion` (
  `id` int(11) NOT NULL,
  `version` varchar(45) DEFAULT NULL,
  `application_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ApplicationVersion_Application1` (`application_id`),
  CONSTRAINT `fk_ApplicationVersion_Application1` FOREIGN KEY (`application_id`) REFERENCES `Application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `ApplicationVersion` (`id`, `version`, `application_id`) VALUES
(0,	'1.0.0',	3),
(1,	'1.6.3',	1);

DROP TABLE IF EXISTS `Appstore`;
CREATE TABLE `Appstore` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='maintains the list of available appstores';




DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `Category` (`id`, `name`, `description`) VALUES
(1,	'Game',	'The category name describes games'),
(2,	'Home',	'Products for home');

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `product_Price_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`user_id`,`product_Price_id`),
  KEY `fk_Order_User1` (`user_id`),
  KEY `fk_Order_Product_Price1` (`product_Price_id`),
  CONSTRAINT `fk_Order_Product_Price1` FOREIGN KEY (`product_Price_id`) REFERENCES `Product_Price` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_User1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

INSERT INTO `Orders` (`id`, `user_id`, `product_Price_id`) VALUES
(15,	2,	1);

DROP TABLE IF EXISTS `OwnedDevice`;
CREATE TABLE `OwnedDevice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Device_User1` (`user_id`),
  CONSTRAINT `fk_Device_User1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO `OwnedDevice` (`id`, `name`, `user_id`, `url`) VALUES
(2,	'My Super Device',	2,	'http://localhost:8080'),
(3,	'My Super Device 2',	2,	'http://localhost:8080');

DROP TABLE IF EXISTS `Product`;
CREATE TABLE `Product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `imageURL` varchar(45) DEFAULT NULL,
  `productVersion_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_products_ProductVersion1` (`productVersion_id`),
  CONSTRAINT `fk_products_ProductVersion1` FOREIGN KEY (`productVersion_id`) REFERENCES `ProductVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO `Product` (`id`, `name`, `description`, `imageURL`, `productVersion_id`) VALUES
(1,	'Product1',	'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi urna ante, egestas nec rhoncus eget, blandit at arcu. Sed ullamcorper feugiat nisi. Vivamus lacus dui, scelerisque id tempor sit amet, scelerisque a velit. Nulla facilisi. Aliquam id elit vel ',	'assets/images/products/1.png',	1),
(2,	'Product 2',	'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi urna ante, egestas nec rhoncus eget, blandit at arcu. Sed ullamcorper feugiat nisi. Vivamus lacus dui, scelerisque id tempor sit amet, scelerisque a velit. Nulla facilisi. Aliquam id elit vel ',	'assets/images/products/2.png',	2),
(3,	'Product 3',	'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi urna ante, egestas nec rhoncus eget, blandit at arcu. Sed ullamcorper feugiat nisi. Vivamus lacus dui, scelerisque id tempor sit amet, scelerisque a velit. Nulla facilisi. Aliquam id elit vel ',	'assets/images/products/3.png',	NULL),
(4,	'Product 4',	'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi urna ante, egestas nec rhoncus eget, blandit at arcu. Sed ullamcorper feugiat nisi. Vivamus lacus dui, scelerisque id tempor sit amet, scelerisque a velit. Nulla facilisi. Aliquam id elit vel ',	'assets/images/products/4.png',	NULL);

DROP TABLE IF EXISTS `ProductVersion`;
CREATE TABLE `ProductVersion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `version` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ProductVersion_products1_idx` (`product_id`),
  CONSTRAINT `fk_ProductVersion_products1` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `ProductVersion` (`id`, `product_id`, `version`) VALUES
(1,	1,	'1.0.0'),
(2,	2,	'1.0.6');

DROP TABLE IF EXISTS `ProductVersion_has_ApplicationVersion`;
CREATE TABLE `ProductVersion_has_ApplicationVersion` (
  `productVersion_id` int(11) NOT NULL,
  `applicationVersion_id` int(11) NOT NULL,
  PRIMARY KEY (`productVersion_id`,`applicationVersion_id`),
  KEY `fk_ProductVersion_has_ApplicationVersion_ApplicationVersion1` (`applicationVersion_id`),
  KEY `fk_ProductVersion_has_ApplicationVersion_ProductVersion1` (`productVersion_id`),
  CONSTRAINT `fk_ProductVersion_has_ApplicationVersion_ApplicationVersion1` FOREIGN KEY (`applicationVersion_id`) REFERENCES `ApplicationVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProductVersion_has_ApplicationVersion_ProductVersion1` FOREIGN KEY (`productVersion_id`) REFERENCES `ProductVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `ProductVersion_has_ApplicationVersion` (`productVersion_id`, `applicationVersion_id`) VALUES
(1,	0),
(2,	0),
(1,	1),
(2,	1);

DROP TABLE IF EXISTS `ProductVersion_has_ServiceVersion`;
CREATE TABLE `ProductVersion_has_ServiceVersion` (
  `productVersion_id` int(11) NOT NULL,
  `serviceVersion_id` int(11) NOT NULL,
  PRIMARY KEY (`productVersion_id`,`serviceVersion_id`),
  KEY `fk_ProductVersion_has_ServiceVersion_ServiceVersion1` (`serviceVersion_id`),
  KEY `fk_ProductVersion_has_ServiceVersion_ProductVersion1` (`productVersion_id`),
  CONSTRAINT `fk_ProductVersion_has_ServiceVersion_ProductVersion1` FOREIGN KEY (`productVersion_id`) REFERENCES `ProductVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProductVersion_has_ServiceVersion_ServiceVersion1` FOREIGN KEY (`serviceVersion_id`) REFERENCES `ServiceVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Product_Price`;
CREATE TABLE `Product_Price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `Appstore_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_Product_Price_Product1` (`product_id`),
  KEY `fk_Product_Price_Appstore1` (`Appstore_id`),
  CONSTRAINT `fk_Product_Price_Appstore1` FOREIGN KEY (`Appstore_id`) REFERENCES `Appstore` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_Price_Product1` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO `Product_Price` (`id`, `price`, `unit`, `product_id`, `Appstore_id`) VALUES
(1,	0.00,	'€',	1,	NULL),
(2,	0.00,	'€',	2,	NULL),
(3,	0.00,	'€',	4,	NULL),
(4,	0.00,	'€',	3,	NULL);

DROP TABLE IF EXISTS `Product_has_Category`;
CREATE TABLE `Product_has_Category` (
  `product_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`product_id`,`category_id`),
  KEY `fk_Product_has_Category_Category1` (`category_id`),
  KEY `fk_Product_has_Category_Product1` (`product_id`),
  CONSTRAINT `fk_Product_has_Category_Category1` FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_has_Category_Product1` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `Product_has_Category` (`product_id`, `category_id`) VALUES
(1,	1),
(2,	1),
(1,	2),
(3,	2);

DROP TABLE IF EXISTS `Service`;
CREATE TABLE `Service` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `lastVersion_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_Service_ServiceVersion1` (`lastVersion_id`),
  CONSTRAINT `fk_Service_ServiceVersion1` FOREIGN KEY (`lastVersion_id`) REFERENCES `ServiceVersion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `ServiceVersion`;
CREATE TABLE `ServiceVersion` (
  `id` int(11) NOT NULL,
  `version` varchar(45) DEFAULT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ServiceVersion_Service1` (`service_id`),
  CONSTRAINT `fk_ServiceVersion_Service1` FOREIGN KEY (`service_id`) REFERENCES `Service` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(45) NOT NULL,
  `username` varchar(80) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(50) NOT NULL,
  `provider` varchar(45) NOT NULL,
  `authmethod` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_UNIQUE` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Table containing the information of clients';

INSERT INTO `User` (`id`, `userId`, `username`, `fullname`, `password`, `email`, `provider`, `authmethod`) VALUES
(2,	'115199325219308345825',	'115199325219308345825',	'Issac Noé García',	'ya29.AHES6ZTVfVJxScCPI7zwwimpsmqvf5w_ByGkhXKkokwU0Utr',	'Some(issac@torito.org)',	'google',	'oauth2');

-- 2013-01-18 10:51:31