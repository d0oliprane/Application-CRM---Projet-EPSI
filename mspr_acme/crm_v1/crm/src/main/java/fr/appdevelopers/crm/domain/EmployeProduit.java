package fr.appdevelopers.crm.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Employe_Produit")
public class EmployeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employe_produit")
    private int idEmployeProduit;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Employe employe;


    public EmployeProduit() {
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}
