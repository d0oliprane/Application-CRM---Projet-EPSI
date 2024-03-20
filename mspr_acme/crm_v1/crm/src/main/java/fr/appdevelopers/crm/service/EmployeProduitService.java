package fr.appdevelopers.crm.service;


import fr.appdevelopers.crm.domain.EmployeProduit;
import fr.appdevelopers.crm.repository.EmployeProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeProduitService {

    @Autowired
    private EmployeProduitRepository employeProduitRepository;


    public EmployeProduit saveProduitEmploye(EmployeProduit employeProduit) {
        return employeProduitRepository.save(employeProduit);
    }
}
