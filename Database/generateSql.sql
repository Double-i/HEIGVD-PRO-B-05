DROP DATABASE IF EXISTS easytoolz;

CREATE DATABASE easytoolz;

USE easytoolz;

CREATE TABLE `tag` (
  `ID` int NOT NULL auto_increment,
  `name` varchar(10)NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE `user` (
  `userName` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isAdmin` tinyint NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (userName)
);


CREATE TABLE `EZObject` (
  `ID` int NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `description` varchar(45),
  `owner` varchar(45)NOT NULL,
  `tagID` int NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (tagID) REFERENCES tag(ID),
  FOREIGN KEY (owner) REFERENCES user(userName)
);

CREATE TABLE `EZObjectImage` (
  `pathToImg` varchar(255),
  `EZObjectID` int NOT NULL,
  FOREIGN KEY (EZObjectID) REFERENCES EZObject(ID)
);

CREATE TABLE `EZObectTag` (
  `tagID` int NOT NULL,
  `EZObjectID` int NOT NULL,
  FOREIGN KEY (tagID) REFERENCES tag(ID),
  FOREIGN KEY (EZObjectID) REFERENCES EZObject(ID)
);

CREATE TABLE `loan` (
  `ID` int NOT NULL,
  `dateStart` date NOT NULL,
  `dateEnd` date NOT NULL,
  `dateReturn` date NOT NULL,
  `state` enum('pending','unavailable','available'),
  `borrower` varchar(45) NOT NULL,
  `loaner` varchar(45) NOT NULL,
  `EZObjectID` int NOT NULL,
  PRIMARY KEY(ID),
  FOREIGN KEY (EZObjectID) REFERENCES EZOBject(ID),
  FOREIGN KEY (borrower) REFERENCES user(userName),
  FOREIGN KEY(loaner) REFERENCES user(userName)
);



CREATE TABLE `report` (
  `EZObjectID` int NOT NULL,
  `reportWriter` varchar(255) NOT NULL,
  `flag` enum('Racisme','Nudite') NOT NULL,
  FOREIGN KEY (EZObjectID) REFERENCES EZObject(ID),
  FOREIGN KEY(reportWriter) REFERENCES user(userName)
);

CREATE TABLE `notification` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `message` varchar(45) NOT NULL,
  `recipient` varchar(255) NOT NULL,
  PRIMARY KEY(ID),
  FOREIGN KEY (recipient) REFERENCES user(userName)
);

CREATE TABLE `conversation` (
  `borrower` varchar(255) NOT NULL,
  `loaner` varchar(255) NOT NULL,
  `message` varchar(45) NOT NULL,
  FOREIGN KEY(borrower) REFERENCES user(userName),
  FOREIGN KEY(loaner) REFERENCES user(userName)
);

