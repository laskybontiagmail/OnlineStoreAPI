CREATE TABLE `Users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(256) NOT NULL,
  `profile_id` int(11) unsigned DEFAULT NULL,
  `user_type` int(11) unsigned DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `password` varchar(256) NOT NULL DEFAULT '',
  `remarks` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERNAME` (`username`),
  KEY `FK_USERPROFILE` (`profile_id`),
  KEY `FK_USERTYPE` (`user_type`),
  CONSTRAINT `FK_USERPROFILE` FOREIGN KEY (`profile_id`) REFERENCES `userprofiles` (`id`),
  CONSTRAINT `FK_USERTYPE` FOREIGN KEY (`user_type`) REFERENCES `usertypes` (`id`)
) ENGINE=InnoDB;
