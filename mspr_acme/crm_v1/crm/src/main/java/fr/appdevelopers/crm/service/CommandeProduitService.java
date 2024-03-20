package fr.appdevelopers.crm.service;

import fr.appdevelopers.crm.domain.CommandeProduit;
import fr.appdevelopers.crm.repository.CommandeProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeProduitService {

    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    public List<CommandeProduit> getAllCommandeProduits() {
        return (List<CommandeProduit>) commandeProduitRepository.findAll();
    }

    public Optional<CommandeProduit> getCommandeProduitById(int id) {
        return commandeProduitRepository.findById(id);
    }

    public CommandeProduit saveCommandeProduit(CommandeProduit commandeProduit) {
        return commandeProduitRepository.save(commandeProduit);
    }

    public void deleteCommandeProduitById(int id) {
        commandeProduitRepository.deleteById(id);
    }

}
