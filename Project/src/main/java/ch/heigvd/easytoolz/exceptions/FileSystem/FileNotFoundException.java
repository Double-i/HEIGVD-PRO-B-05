package ch.heigvd.easytoolz.exceptions.FileSystem;

public class FileNotFoundException extends StorageException {

    FileNotFoundException(String message)
    {
        super(message);
    }

    FileNotFoundException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
