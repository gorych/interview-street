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
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(255) NOT NULL COMMENT '',
  `default_answer_count` INT(11) NOT NULL COMMENT '',
  `min_answer_count` INT(11) NOT NULL COMMENT '',
  `icon` VARCHAR(255) NOT NULL COMMENT '',
  `title` VARCHAR(255) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`answer_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`answer_types` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`answer_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(20) NOT NULL COMMENT '',
  `type_id` INT(11) NOT NULL COMMENT '',
  `default_value` VARCHAR(255) NOT NULL COMMENT '',
  `icon` VARCHAR(255) NOT NULL COMMENT '',
  `title` VARCHAR(255) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_answer_types_question_types1_idx` (`type_id` ASC)  COMMENT '',
  CONSTRAINT `fk_answer_types_question_types1`
  FOREIGN KEY (`type_id`)
  REFERENCES `interview_db`.`question_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`interview_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`interview_types` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`interview_types` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(10) NOT NULL DEFAULT 'open' COMMENT 'Может быть два вида опросов: анонимный и открытый',
  `rus_name` VARCHAR(255) NOT NULL COMMENT '',
  `title` VARCHAR(255) NULL DEFAULT NULL COMMENT '',
  `icon` VARCHAR(255) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`posts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`posts` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`posts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(88) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`subdivisions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`subdivisions` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`subdivisions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(150) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`employees`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`employees` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`employees` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `post_id` INT(11) NOT NULL COMMENT '',
  `subdivision_id` INT(11) NOT NULL COMMENT '',
  `firstname` VARCHAR(10) NOT NULL COMMENT '',
  `secondname` VARCHAR(20) NOT NULL COMMENT '',
  `lastname` VARCHAR(25) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_employees_posts1_idx` (`post_id` ASC)  COMMENT '',
  INDEX `fk_employees_subdivisions1_idx` (`subdivision_id` ASC)  COMMENT '',
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
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`users` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `role_id` INT(11) NOT NULL COMMENT '',
  `employee_id` INT(11) NOT NULL COMMENT '',
  `username` VARCHAR(255) NOT NULL COMMENT '',
  `password` VARCHAR(255) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_users_employees1_idx` (`employee_id` ASC)  COMMENT '',
  INDEX `fk_users_user_roles1_idx` (`role_id` ASC)  COMMENT '',
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
-- Table `interview_db`.`interviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`interviews` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`interviews` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `type_id` INT(11) NOT NULL COMMENT '',
  `user_id` INT(11) NOT NULL COMMENT '',
  `name` VARCHAR(100) NOT NULL COMMENT '',
  `introductory_text` VARCHAR(500) NULL DEFAULT 'Здравствуйте, потратьте, пожалуйста, несколько минут своего времени на заполнение следующей анкеты' COMMENT '',
  `description` VARCHAR(100) NOT NULL COMMENT '',
  `goal` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  `audience` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  `placement_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `end_date` DATETIME NOT NULL COMMENT '',
  `hide` TINYINT(1) NULL DEFAULT '0' COMMENT '',
  `second_passage` TINYINT(1) NULL DEFAULT '0' COMMENT '',
  `question_count` INT(11) NULL DEFAULT '0' COMMENT '',
  `hash` VARCHAR(255) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_interviews_interview_types1_idx` (`type_id` ASC)  COMMENT '',
  INDEX `fk_interviews_interview_types_my` (`user_id` ASC)  COMMENT '',
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
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `text` VARCHAR(255) NOT NULL COMMENT '',
  `number` INT(11) NULL DEFAULT '2147483647' COMMENT '',
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `interview_id` INT(11) NOT NULL COMMENT '',
  `type_id` INT(11) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_questions_interviews1_idx` (`interview_id` ASC)  COMMENT '',
  INDEX `FK95C5414DD601F816` (`type_id` ASC)  COMMENT '',
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
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `type_id` INT(11) NOT NULL COMMENT '',
  `text` VARCHAR(255) NOT NULL COMMENT '',
  `question_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_answers_answer_types1_idx` (`type_id` ASC)  COMMENT '',
  INDEX `fk_answers_questions1_idx` (`question_id` ASC)  COMMENT '',
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
-- Table `interview_db`.`expert_interviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`expert_interviews` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`expert_interviews` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `firstname` VARCHAR(45) NOT NULL COMMENT '',
  `lastname` VARCHAR(45) NOT NULL COMMENT '',
  `interview_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_expert_interviews_interviews1_idx` (`interview_id` ASC)  COMMENT '',
  CONSTRAINT `fk_expert_interviews_interviews1`
  FOREIGN KEY (`interview_id`)
  REFERENCES `interview_db`.`interviews` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `interview_db`.`user_answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`user_answers` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`user_answers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `user_id` INT(11) NULL DEFAULT NULL COMMENT '',
  `question_id` INT(11) NOT NULL COMMENT '',
  `interview_id` INT(11) NOT NULL COMMENT '',
  `reply_date` DATETIME NOT NULL COMMENT '',
  `answer_id` INT(11) NOT NULL COMMENT '',
  `answer` VARCHAR(255) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_user_answers_questions1_idx` (`question_id` ASC)  COMMENT '',
  INDEX `fk_user_answers_users1_idx` (`user_id` ASC)  COMMENT '',
  INDEX `fk_user_answers_interviews1_idx` (`interview_id` ASC)  COMMENT '',
  INDEX `fk_user_answers_answers1_idx` (`answer_id` ASC)  COMMENT '',
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
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `interview_id` INT(11) NOT NULL COMMENT '',
  `user_id` INT(11) NOT NULL COMMENT '',
  `isPassed` TINYINT(1) NULL DEFAULT NULL COMMENT '',
  `passing_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_user_interviews_interviews1_idx` (`interview_id` ASC)  COMMENT '',
  INDEX `fk_user_interviews_users1_idx` (`user_id` ASC)  COMMENT '',
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


-- -----------------------------------------------------
-- Table `interview_db`.`published_interviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interview_db`.`published_interviews` ;

CREATE TABLE IF NOT EXISTS `interview_db`.`published_interviews` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `close_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `interview_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`id`, `interview_id`)  COMMENT '',
  INDEX `fk_published_interviews_interviews1_idx` (`interview_id` ASC)  COMMENT '',
  CONSTRAINT `fk_published_interviews_interviews1`
  FOREIGN KEY (`interview_id`)
  REFERENCES `interview_db`.`interviews` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;