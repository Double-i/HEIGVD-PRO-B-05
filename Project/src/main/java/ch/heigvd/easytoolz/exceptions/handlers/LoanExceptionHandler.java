package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.loan.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class LoanExceptionHandler extends DefaultExceptionHandler {
    @ExceptionHandler({LoanInvalidParameterException.class})
    public ResponseEntity<ApiError> handleLoanInvalidParameterException(LoanInvalidParameterException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({LoanInvalidUserException.class})
    public ResponseEntity<ApiError> handleLoanInvalidUserException(LoanInvalidUserException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({LoanNotFoundException.class})
    public ResponseEntity<ApiError> handleLoanNotFoundException(LoanNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({LoanPeriodAlreadyPassedException.class})
    public ResponseEntity<ApiError> handleLoanAlreadyPassedException(LoanPeriodAlreadyPassedException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({LoanStateCantBeUpdatedException.class})
    public ResponseEntity<ApiError> handleLoanStateCantBeUpdatedException(LoanStateCantBeUpdatedException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.NOT_MODIFIED);
    }
}
