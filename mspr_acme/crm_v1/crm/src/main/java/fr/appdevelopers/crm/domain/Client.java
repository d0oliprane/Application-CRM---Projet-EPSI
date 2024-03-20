package fr.appdevelopers.crm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "Client")
@Component
public class Client extends Utilisateur{
    @Column(name = "date_affiliation")
    private Date dateAffiliation;
    @Column(name = "preferencecom")
    private String preferenceCom;


    public Client(int idUtilisateur, String nom, String prenom, String ville, String codePostal, int numeroRue, String libelleRue, String email, String telephone, Date dateAffiliation, String preferenceCom, boolean archive) {
        super(idUtilisateur, nom, prenom, ville, codePostal, numeroRue, libelleRue, email, telephone, archive);
        this.dateAffiliation = dateAffiliation;
        this.preferenceCom = preferenceCom;
    }

    public Client() {
    }

    public Date getDateAffiliation() {
        return dateAffiliation;
    }

    public void setDateAffiliation(Date dateAffiliation) {
        this.dateAffiliation = dateAffiliation;
    }

    public String getPreferenceCom() {
        return preferenceCom;
    }

    public void setPreferenceCom(String preferenceCom) {
        this.preferenceCom = preferenceCom;
    }
}
