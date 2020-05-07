package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.loan.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class LoanExceptionHandler extends DefaultExceptionHandler{

    @ExceptionHandler({LoanInvalidParameterException.class})
    ResponseEntity<ApiError> handleLoanInvalidParameterException(LoanInvalidParameterException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LoanInvalidParameterException.class})
    ResponseEntity<ApiError> handleLoanInvalidUserException(LoanInvalidUserException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LoanNotFoundException.class})
    ResponseEntity<ApiError> handleLoanNotFoundException(LoanNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({LoanNotFoundException.class})
    ResponseEntity<ApiError> handleLoanPeriodAlreadyPassedException(LoanPeriodAlreadyPassedException ex,WebRequest request)
    {
        return makeError(ex, request, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({LoanStateCantBeUpdatedException.class})
    ResponseEntity<ApiError> handleLoanStateCantBeUpdatedException(LoanStateCantBeUpdatedException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.NOT_ACCEPTABLE);
    }



}
