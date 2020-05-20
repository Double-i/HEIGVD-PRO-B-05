package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import ch.heigvd.easytoolz.exceptions.notifications.NotificationFailedStoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class ReportExceptionHandler extends DefaultExceptionHandler {
    @ExceptionHandler({NotificationFailedStoreException.class})
    public ResponseEntity<ApiError> handleNotificationFailedStoreException(NotificationFailedStoreException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.BAD_REQUEST);
    }
}
