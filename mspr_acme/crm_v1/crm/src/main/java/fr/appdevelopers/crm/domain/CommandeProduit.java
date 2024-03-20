package fr.appdevelopers.crm.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Commande_Produit")
public class CommandeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_produit")
    private int idCommandeProduit;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @Column(name = "quantite")
    private int quantite;



    public CommandeProduit() {
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}
