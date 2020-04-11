CREATE VIEW `objectView` AS 
	SELECT ezobject.name,ezobject.description,user.user_name,address.address,address.district,address.postalcode,address.lat,address.lng
    FROM ezobject
    INNER JOIN user ON ezobject.owner = user.user_name
    INNER JOIN address ON user.fk_address = address.id;