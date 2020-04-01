DROP SCHEMA IF EXISTS EasyToolz_Test;
CREATE DATABASE EasyToolz_Test;
USE EasyToolz_Test;

CREATE TABLE `tag` (
  `ID` int,
  `name` varchar(10),
  PRIMARY KEY (`ID`)
);

CREATE TABLE `material` (
  `ID` int,
  `name` varchar(45),
  `description` varchar(45),
  `owner` varchar(45),
  `tagID` int,
  PRIMARY KEY (`ID`),
  KEY `FK` (`owner`, `tagID`)
);

CREATE TABLE `materialImage` (
  `pathToImg` varchar(255),
  `materialID` int,
  PRIMARY KEY (`pathToImg`),
  KEY `FK` (`materialID`)
);

CREATE TABLE `loan` (
  `ID` int,
  `dateStart` date,
  `dateEnd` date,
  `dateReturn` date,
  `state` enum('pending','unavailable','available'),
  `borrower` varchar(45),
  `loaner` varchar(45),
  `materialID` int,
  PRIMARY KEY (`ID`),
  KEY `FK` (`borrower`, `loaner`, `materialID`)
);

CREATE TABLE `user` (
  `username` varchar(255),
  `firstname` varchar(255),
  `lastname` varchar(255),
  `password` varchar(255),
  `isadmin` tinyint,
  PRIMARY KEY (`userName`)
);

CREATE TABLE `materialTag` (
  `tagID` int,
  `materialID` int,
  KEY `PK,FK` (`tagID`, `materialID`)
);

CREATE TABLE `report` (
  `materialID` int,
  `reportWriter` varchar(255),
  `flag` enum('Racisme','Nudite'),
  KEY `FK` (`materialID`, `reportWriter`)
);

CREATE TABLE `notification` (
  `ID` int,
  `message` varchar(45),
  `recipient` varchar(255),
  PRIMARY KEY (`ID`),
  KEY `FK` (`recipient`)
);

CREATE TABLE `conversation` (
  `userEmprunteur` varchar(255),
  `userPreteur` varchar(255),
  `message` varchar(45),
  KEY `PK,FK` (`userEmprunteur`, `userPreteur`)
);

