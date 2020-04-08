package ch.heigvd.easytoolz.controllers.exceptions.handlers;

import ch.heigvd.easytoolz.controllers.exceptions.EZObjectFormatException;
import ch.heigvd.easytoolz.controllers.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.controllers.exceptions.EZObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Date;
import java.util.Calendar;


/**
 * Handles exceptions thrown by @EZObjectController
 */
@ControllerAdvice
public class EZObjectExceptionHandler extends ResponseEntityExceptionHandler {


    private Date Now()
    {
        return new Date(Calendar.getInstance().getTime().getTime());
    }
    private String getURI(WebRequest request)
    {
        return ((ServletWebRequest)request).getRequest().getRequestURI();
    }


    @ExceptionHandler({EZObjectNotFoundException.class})
    protected ResponseEntity<ApiError> handleEZObjectNotFoundException(EZObjectNotFoundException ex, WebRequest request)
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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> defaultHandler(Exception ex,WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),Now(),getURI(request));
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
