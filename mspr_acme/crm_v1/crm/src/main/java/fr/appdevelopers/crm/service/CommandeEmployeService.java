package fr.appdevelopers.crm.service;

import fr.appdevelopers.crm.domain.Commande;
import fr.appdevelopers.crm.domain.CommandeEmploye;
import fr.appdevelopers.crm.repository.CommandeEmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeEmployeService {

    @Autowired
    private CommandeEmployeRepository commandeEmployeRepository;

    public CommandeEmploye saveCommandeEmploye(CommandeEmploye commandeEmploye) {
        return commandeEmployeRepository.save(commandeEmploye);
    }

    public CommandeEmploye getCommandeEmployeByCommande(Commande commande) {
        return commandeEmployeRepository.findByCommande(commande);
    }

    public List<CommandeEmploye> getAllCommandesEmploye() {
        return (List<CommandeEmploye>) commandeEmployeRepository.findAll();
    }
}
