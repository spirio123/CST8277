CREATE SCHEMA IF NOT EXISTS `demo` DEFAULT CHARACTER SET utf8mb4 ;
USE `demo` ;
CREATE TABLE IF NOT EXISTS `demo`.`user_table` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `demo`.`security_user` (
  `id` INT NOT NULL,
  `password_hash` VARCHAR(256) NULL,
  `user_name` VARCHAR(100) NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_security_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `demo`.`user_table` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `demo`.`security_role` (
  `role_id` INT NOT NULL,
  `role_name` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `demo`.`users_have_roles` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  CONSTRAINT `fk_security_user_has_security_role_security_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `demo`.`security_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_security_user_has_security_role_security_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `demo`.`security_role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;