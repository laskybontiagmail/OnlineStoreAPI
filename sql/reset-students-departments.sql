SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `Students`; 
TRUNCATE table `Departments`; 
SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `Departments` AUTO_INCREMENT = 1;
ALTER TABLE `Students` AUTO_INCREMENT = 1;

