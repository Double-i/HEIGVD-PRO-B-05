package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Date;
import java.util.Calendar;

/**
 * Handles exeptions thrown by any exception
 */

public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * @return the current time
     */
    protected static Date Now()
    {
        return new Date(Calendar.getInstance().getTime().getTime());
    }

    /**
     * @param request WebRequet to treat
     * @return the URI of the request
     */
    protected static String getURI(WebRequest request)
    {
        return ((ServletWebRequest)request).getRequest().getRequestURI();
    }


    protected static ResponseEntity<ApiError> makeError(Exception ex, WebRequest request, HttpStatus status)
    {
        ApiError error = new ApiError(status,ex.getMessage(),Now(),getURI(request));
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleDefaultException(Exception ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), Now(),getURI(request) );
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
