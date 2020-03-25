DROP DATABASE IF EXISTS easytoolz;

CREATE DATABASE easytoolz;

USE easytoolz;

CREATE TABLE tag (
  pkTag int NOT NULL auto_increment,
  name varchar(20)NOT NULL,
  PRIMARY KEY(pkTag)
);

CREATE TABLE user (
  userName varchar(255) NOT NULL,
  firstName varchar(255) NOT NULL,
  lastName varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  isAdmin tinyint NOT NULL,
  email varchar(255) NOT NULL,
  PRIMARY KEY (userName)
);

CREATE TABLE Localisation(
	pkLocalisation INT NOT NULL auto_increment,
    longitude FLOAT NOT NULL,
    latitude FLOAT NOT NULL,
    PRIMARY KEY (pkLocalisation)
);

CREATE TABLE EZObject (
  pkObject int NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  description varchar(45),
  owner varchar(45)NOT NULL,
  localisation INT NULL,
  PRIMARY KEY (pkObject),
  FOREIGN KEY (owner) REFERENCES user(userName),
  FOREIGN KEY(localisation)  REFERENCES Localisation(pkLocalisation)
);



CREATE TABLE EZObjectImage (
  pathToImg varchar(255),
  fkEZObject int NOT NULL,
  FOREIGN KEY (fkEZObject) REFERENCES EZObject(pkObject)
);

CREATE TABLE EZObjectTag (
  fkTag int NOT NULL,
  fkEZObject int NOT NULL,
  FOREIGN KEY (fkTag) REFERENCES tag(pkTag),
  FOREIGN KEY (fkEZObject) REFERENCES EZObject(pkObject)
);

CREATE TABLE loan (
  pkLoan int NOT NULL AUTO_INCREMENT,
  dateStart date NOT NULL,
  dateEnd date NOT NULL,
  dateReturn date ,
  state enum('pending','unavailable','available'),
  borrower varchar(45) NOT NULL,
  fkEZObject int NOT NULL,
  PRIMARY KEY(pkLoan),
  FOREIGN KEY (fkEZObject) REFERENCES EZOBject(pkObject),
  FOREIGN KEY (borrower) REFERENCES user(userName)
  );



CREATE TABLE report (
  fkEZObject int NOT NULL,
  reportWriter varchar(255) NOT NULL,
  flag enum('Racisme','Nudite') NOT NULL,
  FOREIGN KEY (fkEZObject) REFERENCES EZObject(pkObject),
  FOREIGN KEY(reportWriter) REFERENCES user(userName)
);

CREATE TABLE notification (
  pkNotification int NOT NULL AUTO_INCREMENT,
  message varchar(45) NOT NULL,
  recipient varchar(255) NOT NULL,
  sender varchar(255) NOT NULL,
  notificationRead tinyint NOT NULL,
  PRIMARY KEY(pkNotification),
  FOREIGN KEY (recipient) REFERENCES user(userName),
  FOREIGN KEY (sender) REFERENCES user(userName)
);

CREATE TABLE conversation (
  sender varchar(255) NOT NULL,
  recipient varchar(255) NOT NULL,
  message varchar(45) NOT NULL,
  FOREIGN KEY(sender) REFERENCES user(userName),
  FOREIGN KEY(recipient) REFERENCES user(userName)
);

