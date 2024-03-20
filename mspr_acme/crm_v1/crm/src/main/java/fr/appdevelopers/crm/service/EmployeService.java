package fr.appdevelopers.crm.service;

import fr.appdevelopers.crm.domain.Client;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.Produit;
import fr.appdevelopers.crm.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;



    public Employe ajouterEmploye(Employe employe) {

        return employeRepository.save(employe);
    }
    public List<Employe> getAllEmployes() {
        return (List<Employe>) employeRepository.findAll();
    }

    public Employe getEmployeById(int id) {
        return employeRepository.findById(id).orElse(null);
    }

    public Employe saveEmploye(Employe employe) {

        return employeRepository.save(employe);
    }

    public void deleteEmployeById(int id) {
        employeRepository.deleteById(id);
    }

    public Employe findByMatricule(String matricule) {

        return employeRepository.findByMatricule(matricule);
    }
    public Employe findByNom(String nom) {

        return employeRepository.findByNom(nom);
    }

    public void updateEmploye(Employe employe) {
        employeRepository.save(employe);
    }

    public List<Employe> getEmployesParArchive(boolean actif) {
        if (!actif) {
            return employeRepository.findAllByArchiveFalse();
        } else {
            return employeRepository.findAllByArchiveTrue();
        }
    }
    public boolean isEmployeArchive(Employe employe) {
        return employe.getArchive();
    }
}
