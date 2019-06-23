CREATE TABLE `UserTypes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(256) NOT NULL DEFAULT '',
  `type_value` varchar(256) NOT NULL DEFAULT '',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remarks` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_TYPENAME` (`type_name`)
) ENGINE=InnoDB;
