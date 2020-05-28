package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.ezobject.EZObjectNotFoundException;
import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UserExceptionHandler extends DefaultExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(EZObjectNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyPresent.class)
    public ResponseEntity<ApiError> handleUserAlreadyPresentException(EZObjectNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.IM_USED);

    }

    @ExceptionHandler(UserFailedStoreException.class)
    public ResponseEntity<ApiError> handleUserFailedStoreException(EZObjectNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.BAD_REQUEST);
    }
}
