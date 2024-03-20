package fr.appdevelopers.crm.controller;
import fr.appdevelopers.crm.domain.*;

import fr.appdevelopers.crm.repository.CommandeEmployeRepository;
import fr.appdevelopers.crm.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Controller
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeService employeService;
    @Autowired
    private CommandeEmployeService commandeEmployeService;


    @GetMapping("/commandes")
    public String listeCommande(Model model) {
        List<Commande> commandes = commandeService.getAllCommandes();
        Collections.reverse(commandes);
        model.addAttribute("commandes", commandes);
        model.addAttribute("pageTitle", "Liste des Commandes");
        return "liste_commande";
    }
    @GetMapping("/passer-commande")
    public String afficherPasserCommandeClient(Model model){

        List<Client> clients = clientService.getClientsParArchive(false);
        List<Produit> listProduits = produitService.getProduitsParArchive(false);
        List<StatutLivraison> statutLivraisons = Arrays.asList(StatutLivraison.values());
        Commande commande = new Commande();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Employe employeConnecte = employeService.findByMatricule(username);

        model.addAttribute("clients", clients);
        model.addAttribute("statutLivraisons", statutLivraisons);
        model.addAttribute("commande", commande);
        model.addAttribute("listProduits", listProduits);
        model.addAttribute("employeConnecte", employeConnecte);
        model.addAttribute("pageTitle", "Passer commande");
        return "passer_commande";
    }

    @PostMapping("/passer-commande")
    public String passerCommandeClient(@RequestParam("idUtilisateur") int idUtilisateur,
                                       @RequestParam("dateCommande") Date dateCommande,
                                       @RequestParam("produits") List<Integer> produitsIds,
                                       @RequestParam("idEmploye") int idEmploye,
                                       @RequestParam Map<String, String> formData,
                                       RedirectAttributes redirectAttributes) {

        if (!commandeService.validerDateCommande(dateCommande, redirectAttributes)) {
            return "redirect:/passer-commande?erreur=date_vide";
        }
        if (!commandeService.validerIdClient(idUtilisateur, redirectAttributes)) {
            return "redirect:/passer-commande?erreur=client_vide";
        }
        if (!commandeService.validerPanier(produitsIds, formData, redirectAttributes)) {
            return "redirect:/passer-commande?erreur=panier_vide";
        }
        int nouvelleCommandeId = commandeService.creerCommande(idUtilisateur,
                                                dateCommande, produitsIds, idEmploye, formData);

        return "redirect:/detail_commande/" + nouvelleCommandeId;
    }


    @GetMapping("/detail_commande/{idCommande}")
    public String afficherPanier(@PathVariable int idCommande, Model model) {
        Commande commande = commandeService.getCommandeById(idCommande);
        Client client = commande.getClient();
        CommandeEmploye commandeEmploye = commandeEmployeService.getCommandeEmployeByCommande(commande);
        Employe employe = commandeEmploye.getEmploye();
        List<StatutLivraison> statutLivraisons = Arrays.asList(StatutLivraison.values());
        List<CommandeProduit> produitsCommande = commande.getProduits();
        float totalCommande = 0;
        for (CommandeProduit commandeProduit : produitsCommande) {
            totalCommande += commandeProduit.getProduit().getPrix() * commandeProduit.getQuantite();
        }
        model.addAttribute("statutLivraisons", statutLivraisons);
        model.addAttribute("commande", commande);
        model.addAttribute("client", client);
        model.addAttribute("produitsCommande", produitsCommande);
        model.addAttribute("employe", employe);
        model.addAttribute("totalCommande", totalCommande);
        model.addAttribute("pageTitle", "Détails de la commande");
        return "detail_commande";
    }
    @PostMapping("/modifier-commande/{idCommande}")
    public String modifierCommande(@PathVariable int idCommande, @ModelAttribute Commande commande){
        Commande ancienneCommande = commandeService.getCommandeById(idCommande);
        ancienneCommande.setStatutLivraison(commande.getStatutLivraison());
        commandeService.updateCommande(ancienneCommande);
        return "redirect:/commandes";
    }
}