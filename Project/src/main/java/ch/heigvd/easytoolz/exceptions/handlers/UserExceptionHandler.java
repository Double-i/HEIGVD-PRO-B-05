package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class UserExceptionHandler extends DefaultExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(EZObjectNotFoundException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), Now(),getURI(request) );
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyPresent.class)
    public ResponseEntity<ApiError> handleUserAlreadyPresentException(EZObjectNotFoundException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.IM_USED, ex.getMessage(), Now(),getURI(request) );
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.IM_USED);
    }

    @ExceptionHandler(UserFailedStoreException.class)
    public ResponseEntity<ApiError> handleUserFailedStoreException(EZObjectNotFoundException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), Now(),getURI(request) );
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.NOT_FOUND);
    }
}
