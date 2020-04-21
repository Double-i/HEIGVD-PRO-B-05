package ch.heigvd.easytoolz.exceptions.ezobject;

public class EZObjectAlreadyUsed extends RuntimeException {
    public EZObjectAlreadyUsed(String message)
    {
        super("EZObject already used :"+ message);
    }
}
