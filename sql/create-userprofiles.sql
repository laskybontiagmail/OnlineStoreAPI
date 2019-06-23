CREATE TABLE `UserProfiles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(128) NOT NULL DEFAULT '',
  `middlename` varchar(128) DEFAULT '',
  `lastname` varchar(128) NOT NULL DEFAULT '',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remarks` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_FULLNAME` (`firstname`,`middlename`,`lastname`)
) ENGINE=InnoDB AUTO_INCREMENT=1;
