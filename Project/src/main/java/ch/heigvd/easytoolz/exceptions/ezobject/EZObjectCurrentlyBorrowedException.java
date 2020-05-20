package ch.heigvd.easytoolz.exceptions.ezobject;

public class EZObjectCurrentlyBorrowedException extends RuntimeException {
    public EZObjectCurrentlyBorrowedException(int objectId){
        super("The object " + objectId + " is currently borrowed");
    }
}
