package ch.heigvd.easytoolz.exceptions.address;

public class AddressFailedStoreException extends RuntimeException {
    public AddressFailedStoreException(){
        super("Failed to store the address");
    }
}
