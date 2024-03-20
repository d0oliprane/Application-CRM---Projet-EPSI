package fr.appdevelopers.crm.domain;

public enum StatutLivraison {
    EN_PREPARATION ("En préparation"),
    EN_COURS_ACHEMINEMENT ("En préparation"),
    LIVREE ("En préparation");
    private String statut;
    private StatutLivraison(String statut){
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
