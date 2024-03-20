package fr.appdevelopers.crm.domain;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Employe")
@Component
public class Employe extends Utilisateur{

    @Column(name = "mot_de_passe")
    private String motDePasse;
    @Column(name = "matricule")
    private String matricule;
    @Column(name = "date_embauche")
    private Date dateEmbauche;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


    public Employe(int idUtilisateur, String nom, String prenom, String ville, String codePostal, int numeroRue, String libelleRue, String email, String telephone, String motDePasse, String matricule, Date dateEmbauche, Role role, boolean archive) {
        super(idUtilisateur, nom, prenom, ville, codePostal, numeroRue, libelleRue, email, telephone, archive);
        this.motDePasse = motDePasse;
        this.matricule = matricule;
        this.dateEmbauche = dateEmbauche;
        this.role = role;
    }

    public Employe() {
    }


    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



}
