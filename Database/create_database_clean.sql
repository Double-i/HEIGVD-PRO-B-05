
DROP SCHEMA IF EXISTS easytoolz;
CREATE SCHEMA IF NOT EXISTS easytoolz;

USE easytoolz;

CREATE TABLE IF NOT EXISTS country(
    id INT PRIMARY KEY AUTO_INCREMENT,
    country VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS city(
    id INT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(40),
    fk_country INT 
);

CREATE TABLE IF NOT EXISTS address(
    id INT PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(255),
    district VARCHAR(10),
    postalCode VARCHAR(10),
    lat DECIMAL(10,7),
    lng DECIMAL(10,7),
    fk_city INT
);

CREATE TABLE IF NOT EXISTS user(
    user_name VARCHAR(255),
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    password VARCHAR(255),
    is_admin TINYINT,
    email VARCHAR(100),
    fk_address INT
);

CREATE TABLE IF NOT EXISTS conversation(
    sender VARCHAR(255),
    recipient VARCHAR(255),
    message VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ezobject(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45),
    description VARCHAR(255),
    owner VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ezobject_image(
    path_to_image VARCHAR(255),
    fk_ez_object INT
);

CREATE TABLE IF NOT EXISTS tag(
    name VARCHAR(45)
);

CREATE TABLE ezobject_tag(
    fk_tag INT,
    fk_ezobject INT
);

CREATE TABLE IF NOT EXISTS loan(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date_start DATE,
    date_end DATE,
    date_return DATE,
    state ENUM('pending','unavailable','available'),
    borrower VARCHAR(255),
    fk_ezobject int
);

CREATE TABLE IF NOT EXISTS notification(
    id INT PRIMARY KEY AUTO_INCREMENT,
    message VARCHAR(255),
    recipient VARCHAR(255),
    sender VARCHAR(255),
    notificatoin_read TINYINT
);

CREATE TABLE IF NOT EXISTS report(
    fk_ezobject INT,
    report_writer VARCHAR(255),
    flag ENUM('Racisme','Nudite')
);