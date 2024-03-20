package fr.appdevelopers.crm.service;

import fr.appdevelopers.crm.domain.*;
import fr.appdevelopers.crm.repository.CommandeProduitRepository;
import fr.appdevelopers.crm.repository.CommandeRepository;
import fr.appdevelopers.crm.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.sql.Date;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeService employeService;

    @Autowired
    private ProduitService produitService;
    @Autowired
    private CommandeEmployeService commandeEmployeService;

    public boolean validerDateCommande(Date dateCommande, RedirectAttributes redirectAttributes) {
        if (dateCommande == null) {
            redirectAttributes.addFlashAttribute("erreurDate", "date_vide");
            return false;
        }
        return true;
    }

    public boolean validerIdClient(int idUtilisateur, RedirectAttributes redirectAttributes) {
        if (idUtilisateur <= 0) {
            redirectAttributes.addFlashAttribute("erreurClient", "client_vide");
            return false;
        }
        return true;
    }

    public boolean validerPanier(List<Integer> produitsIds, Map<String, String> formData, RedirectAttributes redirectAttributes) {
        boolean produitsNonVides = produitsIds.stream()
                .anyMatch(produitId -> {
                    String quantiteKey = "quantite-" + produitId;
                    int quantite = Integer.parseInt(formData.get(quantiteKey));
                    return quantite > 0;
                });

        if (!produitsNonVides) {
            redirectAttributes.addFlashAttribute("erreurPanier", "panier_vide");
            return false;
        }
        return true;
    }

    public int creerCommande(int idUtilisateur, Date dateCommande, List<Integer> produitsIds, int idEmploye, Map<String, String> formData) {
        Commande commande = new Commande();
        commande.setDateCommande(dateCommande);
        commande.setStatutLivraison(StatutLivraison.EN_PREPARATION);
        Client client = clientService.getClientById(idUtilisateur);
        commande.setClient(client);
        Employe employe = employeService.getEmployeById(idEmploye);
        Commande savedCommande = saveCommande(commande);
        CommandeEmploye commandeEmploye = new CommandeEmploye();
        commandeEmploye.setCommande(savedCommande);
        commandeEmploye.setEmploye(employe);
        commandeEmployeService.saveCommandeEmploye(commandeEmploye);
        int nouvelleCommandeId;
        for (Integer produitId : produitsIds) {
            String quantiteKey = "quantite-" + produitId;
            int quantite = Integer.parseInt(formData.get(quantiteKey));
            Produit produit = produitService.getProduitById(produitId);
            if (quantite > produit.getStock()) {
                quantite = produit.getStock();
            }
            CommandeProduit commandeProduit = new CommandeProduit();
            commandeProduit.setProduit(produit);
            commandeProduit.setCommande(savedCommande);
            commandeProduit.setQuantite(quantite);
            produit.setStock(produit.getStock() - quantite);
            produitService.saveProduit(produit);
            commandeProduitRepository.save(commandeProduit);
        }
        nouvelleCommandeId = savedCommande.getIdCommande();
        return nouvelleCommandeId;
    }



    public List<Commande> getAllCommandes () {
        return (List<Commande>) commandeRepository.findAll();
    }

    public Commande getCommandeById (int id){
        return commandeRepository.findById(id).orElse(null);
    }


    public CommandeProduit saveCommandeProduit(CommandeProduit commandeProduit) {
        return commandeProduitRepository.save(commandeProduit);
    }
    public List<Commande> getCommandesByStatutLivraison(StatutLivraison statutLivraison) {

        return commandeRepository.findByStatutLivraison(statutLivraison);
    }
    public Commande saveCommande(Commande commande) {
        int numeroCommande = genererNumeroCommande();
        commande.setNumeroCommande(numeroCommande);
        return commandeRepository.save(commande);
    }

    private int genererNumeroCommande() {
        Optional<Commande> commandeOptionnelle = commandeRepository.findTopByOrderByNumeroCommandeDesc();
        if (commandeOptionnelle.isPresent()) {
            int numeroMax = commandeOptionnelle.get().getNumeroCommande();
            return numeroMax + 1;
        } else {
            return 202400000;
        }
    }





    public void updateCommande(Commande commande) {commandeRepository.save(commande);}

}
