-- TRIGGERS INSERT INTO NOTIFICATION
USE easytoolz;

-- WHEN new message into conversation
CREATE TRIGGER new_message AFTER INSERT ON conversation
FOR EACH ROW 
INSERT INTO notification(message,recipient) VALUES("You've got a new message",conversation.userEmprunteur);

-- WHEN update loan to pending
CREATE TRIGGER new_loan AFTER INSERT ON loan
FOR EACH ROW 
INSERT INTO notification(message,recipient) VALUES("Someone Want's to borrow an object",loan.borrower);

-- WHEN update loann to available
CREATE TRIGGER loan_available AFTER UPDATE ON loan
FOR EACH ROW
INSERT INTO notification(message,recipient) VALUES("An item you want to borrow is available now",loan.borrower);

-- WHEN an object is available?