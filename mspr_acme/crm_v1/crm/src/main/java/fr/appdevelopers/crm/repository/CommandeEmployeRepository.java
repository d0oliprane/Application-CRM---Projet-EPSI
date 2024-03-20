package fr.appdevelopers.crm.repository;

import fr.appdevelopers.crm.domain.Commande;
import fr.appdevelopers.crm.domain.CommandeEmploye;
import fr.appdevelopers.crm.domain.CommandeProduit;
import org.springframework.data.repository.CrudRepository;

public interface CommandeEmployeRepository extends CrudRepository<CommandeEmploye,Integer> {
    CommandeEmploye findByCommande(Commande commande);

}
