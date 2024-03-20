package fr.appdevelopers.crm.repository;

import fr.appdevelopers.crm.domain.Commande;
import fr.appdevelopers.crm.domain.CommandeProduit;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.Produit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface CommandeProduitRepository extends CrudRepository<CommandeProduit,Integer> {

    @Query("SELECT p.nom AS produit, SUM(cp.quantite) AS quantiteVendue " +
            "FROM CommandeProduit cp " +
            "JOIN Produit p ON cp.produit.idProduit = p.idProduit " +
            "GROUP BY cp.produit.idProduit " +
            "ORDER BY SUM(cp.quantite) DESC LIMIT 5")
    List<Object[]> getTop5ProduitsPlusVendus();

    @Query("SELECT p.nom AS produit, SUM(cp.quantite) AS quantiteVendue " +
            "FROM CommandeProduit cp " +
            "JOIN Produit p ON cp.produit.idProduit = p.idProduit " +
            "GROUP BY cp.produit.idProduit " +
            "ORDER BY SUM(cp.quantite) ASC LIMIT 5")
    List<Object[]> getTop5ProduitsMoinsVendus();


}
