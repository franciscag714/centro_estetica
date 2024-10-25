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

# V4.0 - 2024-10-25
ALTER TABLE `centro_estetica`.`attentionts` 
RENAME TO  `centro_estetica`.`attentions` ;

ALTER TABLE `centro_estetica`.`attentions` 
DROP FOREIGN KEY `FK_appointments_attentionts`,
DROP FOREIGN KEY `FK_services_attentionts`;

ALTER TABLE `centro_estetica`.`attentions` 
ADD CONSTRAINT `FK_appointments_attentions`
  FOREIGN KEY (`appointment_id`)
  REFERENCES `centro_estetica`.`appointments` (`id`),
ADD CONSTRAINT `FK_services_attentions`
  FOREIGN KEY (`service_id`)
  REFERENCES `centro_estetica`.`services` (`id`);

ALTER TABLE `centro_estetica`.`attentions` 
ADD COLUMN `price` DECIMAL(10,2) NOT NULL AFTER `service_id`;