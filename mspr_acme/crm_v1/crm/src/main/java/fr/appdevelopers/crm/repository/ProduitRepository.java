package fr.appdevelopers.crm.repository;

import fr.appdevelopers.crm.domain.Client;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.Produit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import fr.appdevelopers.crm.domain.Client;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.Produit;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProduitRepository extends CrudRepository<Produit, Integer>{
    List<Produit> findAllByArchiveFalse();
    List<Produit> findAllByArchiveTrue();


}

