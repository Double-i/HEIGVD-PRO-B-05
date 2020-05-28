package ch.heigvd.easytoolz.config;

import java.security.Principal;

public class StompPrincipal implements Principal {
    private final String name;

    StompPrincipal(String name){
        this.name = name;
    }

    /**
     * @return the name of the stomp
     */
    @Override
    public String getName() {
        return this.name;
    }
}
