package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectAlreadyUsed;
import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectFormatException;
import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * Handles exceptions thrown by any exception of EZObjectController
 */
@ControllerAdvice
public class EZObjectExceptionHandler extends DefaultExceptionHandler {

    @ExceptionHandler({EZObjectNotFoundException.class})
    public ResponseEntity<ApiError> handleEZObjectNotFoundException(EZObjectNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EZObjectFormatException.class})
    public ResponseEntity<ApiError> handleEZObjectFormatException(EZObjectFormatException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EZObjectAlreadyUsed.class})
    public ResponseEntity<ApiError> handleEzObjectAlreadyUsed(EZObjectAlreadyUsed ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.IM_USED);
    }
}
