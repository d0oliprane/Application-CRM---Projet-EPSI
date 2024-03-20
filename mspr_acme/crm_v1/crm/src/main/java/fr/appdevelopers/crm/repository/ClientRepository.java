package fr.appdevelopers.crm.repository;

import fr.appdevelopers.crm.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client,Integer> {
    List<Client> findAllByArchiveFalse();
    List<Client> findAllByArchiveTrue();

    @Query("SELECT u.nom, u.prenom, SUM(cp.quantite * p.prix) AS chiffreAffaires " +
            "FROM Commande c " +
            "JOIN CommandeProduit cp ON c.idCommande = cp.commande.idCommande " +
            "JOIN Produit p ON cp.produit.idProduit = p.idProduit " +
            "JOIN Client cl ON c.client.idUtilisateur = cl.idUtilisateur " +
            "JOIN Utilisateur u ON cl.idUtilisateur = u.idUtilisateur " +
            "GROUP BY u.nom, u.prenom")
    List<Object[]> getChiffreAffairesParClient();
}