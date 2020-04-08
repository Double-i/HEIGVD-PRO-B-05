package ch.heigvd.easytoolz.controllers.exceptions.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy@HH:mm:ss")
    Date date;
    HttpStatus code;
    String message;

    String url;

    public ApiError(HttpStatus code, String message,Date date,String url )
    {
        this.date = date;
        this.code = code;
        this.message  = message;
        this.url = url;
    }
}
