package ch.heigvd.easytoolz.controllers.exceptions;

import ch.heigvd.easytoolz.models.EZObject;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Date;
import java.util.Calendar;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EZObjectNotFoundException.class})
    protected ResponseEntity<ApiError> handleEZObjectNotFoundException(EZObjectNotFoundException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), new Date(Calendar.getInstance().getTime().getTime()));
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> defaultHandler(Exception ex,WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"an error occured", new Date(Calendar.getInstance().getTime().getTime()));
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
