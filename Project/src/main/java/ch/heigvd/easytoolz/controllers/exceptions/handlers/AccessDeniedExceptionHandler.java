package ch.heigvd.easytoolz.controllers.exceptions.handlers;

import ch.heigvd.easytoolz.controllers.exceptions.errors.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class AccessDeniedExceptionHandler extends DefaultExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError> handleEZObjectNotFoundException(AccessDeniedException ex, WebRequest request)
    {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage(), Now(),getURI(request) );
        return new ResponseEntity<ApiError>(error, new HttpHeaders(),HttpStatus.FORBIDDEN);
    }
}
