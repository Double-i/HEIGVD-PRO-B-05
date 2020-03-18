DROP SCHEMA IF EXISTS EasyToolz_Test;
CREATE DATABASE EasyToolz_Test;
USE EasyToolz_Test;

CREATE TABLE `tag` (
  `id` int,
  `name` enum('Bricolage'),
  PRIMARY KEY (`id`)
);

CREATE TABLE `material` (
  `id` int,
  `name` varchar(45),
  `description` varchar(45),
  `User_userName` varchar(255),
  PRIMARY KEY (`id`),
  KEY `FK` (`User_userName`)
);

CREATE TABLE `materialimage` (
  `pathToImg` varchar(255),
  `Material_id` int,
  PRIMARY KEY (`pathToImg`),
  KEY `FK` (`Material_id`)
);

CREATE TABLE `emprunt` (
  `id` int,
  `dateStart` date,
  `dateEnd` date,
  `dateReturn` date,
  `state` enum('Confirme','En attente','Refuse'),
  `User_userName` varchar(255),
  `Material_id` int,
  PRIMARY KEY (`id`),
  KEY `FK` (`User_userName`, `Material_id`)
);

CREATE TABLE `user` (
  `username` varchar(255),
  `firstname` varchar(255),
  `lastname` varchar(255),
  `password` varchar(255),
  `isadmin` tinyint,
  PRIMARY KEY (`userName`)
);

CREATE TABLE `material_tag` (
  `Material_id` int,
  `Tag_id` int,
  KEY `PK,FK` (`Material_id`, `Tag_id`)
);

CREATE TABLE `report` (
  `User_userName` varchar(255),
  `Material_id` int,
  `flag` enum('Racisme','Nudité'),
  KEY `FK` (`User_userName`, `Material_id`)
);

CREATE TABLE `notification` (
  `id` int,
  `message` varchar(45),
  `User_userName` varchar(255),
  PRIMARY KEY (`id`),
  KEY `FK` (`User_userName`)
);

CREATE TABLE `conversation` (
  `userEmprunteur` varchar(255),
  `userPreteur` varchar(255),
  `message` varchar(45),
  KEY `PK,FK` (`userEmprunteur`, `userPreteur`)
);

