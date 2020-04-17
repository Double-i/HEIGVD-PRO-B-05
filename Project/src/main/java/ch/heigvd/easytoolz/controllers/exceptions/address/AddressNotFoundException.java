package ch.heigvd.easytoolz.controllers.exceptions.address;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(int id){
        super("Address " + id + " not found");
    }
}
