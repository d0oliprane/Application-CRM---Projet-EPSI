package fr.appdevelopers.crm.service;

import fr.appdevelopers.crm.domain.*;
import fr.appdevelopers.crm.repository.ClientRepository;
import fr.appdevelopers.crm.repository.CommandeProduitRepository;
import fr.appdevelopers.crm.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CommandeProduitRepository commandeProduitRepository;
    @Autowired
    CommandeRepository commandeRepository;

    public Map<String, Integer> convertToMapByMonth(List<Commande> commandes) {
        Map<String, Integer> graphData = new TreeMap<>();

        for (Commande commande : commandes) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(commande.getDateCommande());
            int numeroMois = calendar.get(Calendar.MONTH) + 1;
            String nomMois = Month.of(numeroMois).getDisplayName(TextStyle.FULL, Locale.FRENCH);
            graphData.put(nomMois, graphData.getOrDefault(nomMois, 0) + 1);
        }

        return graphData;
    }
    public List<String> getEmployeNames(List<CommandeEmploye> commandesEmploye) {
        List<String> employeNames = new ArrayList<>();
        for (CommandeEmploye commandeEmploye : commandesEmploye) {
            String employeName = commandeEmploye.getEmploye().getNom();
            if (!employeNames.contains(employeName)) {
                employeNames.add(employeName);
            }
        }
        return employeNames;
    }
    public List<CommandeEmploye> filterByStatus(List<CommandeEmploye> commandesEmploye, StatutLivraison status) {
        return commandesEmploye.stream()
                .filter(commandeEmploye -> commandeEmploye.getCommande().getStatutLivraison().equals(status))
                .collect(Collectors.toList());
    }


    public Map<String, Integer> convertToMapByMonthEmp(List<CommandeEmploye> commandesEmploye) {
        Map<String, Integer> graphData = new HashMap<>();

        for (CommandeEmploye commandeEmploye : commandesEmploye) {
            String employeName = commandeEmploye.getEmploye().getNom();
            List<Commande> commandes = Collections.singletonList(commandeEmploye.getCommande());
            for (Commande commande : commandes) {
                String month = getMonthFromCommandeDate(commande.getDateCommande());
                graphData.put(employeName + "_" + month, graphData.getOrDefault(employeName + "_" + month, 0) + 1);
            }
        }
        return graphData;
    }
    public String getMonthFromCommandeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        String[] monthNames = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"};
        return monthNames[month];
    }


    public Double getChiffreAffairesTotal(){
        return commandeRepository.getChiffreAffairesTotal();
    }
    public List<Object[]> getTop5ProduitsPlusVendus() {
        return commandeProduitRepository.getTop5ProduitsPlusVendus();
    }
    public List<Object[]> getTop5ProduitsMoinsVendus() {
        return commandeProduitRepository.getTop5ProduitsMoinsVendus();
    }
    public List<Object[]> getChiffreAffairesParMoisEtAnnee() {
        return commandeRepository.getChiffreAffairesParMoisEtAnnee();
    }
    public Map<String, Double> getChiffresAffairesParClient() {
        List<Object[]> chiffreAffairesParClient = clientRepository.getChiffreAffairesParClient();

        Map<String, Double> chiffresAffairesParClient = new HashMap<>();
        for (Object[] row : chiffreAffairesParClient) {
            String nomClient = (String) row[0];
            String prenomClient = (String) row[1];
            Double chiffreAffaire = (Double) row[2];
            chiffresAffairesParClient.put(nomClient + " " + prenomClient, chiffreAffaire);
        }
        return chiffresAffairesParClient;
    }

}
