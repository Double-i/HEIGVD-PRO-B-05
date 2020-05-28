package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.address.AddressFailedStoreException;
import ch.heigvd.easytoolz.exceptions.address.AddressNotFoundException;
import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AddressExceptionHandler extends DefaultExceptionHandler {

    @ExceptionHandler({AddressFailedStoreException.class})
    public ResponseEntity<ApiError> handleAdressFailedStoreException(AddressFailedStoreException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<ApiError> handleAdressNotFoundException(AddressNotFoundException ex, WebRequest request)
    {
        return makeError(ex, request, HttpStatus.NOT_FOUND);
    }
}
