package ch.heigvd.easytoolz.controllers.exceptions;

import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handles bad http response
     * @param ex
     * @return
     */
    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    public  ResponseEntity<ApiError>  handleHttpMessageNotReadableException(Exception ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.NO_CONTENT,"Bad request",ex);
        return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
    }*/

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(Exception ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.NO_CONTENT,ex.getLocalizedMessage(),ex);
        return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
    }

}
