CREATE VIEW `objectView` AS 
	SELECT ezobject.name,ezobject.description,user.userName,address.address,address.district,address.postalcode,address.lat,address.lng
    FROM ezobject
    INNER JOIN user ON ezobject.owner = user.userName
    INNER JOIN address ON user.fkAddress = address.id;