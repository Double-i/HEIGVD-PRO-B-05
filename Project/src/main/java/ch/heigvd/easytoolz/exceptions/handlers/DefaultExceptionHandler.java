package ch.heigvd.easytoolz.exceptions.handlers;

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
     * @return the current timme
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
}
