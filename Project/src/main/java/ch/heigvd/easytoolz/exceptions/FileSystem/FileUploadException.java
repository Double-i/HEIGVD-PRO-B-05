package ch.heigvd.easytoolz.exceptions.FileSystem;

public class FileUploadException extends StorageException{
    public FileUploadException(String message)
    {
        super(message);
    }

    public FileUploadException(String message, Throwable cause)
    {
        super(message,cause);
    }
}

