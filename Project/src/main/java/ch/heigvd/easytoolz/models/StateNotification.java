package ch.heigvd.easytoolz.models;

public enum StateNotification {
    MESSAGE("message"),
    SIGNALEMENT("signalement"),
    RESERVATION("reservation"),
    RACCOURCISSEMENT("raccourcissement"),
    ACCEPTATION_DEMANDE_EMPRUNT("acceptationDemandeEmprunt"),
    REFUS_DEMANDE_EMPRUNT("refusDemandeEmprunt"),
    ACCEPTATION_DEMANDE_RACOURCISSEMENT("acceptationDemandeRacourcissement"),
    REFUS_DEMANDE_RACOURCISSEMENT("refusDemandeRacourcissement");

    private final String state;

    StateNotification(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
