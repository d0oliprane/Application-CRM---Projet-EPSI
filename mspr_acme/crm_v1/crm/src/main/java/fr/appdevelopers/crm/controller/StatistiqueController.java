package fr.appdevelopers.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.appdevelopers.crm.domain.Commande;
import fr.appdevelopers.crm.domain.CommandeEmploye;
import fr.appdevelopers.crm.domain.Produit;
import fr.appdevelopers.crm.domain.StatutLivraison;
import fr.appdevelopers.crm.service.CommandeEmployeService;
import fr.appdevelopers.crm.service.CommandeService;
import fr.appdevelopers.crm.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatistiqueController {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private StatistiqueService statistiqueService;
    @Autowired
    private CommandeEmployeService commandeEmployeService;


    @GetMapping("/statistiques")
    public String listeCommandeStats(Model model) {

        List<Commande> enPreparation = commandeService.getCommandesByStatutLivraison(StatutLivraison.valueOf("EN_PREPARATION"));
        List<Commande> enCoursAcheminement = commandeService.getCommandesByStatutLivraison(StatutLivraison.valueOf("EN_COURS_ACHEMINEMENT"));
        List<Commande> terminees = commandeService.getCommandesByStatutLivraison(StatutLivraison.valueOf("LIVREE"));
        Map<String, Integer> graphDataPreparation = statistiqueService.convertToMapByMonth(enPreparation);
        Map<String, Integer> graphDataAcheminement = statistiqueService.convertToMapByMonth(enCoursAcheminement);
        Map<String, Integer> graphDataTerminees = statistiqueService.convertToMapByMonth(terminees);


        List<CommandeEmploye> commandesEmploye = commandeEmployeService.getAllCommandesEmploye();
        List<CommandeEmploye> graphDataPreparationEmp = statistiqueService.filterByStatus(commandesEmploye, StatutLivraison.valueOf("EN_PREPARATION"));
        List<CommandeEmploye> graphDataAcheminementEmp = statistiqueService.filterByStatus(commandesEmploye, StatutLivraison.valueOf("EN_COURS_ACHEMINEMENT"));
        List<CommandeEmploye> graphDataTermineesEmp = statistiqueService.filterByStatus(commandesEmploye, StatutLivraison.valueOf("LIVREE"));
        Map<String, Integer> graphDataPreparationE = statistiqueService.convertToMapByMonthEmp(graphDataPreparationEmp);
        Map<String, Integer> graphDataAcheminementE = statistiqueService.convertToMapByMonthEmp(graphDataAcheminementEmp);
        Map<String, Integer> graphDataTermineesE = statistiqueService.convertToMapByMonthEmp(graphDataTermineesEmp);
        List<String> employeNames = statistiqueService.getEmployeNames(commandesEmploye);


        Map<String, Double> chiffresAffairesParClient = statistiqueService.getChiffresAffairesParClient();
        List<Object[]> top5ProduitsPlusVendus = statistiqueService.getTop5ProduitsPlusVendus();
        List<Object[]> top5ProduitsMoinsVendus = statistiqueService.getTop5ProduitsMoinsVendus();

        List<Object[]> chiffreAffairesParMoisEtAnnee = statistiqueService.getChiffreAffairesParMoisEtAnnee();
        Double chiffreAffairesTotal = statistiqueService.getChiffreAffairesTotal();

        model.addAttribute("chiffreAffairesTotal", chiffreAffairesTotal);
        model.addAttribute("chiffreAffairesParMoisEtAnnee", chiffreAffairesParMoisEtAnnee);
        model.addAttribute("top5ProduitsMoinsVendus", top5ProduitsMoinsVendus);
        model.addAttribute("top5ProduitsPlusVendus", top5ProduitsPlusVendus);
        model.addAttribute("chiffresAffairesParClient", chiffresAffairesParClient);
        model.addAttribute("graphDataPreparationE", graphDataPreparationE);
        model.addAttribute("graphDataAcheminementE", graphDataAcheminementE);
        model.addAttribute("graphDataTermineesE", graphDataTermineesE);
        model.addAttribute("employeNames", employeNames);
        model.addAttribute("graphDataPreparation", graphDataPreparation);
        model.addAttribute("graphDataAcheminement", graphDataAcheminement);
        model.addAttribute("graphDataTerminees", graphDataTerminees);
        model.addAttribute("pageTitle", "Statistiques");

        return "statistiques";
    }
}

