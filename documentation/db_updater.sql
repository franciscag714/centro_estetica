# V1.0 - 2024-07-08
# Original database

# V2.0 - 2024-07-09 
ALTER TABLE `centro_estetica`.`employees` 
ADD COLUMN `is_admin` TINYINT NOT NULL AFTER `email`;

# V3.0 - 2024-09-21
ALTER TABLE `centro_estetica`.`clients` 
CHANGE COLUMN `user` `user` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL ,
CHANGE COLUMN `password` `password` VARCHAR(60) NOT NULL ;

ALTER TABLE `centro_estetica`.`employees` 
CHANGE COLUMN `user` `user` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL ,
CHANGE COLUMN `password` `password` VARCHAR(60) NOT NULL ;
