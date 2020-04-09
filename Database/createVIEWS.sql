CREATE VIEW `EZObjectView` AS 
        SELECT                                               
                ezobject.id as object_id,                     
                ezobject.name as object_name,                 
                ezobject.description as object_description,  
                user.userName as object_owner,               
                address.address as owner_address,             
                address.district as owner_district,           
                address.postalcode as owner_postal_code,      
                address.lat as owner_latitude,                
                address.lng as owner_longitude               
        FROM ezobject                                         
        INNER JOIN user ON ezobject.owner = user.userName      
        INNER JOIN address ON user.fkAddress = address.id;