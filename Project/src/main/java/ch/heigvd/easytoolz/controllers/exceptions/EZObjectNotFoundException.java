package ch.heigvd.easytoolz.controllers.exceptions;

import javax.persistence.EntityNotFoundException;

public class EZObjectNotFoundException extends EntityNotFoundException {
    public EZObjectNotFoundException(int id)
    {
        super("Object not found "+ id);
    }
}
