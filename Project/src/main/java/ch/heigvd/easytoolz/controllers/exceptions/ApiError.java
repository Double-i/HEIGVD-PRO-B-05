package ch.heigvd.easytoolz.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class ApiError {

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    /**
     * Error status
     * 4xx Client error
     * 5xx Server error
     */
    private HttpStatus status;

    /**
     * Date time of the error occurence
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    /**
     * user friendly message about the error
     */
    private String message;

    /**
     * System message describing the error
     */
    private String debugMessage;


    private ApiError()
    {
        timeStamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status)
    {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex)
    {
        this();
        this.status = status;
        this.message = "Unexpected Error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex)
    {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}

