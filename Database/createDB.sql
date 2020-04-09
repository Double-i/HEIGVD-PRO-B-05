-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema easytoolz
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema easytoolz
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `easytoolz` DEFAULT CHARACTER SET utf8mb4 ;
USE `easytoolz` ;

-- -----------------------------------------------------
-- Table `easytoolz`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `easytoolz`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(40) NOT NULL,
  `fkCountry` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_City_Country1_idx` (`fkCountry` ASC),
  CONSTRAINT `fk_City_Country1`
    FOREIGN KEY (`fkCountry`)
    REFERENCES `easytoolz`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `easytoolz`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NOT NULL,
  `district` VARCHAR(10) NOT NULL,
  `postalCode` VARCHAR(10) NOT NULL,
  `lat` FLOAT NOT NULL,
  `lng` FLOAT NOT NULL,
  `fkCity` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_City1_idx` (`fkCity` ASC),
  CONSTRAINT `fk_address_City1`
    FOREIGN KEY (`fkCity`)
    REFERENCES `easytoolz`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `easytoolz`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`user` (
  `userName` VARCHAR(255) NOT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `isAdmin` TINYINT NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `fkAddress` INT NOT NULL,
  PRIMARY KEY (`userName`),
  INDEX `fk_user_address1_idx` (`fkAddress` ASC),
  CONSTRAINT `fk_user_address1`
    FOREIGN KEY (`fkAddress`)
    REFERENCES `easytoolz`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`conversation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`conversation` (
  `sender` VARCHAR(255) NOT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  `message` VARCHAR(45) NOT NULL,
  INDEX `sender` (`sender` ASC),
  INDEX `recipient` (`recipient` ASC),
  CONSTRAINT `conversation_ibfk_1`
    FOREIGN KEY (`sender`)
    REFERENCES `easytoolz`.`user` (`userName`),
  CONSTRAINT `conversation_ibfk_2`
    FOREIGN KEY (`recipient`)
    REFERENCES `easytoolz`.`user` (`userName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`ezobject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`ezobject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `owner` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `owner` (`owner` ASC),
  CONSTRAINT `ezobject_ibfk_1`
    FOREIGN KEY (`owner`)
    REFERENCES `easytoolz`.`user` (`userName`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`ezobjectimage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`ezobjectimage` (
  `pathToImg` VARCHAR(255) NULL DEFAULT NULL,
  `fkEZObject` INT NOT NULL,
  INDEX `fkEZObject` (`fkEZObject` ASC),
  CONSTRAINT `ezobjectimage_ibfk_1`
    FOREIGN KEY (`fkEZObject`)
    REFERENCES `easytoolz`.`ezobject` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`tag` (
  `pkTag` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`pkTag`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`ezobjecttag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`ezobjecttag` (
  `fkTag` INT NOT NULL,
  `fkEZObject` INT NOT NULL,
  INDEX `fkTag` (`fkTag` ASC),
  INDEX `fkEZObject` (`fkEZObject` ASC),
  CONSTRAINT `ezobjecttag_ibfk_1`
    FOREIGN KEY (`fkTag`)
    REFERENCES `easytoolz`.`tag` (`pkTag`),
  CONSTRAINT `ezobjecttag_ibfk_2`
    FOREIGN KEY (`fkEZObject`)
    REFERENCES `easytoolz`.`ezobject` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`loan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`loan` (
  `pkLoan` INT NOT NULL AUTO_INCREMENT,
  `dateStart` DATE NOT NULL,
  `dateEnd` DATE NOT NULL,
  `dateReturn` DATE NULL DEFAULT NULL,
  `state` ENUM('pending', 'unavailable', 'available') NULL DEFAULT NULL,
  `borrower` VARCHAR(45) NOT NULL,
  `fkEZObject` INT NOT NULL,
  PRIMARY KEY (`pkLoan`),
  INDEX `fkEZObject` (`fkEZObject` ASC),
  INDEX `borrower` (`borrower` ASC),
  CONSTRAINT `loan_ibfk_1`
    FOREIGN KEY (`fkEZObject`)
    REFERENCES `easytoolz`.`ezobject` (`id`),
  CONSTRAINT `loan_ibfk_2`
    FOREIGN KEY (`borrower`)
    REFERENCES `easytoolz`.`user` (`userName`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`notification` (
  `pkNotification` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(45) NOT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  `sender` VARCHAR(255) NOT NULL,
  `notificationRead` TINYINT NOT NULL,
  PRIMARY KEY (`pkNotification`),
  INDEX `recipient` (`recipient` ASC),
  INDEX `sender` (`sender` ASC),
  CONSTRAINT `notification_ibfk_1`
    FOREIGN KEY (`recipient`)
    REFERENCES `easytoolz`.`user` (`userName`),
  CONSTRAINT `notification_ibfk_2`
    FOREIGN KEY (`sender`)
    REFERENCES `easytoolz`.`user` (`userName`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `easytoolz`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `easytoolz`.`report` (
  `fkEZObject` INT NOT NULL,
  `reportWriter` VARCHAR(255) NOT NULL,
  `flag` ENUM('Racisme', 'Nudite') NOT NULL,
  INDEX `fkEZObject` (`fkEZObject` ASC),
  INDEX `reportWriter` (`reportWriter` ASC),
  CONSTRAINT `report_ibfk_1`
    FOREIGN KEY (`fkEZObject`)
    REFERENCES `easytoolz`.`ezobject` (`id`),
  CONSTRAINT `report_ibfk_2`
    FOREIGN KEY (`reportWriter`)
    REFERENCES `easytoolz`.`user` (`userName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
