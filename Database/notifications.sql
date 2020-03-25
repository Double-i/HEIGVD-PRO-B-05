-- TRIGGERS INSERT INTO NOTIFICATION
USE easytoolz;

-- WHEN new message into conversation
CREATE TRIGGER new_message AFTER INSERT ON conversation
FOR EACH ROW 
INSERT INTO notification(message,recipient,sender,notificationRead) VALUES("You've got a new message",NEW.recipient,NEW.sender,0);

delimiter $$
-- WHEN update loan to pending
CREATE TRIGGER new_loan AFTER INSERT ON loan
FOR EACH ROW 
BEGIN
SET @sender = (SELECT owner FROM EZObject WHERE pkObject = NEW.fkEZObject);
INSERT INTO notification(message,recipient,sender,notificationRead) VALUES("Someone Want's to borrow an object",NEW.borrower,@sender,0);
END $$
delimiter ;

-- WHEN update loann to available
delimiter $$
CREATE TRIGGER loan_available AFTER UPDATE ON loan
FOR EACH ROW
BEGIN
SET @sender = (SELECT owner FROM EZObject WHERE pkEZObject = NEW.fkEZObject);
INSERT INTO notification(message,recipient,sender,notificationRead) VALUES("An item you want to borrow is available now",NEW.borrower,@sender,0);
END $$
delimiter ;

-- WHEN an object is available?


DELIMITER $$
CREATE PROCEDURE check_recipient( sender VARCHAR(45), recipient VARCHAR(45))
BEGIN
    DECLARE message VARCHAR(255);
    IF sender = recipient  THEN
        SET message = 'recipient cannot be the same as sender';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = message;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER before_notification_sent
BEFORE INSERT
ON notification FOR EACH ROW
BEGIN
    CALL check_recipient(NEW.sender,NEW.recipient);
END$$
DELIMITER ;