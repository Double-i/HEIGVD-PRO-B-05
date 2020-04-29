package ch.heigvd.easytoolz.exceptions.handlers;

import ch.heigvd.easytoolz.exceptions.FileSystem.FileNotFoundException;
import ch.heigvd.easytoolz.exceptions.FileSystem.FileUploadException;
import ch.heigvd.easytoolz.exceptions.FileSystem.StorageException;
import ch.heigvd.easytoolz.exceptions.errors.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class FileSystemExecptionHandler extends DefaultExceptionHandler{

    @ExceptionHandler({StorageException.class})
    public ResponseEntity<ApiError> handleFileSystemException(StorageException ex, WebRequest request)
    {
        return makeError(ex,request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<ApiError> handleFileNotFoundException(FileNotFoundException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FileUploadException.class})
    public ResponseEntity<ApiError> handleFileUploadException(FileUploadException ex, WebRequest request)
    {
        return makeError(ex,request,HttpStatus.NOT_ACCEPTABLE);
    }
}
