package ch.heigvd.easytoolz.controllers.exceptions;

import org.springframework.http.HttpStatus;

import java.sql.Date;

public class ApiError {

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    Date date;
    HttpStatus code;
    String message;

    ApiError(HttpStatus code, String message,Date date)
    {
        this.date = date;
        this.code = code;
        this.message  = message;
    }
}
