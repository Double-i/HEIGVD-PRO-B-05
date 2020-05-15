package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.loan.LoanInvalidParameterException;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationFailedStoreException;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationFailedUpdateException;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class NotificationExceptionHandler extends DefaultExceptionHandler{
    @ExceptionHandler({NotificationFailedStoreException.class})
    public ResponseEntity<ApiError> handleNotificationFailedStoreException(NotificationFailedStoreException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.NOT_MODIFIED);
    }
    @ExceptionHandler({NotificationFailedUpdateException.class})
    public ResponseEntity<ApiError> handleNotificationFailedUpdateException(NotificationFailedUpdateException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.NOT_MODIFIED);
    }
    @ExceptionHandler({NotificationNotFoundException.class})
    public ResponseEntity<ApiError> handleNotificationNotFoundException(NotificationNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.NOT_FOUND);
    }

}
