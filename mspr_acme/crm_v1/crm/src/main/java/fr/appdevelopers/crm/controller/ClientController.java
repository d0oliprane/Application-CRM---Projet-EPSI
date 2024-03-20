package fr.appdevelopers.crm.controller;

import fr.appdevelopers.crm.domain.Client;
import fr.appdevelopers.crm.domain.Employe;
import fr.appdevelopers.crm.domain.Produit;
import fr.appdevelopers.crm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public String listeClient(Model model) {
        List<Client> clients = clientService.getClientsParArchive(false);
        model.addAttribute("clients", clients);
        model.addAttribute("pageTitle", "Liste des clients");
        return "liste_client";
    }

    @GetMapping("/ajouter-client")
    public String afficherFormulaireAjoutClient(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("pageTitle", "Ajouter un client");
        return "ajout_client";
    }

    @PostMapping("/ajouter-client")
    public String ajouterClient(@ModelAttribute Client client) {
        clientService.ajouterClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/modifier-client/{idClient}")
    public String afficherFormulaireModificationClient(@PathVariable("idClient") int idClient, Model model) {
        Client client = clientService.getClientById(idClient);
        model.addAttribute("client", client);
        model.addAttribute("idClient", idClient);
        model.addAttribute("pageTitle", "Modifier un client");
        return "modification_client";
    }


    @PostMapping("/modifier-client/{idClient}")
    public String modifierClient(@PathVariable int idClient, @ModelAttribute Client client) {
        Client ancienClient = clientService.getClientById(idClient);

        ancienClient.setNom(client.getNom());
        ancienClient.setPrenom(client.getPrenom());
        ancienClient.setVille(client.getVille());
        ancienClient.setCodePostal(client.getCodePostal());
        ancienClient.setNumeroRue(client.getNumeroRue());
        ancienClient.setLibelleRue(client.getLibelleRue());
        ancienClient.setEmail(client.getEmail());
        ancienClient.setTelephone(client.getTelephone());
        ancienClient.setDateAffiliation(client.getDateAffiliation());
        ancienClient.setPreferenceCom(client.getPreferenceCom());

        clientService.updateClient(ancienClient);

        return "redirect:/clients";

    }


    @GetMapping("/clients-archives")
    public String listeClientsArchive(Model model) {
        List<Client> clients = clientService.getClientsParArchive(true);
        model.addAttribute("clients", clients);
        model.addAttribute("pageTitle", "Liste de clients archivés");
        return "liste_client_archives";
    }


    @GetMapping("/confirmation-archivage-client/{idUtilisateur}")
    public String afficherConfirmationArchivage(Model model, @PathVariable("idUtilisateur") int idUtilisateur) {
        Client client = clientService.getClientById(idUtilisateur);
        model.addAttribute("client", client);
        model.addAttribute("pageTitle", "Archiver client ?");
        return "confirmation_archivage_client";
    }

    @PostMapping("/archiver-client/{idUtilisateur}")
    public String archiverProduit(@PathVariable int idUtilisateur, @ModelAttribute Client client, @RequestParam String confirmation) {
        if ("oui".equals(confirmation)) {
            Client ancienClient = clientService.getClientById(idUtilisateur);
            ancienClient.setArchive(true);
            clientService.updateClient(ancienClient);
            return "redirect:/clients";
        }
        return "redirect:/clients";
    }

    @GetMapping("/confirmation-desarchivage-client/{idUtilisateur}")
    public String afficherConfirmationDesarchivage(Model model, @PathVariable("idUtilisateur") int idUtilisateur) {
        Client client = clientService.getClientById(idUtilisateur);
        model.addAttribute("client", client);
        model.addAttribute("pageTitle", "Désarchiver client ?");
        return "confirmation_desarchivage_client";
    }

    @PostMapping("/desarchiver-client/{idUtilisateur}")
    public String desarchiverClient(@PathVariable int idUtilisateur, @ModelAttribute Client client, @RequestParam String confirmation) {
        if ("oui".equals(confirmation)) {
            Client ancienClient = clientService.getClientById(idUtilisateur);
            ancienClient.setArchive(false);
            clientService.updateClient(ancienClient);
            return "redirect:/clients-archives";
        }
        return "redirect:/clients-archives";
    }
}
