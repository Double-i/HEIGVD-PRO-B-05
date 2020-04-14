package ch.heigvd.easytoolz.controllers.exceptions.address;

import ch.heigvd.easytoolz.models.Address;

public class AddressFailedStoreException extends RuntimeException {
    public AddressFailedStoreException(){
        super("Failed to store the address");
    }
}
