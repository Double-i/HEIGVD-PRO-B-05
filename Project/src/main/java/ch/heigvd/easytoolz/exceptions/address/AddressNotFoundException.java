package ch.heigvd.easytoolz.exceptions.address;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(int id){
        super("Address " + id + " not found");
    }
}
