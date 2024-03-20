package fr.appdevelopers.crm.repository;

import fr.appdevelopers.crm.domain.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeRepository extends CrudRepository<Employe,Integer> {
    List<Employe> findAllByArchiveFalse();
    List<Employe> findAllByArchiveTrue();
    Employe findByMatricule(String matricule);
    Employe findByNom(String nom);

}
