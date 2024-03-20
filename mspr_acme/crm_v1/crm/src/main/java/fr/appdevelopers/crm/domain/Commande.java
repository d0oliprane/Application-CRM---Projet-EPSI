package fr.appdevelopers.crm.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private int idCommande;

    @Column(name = "date_commande")
    private Date dateCommande;

    @Column(name = "numero_commande")
    private int numeroCommande;

    @Column(name = "statut_livraison")
    @Enumerated(EnumType.STRING)
    private StatutLivraison statutLivraison;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Client client;

    @OneToMany(mappedBy = "commande")
    private List<CommandeProduit> produits;

    public Commande(int idCommande, Date dateCommande, int numeroCommande, StatutLivraison statutLivraison, Client client, List<CommandeProduit> produits) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.numeroCommande = numeroCommande;
        this.statutLivraison = statutLivraison;
        this.client = client;
        this.produits = produits;
    }

    public Commande() {
    }

    public int getIdCommande() {
        return idCommande;
    }


    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public int getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(int numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public StatutLivraison getStatutLivraison() {
        return statutLivraison;
    }

    public void setStatutLivraison(StatutLivraison statutLivraison) {
        this.statutLivraison = statutLivraison;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<CommandeProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<CommandeProduit> produits) {
        this.produits = produits;
    }


}
