package fr.appdevelopers.crm.service;

import fr.appdevelopers.crm.domain.Client;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client ajouterClient(Client client) {

        return clientRepository.save(client);
    }

    public List<Client> getAllClient() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client getClientById(int id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client saveClient(Client client) {

        return clientRepository.save(client);
    }

    public void deleteClientById(int id) {
        clientRepository.deleteById(id);
    }
    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> getClientsParArchive(boolean actif) {
        if (!actif) {
            return clientRepository.findAllByArchiveFalse();
        } else {
            return clientRepository.findAllByArchiveTrue();
        }
    }

}
