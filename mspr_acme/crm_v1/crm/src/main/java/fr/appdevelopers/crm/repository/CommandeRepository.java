package fr.appdevelopers.crm.repository;

import fr.appdevelopers.crm.domain.Commande;
import fr.appdevelopers.crm.domain.CommandeProduit;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.StatutLivraison;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeRepository extends CrudRepository<Commande,Integer> {

    List<Commande> findByStatutLivraison(StatutLivraison statutLivraison);
    Optional<Commande> findTopByOrderByNumeroCommandeDesc();


    @Query("SELECT SUM(cp.quantite * p.prix) AS chiffreAffairesTotal " +
            "FROM CommandeProduit cp " +
            "JOIN Produit p ON cp.produit.idProduit = p.idProduit")
    Double getChiffreAffairesTotal();

    @Query("SELECT YEAR(c.dateCommande) AS annee, MONTH(c.dateCommande) AS mois, SUM(cp.quantite * p.prix) AS chiffreAffaires " +
            "FROM Commande c " +
            "JOIN CommandeProduit cp ON c.idCommande = cp.commande.idCommande " +
            "JOIN Produit p ON cp.produit.idProduit = p.idProduit " +
            "GROUP BY YEAR(c.dateCommande), MONTH(c.dateCommande)")
    List<Object[]> getChiffreAffairesParMoisEtAnnee();


}
