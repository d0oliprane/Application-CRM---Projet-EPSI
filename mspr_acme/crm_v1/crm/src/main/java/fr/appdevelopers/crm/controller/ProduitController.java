package fr.appdevelopers.crm.controller;

import fr.appdevelopers.crm.domain.*;
import fr.appdevelopers.crm.service.EmployeProduitService;
import fr.appdevelopers.crm.service.EmployeService;
import fr.appdevelopers.crm.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
public class ProduitController {

    @Autowired
    ProduitService produitService;
    @Autowired
    EmployeService employeService;
    @Autowired
    EmployeProduitService employeProduitService;


    @GetMapping("/produits")
    public String listeProduits(Model model) {
        List<Produit> produits = produitService.getProduitsParArchive(false);
        model.addAttribute("produits", produits);
        model.addAttribute("pageTitle", "Liste des produits");
        return "liste_produits";
    }

    @GetMapping("/produits-archives")
    public String listeProduitsArchive(Model model) {
        List<Produit> produits = produitService.getProduitsParArchive(true);
        model.addAttribute("produits", produits);
        model.addAttribute("pageTitle", "Liste des produits archivés");
        return "liste_produits_archives";
    }

    @GetMapping("/produit/{idProduit}")
    public String getProduitById(@PathVariable("idProduit") int produitId, Model model) {
        Produit produit = produitService.getProduitById(produitId);
        model.addAttribute("produit", produit);
        model.addAttribute("pageTitle", "Détails du produit");
        return "details_produit";
    }

    @GetMapping("/ajouter-produit")
    public String afficherFormulaireAjoutProduit(Model model) {
        List<String> categories = produitService.getDistinctCategories();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Employe employeConnecte = employeService.findByMatricule(username);

        model.addAttribute("employeConnecte", employeConnecte);
        model.addAttribute("categories", categories);
        model.addAttribute("produit", new Produit());
        model.addAttribute("pageTitle", "Ajouter un produit");
        return "ajout_produit";
    }

    @PostMapping("/ajouter-produit")
    public String ajouterProduit(
            @ModelAttribute Produit produit,
            @RequestParam(value = "categories", required = false) String categories,
            @RequestParam("categorie") String nouvelleCategorie,
            @RequestParam("idEmploye") int idEmploye
    ) throws IOException {

        Employe employeConnecte = employeService.getEmployeById(idEmploye);

        if (employeConnecte == null) {
            return "redirect:/erreur";
        }
        if (categories == null || categories.isEmpty()) {
            categories = "Catégorie par défaut";
        }
        if (nouvelleCategorie != null && !nouvelleCategorie.isEmpty()) {
            produit.setCategorie(nouvelleCategorie);
        } else {
            produit.setCategorie(categories);
        }

        Produit savedProduit = produitService.saveProduit(produit);

        EmployeProduit employeProduit = new EmployeProduit();
        employeProduit.setEmploye(employeConnecte);
        employeProduit.setProduit(savedProduit);

        employeProduitService.saveProduitEmploye(employeProduit);

        return "redirect:/produits";
    }


    @GetMapping("/modifier-produit/{idProduit}")
    public String afficherFormulaireModification(@PathVariable("idProduit") int idProduit, Model model) {
        List<String> categories = produitService.getDistinctCategories();
        Produit produit = produitService.getProduitById(idProduit);
        model.addAttribute("produit", produit);
        model.addAttribute("idProduit", idProduit);
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Modifier un produit");
        return "modification_produit";
    }


    @PostMapping("/modifier-produit/{idProduit}")
    public String modifierProduit(@PathVariable int idProduit, @ModelAttribute Produit produit,
                                  @RequestParam(value = "categories", required = false) String categories,
                                  @RequestParam("categorie") String nouvelleCategorie,
                                  @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Produit ancienProduit = produitService.getProduitById(idProduit);
        if (categories != null && !categories.isEmpty()) {
            produit.setCategorie(categories);
        } else if (nouvelleCategorie != null && !nouvelleCategorie.isEmpty()) {
            produit.setCategorie(nouvelleCategorie);
        } else {
            produit.setCategorie("Catégorie par défaut");
        }
        produit.setImage(imageFile.getOriginalFilename());

        if (!produit.getImageFile().isEmpty()) {
            String nomImage = produitService.saveImage(produit.getImageFile());
            produit.setImage(nomImage);
        } else {

            produit.setImage(ancienProduit.getImage());
        }
        ancienProduit.setNom(produit.getNom());
        ancienProduit.setDescription(produit.getDescription());
        ancienProduit.setStock(produit.getStock());
        ancienProduit.setCategorie(produit.getCategorie());
        ancienProduit.setImage(produit.getImage());

        produitService.updateProduit(ancienProduit);

        return "redirect:/produits";

    }

    @GetMapping("/confirmation-archivage/{idProduit}")
    public String afficherConfirmationArchivage(Model model, @PathVariable("idProduit") int idProduit) {
        Produit produit = produitService.getProduitById(idProduit);
        model.addAttribute("produit", produit);
        model.addAttribute("pageTitle", "Archiver produit ?");
        return "confirmation_archivage";
    }

    @PostMapping("/archiver-produit/{idProduit}")
    public String archiverProduit(@PathVariable int idProduit, @ModelAttribute Produit produit, @RequestParam String confirmation) {
        if ("oui".equals(confirmation)) {
            Produit ancienProduit = produitService.getProduitById(idProduit);
            ancienProduit.setArchive(true);
            produitService.updateProduit(ancienProduit);
            return "redirect:/produits";
        }
        return "redirect:/produits";
    }

    @GetMapping("/confirmation-desarchivage/{idProduit}")
    public String afficherConfirmationDesarchivage(Model model, @PathVariable("idProduit") int idProduit) {
        Produit produit = produitService.getProduitById(idProduit);
        model.addAttribute("produit", produit);
        model.addAttribute("pageTitle", "Désarchiver produit ?");
        return "confirmation_desarchivage";
    }

    @PostMapping("/desarchiver-produit/{idProduit}")
    public String desarchiverProduit(@PathVariable int idProduit, @ModelAttribute Produit produit, @RequestParam String confirmation) {
        if ("oui".equals(confirmation)) {
            Produit ancienProduit = produitService.getProduitById(idProduit);
            ancienProduit.setArchive(false);
            produitService.updateProduit(ancienProduit);
            return "redirect:/produits-archives";
        }
        return "redirect:/produits-archives";
    }
}


