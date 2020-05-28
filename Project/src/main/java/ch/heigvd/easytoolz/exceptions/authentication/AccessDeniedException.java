package ch.heigvd.easytoolz.exceptions.authentication;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {
    public AccessDeniedException() {
        super("You're not allowed to do this action");
    }
}
