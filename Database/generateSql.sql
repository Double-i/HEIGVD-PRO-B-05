DROP SCHEMA IF EXISTS EasyToolZ;
CREATE DATABASE EasyToolz;
USE EasyToolz;

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
  `userName` varchar(45),
  `firstName` varchar(45),
  `lastName` varchar(45),
  `password` varchar(45),
  `isAdmin` tinyint,
  `profilImg` varchar(45),
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

