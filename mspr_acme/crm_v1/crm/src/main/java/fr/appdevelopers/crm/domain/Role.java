package fr.appdevelopers.crm.domain;

public enum Role {
    ADMIN ("ADMIN"),
    RESPONSABLE ("RESPONSABLE"),
    EMPLOYE ("EMPLOYE");
    private String role;
    private Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }


}
