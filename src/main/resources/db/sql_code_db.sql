-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema interview_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema interview_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `interview_db` DEFAULT CHARACTER SET utf8 ;
USE `interview_db` ;

-- -----------------------------------------------------
-- Table `interview_db`.`question_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`question_types` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`question_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `default_answer_count` INT(11) NOT NULL,
  `min_answer_count` INT(11) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`answer_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`answer_types` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`answer_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `type_id` INT(11) NOT NULL,
  `default_value` VARCHAR(255) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_answer_types_question_types1_idx` (`type_id` ASC),
  CONSTRAINT `fk_answer_types_question_types1`
    FOREIGN KEY (`type_id`)
    REFERENCES `interview_db`.`question_types` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`interview_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`interview_types` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`interview_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(10) NOT NULL DEFAULT 'open' COMMENT 'Может быть два вида опросов: анонимный и открытый',
  `rus_name` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `icon` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`interviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`interviews` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`interviews` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `introductory_text` VARCHAR(500) NULL DEFAULT 'Здравствуйте, потратьте, пожалуйста, несколько минут своего времени на заполнение следующей анкеты',
  `description` VARCHAR(100) NOT NULL,
  `goal` VARCHAR(100) NULL DEFAULT NULL,
  `audience` VARCHAR(100) NULL DEFAULT NULL,
  `placement_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` DATE NOT NULL,
  `hide` TINYINT(1) NULL DEFAULT '0',
  `question_count` INT(11) NULL DEFAULT '0',
  `hash` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_interviews_interview_types1_idx` (`type_id` ASC),
  CONSTRAINT `fk_interviews_interview_types1`
    FOREIGN KEY (`type_id`)
    REFERENCES `interview_db`.`interview_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_interviews_interview_types_my`
    FOREIGN KEY (`user_id`)
    REFERENCES `interview_db`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`questions` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`questions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(255) NOT NULL,
  `number` INT(11) NULL DEFAULT '2147483647',
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `interview_id` INT(11) NOT NULL,
  `type_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_questions_interviews1_idx` (`interview_id` ASC),
  INDEX `FK95C5414DD601F816` (`type_id` ASC),
  CONSTRAINT `FK95C5414DD601F816`
    FOREIGN KEY (`type_id`)
    REFERENCES `interview_db`.`question_types` (`id`),
  CONSTRAINT `fk_questions_interviews1`
    FOREIGN KEY (`interview_id`)
    REFERENCES `interview_db`.`interviews` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`answers` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`answers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type_id` INT(11) NOT NULL,
  `text` VARCHAR(255) NOT NULL,
  `question_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_answers_answer_types1_idx` (`type_id` ASC),
  INDEX `fk_answers_questions1_idx` (`question_id` ASC),
  CONSTRAINT `fk_answers_answer_types1`
    FOREIGN KEY (`type_id`)
    REFERENCES `interview_db`.`answer_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_answers_questions1`
    FOREIGN KEY (`question_id`)
    REFERENCES `interview_db`.`questions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`posts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`posts` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`posts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(88) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`subdivisions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`subdivisions` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`subdivisions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`employees`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`employees` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`employees` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `post_id` INT(11) NOT NULL,
  `subdivision_id` INT(11) NOT NULL,
  `firstname` VARCHAR(10) NOT NULL,
  `secondname` VARCHAR(20) NOT NULL,
  `lastname` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employees_posts1_idx` (`post_id` ASC),
  INDEX `fk_employees_subdivisions1_idx` (`subdivision_id` ASC),
  CONSTRAINT `fk_employees_posts1`
    FOREIGN KEY (`post_id`)
    REFERENCES `interview_db`.`posts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_employees_subdivisions1`
    FOREIGN KEY (`subdivision_id`)
    REFERENCES `interview_db`.`subdivisions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`user_roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`users` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_id` INT(11) NOT NULL,
  `employee_id` INT(11) NOT NULL,
  `passportData` VARCHAR(9) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_users_employees1_idx` (`employee_id` ASC),
  INDEX `fk_users_user_roles1_idx` (`role_id` ASC),
  CONSTRAINT `fk_users_employees1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `interview_db`.`employees` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_user_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `interview_db`.`user_roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`user_answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`user_answers` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`user_answers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NULL DEFAULT NULL,
  `question_id` INT(11) NOT NULL,
  `interview_id` INT(11) NOT NULL,
  `reply_date` DATE NOT NULL,
  `answer_id` INT(11) NOT NULL,
  `answer` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_answers_questions1_idx` (`question_id` ASC),
  INDEX `fk_user_answers_users1_idx` (`user_id` ASC),
  INDEX `fk_user_answers_interviews1_idx` (`interview_id` ASC),
  INDEX `fk_user_answers_answers1_idx` (`answer_id` ASC),
  CONSTRAINT `fk_user_answers_answers1`
    FOREIGN KEY (`answer_id`)
    REFERENCES `interview_db`.`answers` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_answers_interviews1`
    FOREIGN KEY (`interview_id`)
    REFERENCES `interview_db`.`interviews` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_answers_questions1`
    FOREIGN KEY (`question_id`)
    REFERENCES `interview_db`.`questions` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_answers_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `interview_db`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`user_interviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`user_interviews` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`user_interviews` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `interview_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `isPassed` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_interviews_interviews1_idx` (`interview_id` ASC),
  INDEX `fk_user_interviews_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_interviews_interviews1`
    FOREIGN KEY (`interview_id`)
    REFERENCES `interview_db`.`interviews` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_interviews_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `interview_db`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;