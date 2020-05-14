package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AccessDeniedExceptionHandler extends DefaultExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError> handleEZObjectNotFoundException(AccessDeniedException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.FORBIDDEN);

    }
}
