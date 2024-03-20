package fr.appdevelopers.crm.domain;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Utilisateur")
@Inheritance(strategy=InheritanceType.JOINED)
@Component
public abstract class

Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private int idUtilisateur;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "ville")
    private String ville;
    @Column(name = "code_postal")
    private String codePostal;
    @Column(name = "numero_rue")
    private int numeroRue;
    @Column(name = "libelle_rue")
    private String libelleRue;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "archive")
    private boolean archive;

    public Utilisateur(int idUtilisateur, String nom, String prenom, String ville, String codePostal, int numeroRue, String libelleRue, String email, String telephone, boolean archive) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.codePostal = codePostal;
        this.numeroRue = numeroRue;
        this.libelleRue = libelleRue;
        this.email = email;
        this.telephone = telephone;
        this.archive = archive;
    }
    public Utilisateur() {
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public int getNumeroRue() {
        return numeroRue;
    }

    public void setNumeroRue(int numeroRue) {
        this.numeroRue = numeroRue;
    }

    public String getLibelleRue() {
        return libelleRue;
    }

    public void setLibelleRue(String libelleRue) {
        this.libelleRue = libelleRue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;}

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean getArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
