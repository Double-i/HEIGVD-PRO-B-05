
    
CREATE VIEW `EZObjectView` AS 
        SELECT                                               
                ezobject.id as object_id,                     
                ezobject.name as object_name,                 
                ezobject.description as object_description,  
                user.user_name as object_owner,               
                address.address as owner_address,             
                address.district as owner_district,           
                address.postalcode as owner_postal_code,      
                address.lat as owner_latitude,                
                address.lng as owner_longitude               
        FROM ezobject                                         
        INNER JOIN user ON ezobject.owner = user.user_name      
        INNER JOIN address ON user.fk_address = address.id;