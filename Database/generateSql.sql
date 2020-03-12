DROP SCHEMA IF EXISTS EasyToolZ;
CREATE DATABASE EasyToolz;
USE EasyToolz;

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
  `userName` varchar(255),
  `firstName` varchar(45),
  `lastName` varchar(45),
  `password` varchar(45),
  `isadmin` tinyint,
  `profilImg` varchar(45),
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

