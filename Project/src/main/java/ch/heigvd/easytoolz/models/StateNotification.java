package ch.heigvd.easytoolz.models;

public enum StateNotification {
    MESSAGE("message", "Vous avez reçu un message de l'utilisateur '%s'"),
    SIGNALEMENT("signalement", "Votre objet 'x' a été signalé par un utilisateur. Causes : %s"),
    RESERVATION("reservation", "L'utilisateur '%s' souhaite vous emprunter l'outil '%s'"),
    ACCEPTATION_DEMANDE_EMPRUNT("acceptationDemandeEmprunt", "Votre demande d'emprunt pour l'outil '%s' a été acceptée"),
    REFUS_DEMANDE_EMPRUNT("refusDemandeEmprunt", "Votre demande d'emprunt pour l'outil '%s' a été refusée"),
    RACCOURCISSEMENT_OWNER("raccourcissement", "Une demande de racourcissement a été faite sur l'outil '%s'"),
    RACCOURCISSEMENT_BORROWER("raccourcissement", "Une demande de racourcissement a été faite sur l'outil '%s'"),
    ACCEPTATION_DEMANDE_RACOURCISSEMENT_OWNER("acceptationDemandeRacourcissement", "Votre demande racourcissement pour l'outil '%s' a été acceptée"),
    ACCEPTATION_DEMANDE_RACOURCISSEMENT_BORROWER("acceptationDemandeRacourcissement", "Votre demande racourcissement pour l'outil '%s' a été acceptée"),
    REFUS_DEMANDE_RACOURCISSEMENT_OWNER("refusDemandeRacourcissement", "Votre demande racourcissement pour l'outil '%s' a été refusée"),
    REFUS_DEMANDE_RACOURCISSEMENT_BORROWER("refusDemandeRacourcissement", "Votre demande racourcissement pour l'outil '%s' a été refusée");


    private final String name;
    private final String message;

    StateNotification(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
