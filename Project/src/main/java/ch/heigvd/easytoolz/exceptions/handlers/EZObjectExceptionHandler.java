package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectFormatException;
import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import org.springframework.http.HttpHeaders;
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
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), Now(),getURI(request) );
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EZObjectFormatException.class})
    public ResponseEntity<ApiError> handleEZObjectFormatException(EZObjectFormatException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),Now(),getURI(request));
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), Now(), getURI(request));
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.NOT_FOUND);
    }


}
