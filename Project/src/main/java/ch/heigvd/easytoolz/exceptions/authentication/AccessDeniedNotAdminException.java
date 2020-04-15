package ch.heigvd.easytoolz.exceptions.authentication;

import org.springframework.security.access.AccessDeniedException;

public class AccessDeniedNotAdminException extends AccessDeniedException {
    public AccessDeniedNotAdminException() {
        super("You're not allowed to do this action because you're not admin");
    }
}
