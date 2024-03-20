package fr.appdevelopers.crm.controller;

import fr.appdevelopers.crm.config.SecuriteConfig;
import fr.appdevelopers.crm.domain.*;
import fr.appdevelopers.crm.repository.EmployeRepository;
import fr.appdevelopers.crm.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Arrays;
import java.util.List;


@Controller
public class EmployeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmployeService employeService;

    @GetMapping("/employes")
    public String listeEmployes(Model model) {
        List<Employe> employes = employeService.getEmployesParArchive(false);
        model.addAttribute("employes", employes);
        model.addAttribute("pageTitle", "Liste des employés");
        return "liste_employes";
    }
    @GetMapping("/employe")
    public String afficherEmploye(Model model, String nom) {
        Employe nomEmploye = employeService.findByNom(nom);

        model.addAttribute("nomEmploye", nomEmploye);
        model.addAttribute("pageTitle", "Liste des employés");

        return "fragments/sidebar";
    }
    @GetMapping("/ajouter-employe")
    public String afficherFormulaireAjoutEmploye(Model model) {
        List<Role> roles = Arrays.asList(Role.values());
        model.addAttribute("roles", roles);
        model.addAttribute("employe", new Employe());
        model.addAttribute("pageTitle", "Ajouter un employé");
        return "ajout_employe";
    }
    @PostMapping("/ajouter-employe")
    public String ajouterEmploye(@ModelAttribute Employe employe,
                                 @RequestParam(value = "ConfirmMdp") String confirmPassword,
                                 @RequestParam("roles") String role,
                                 RedirectAttributes redirectAttributes) {
        Employe existingEmploye = employeService.findByMatricule(employe.getMatricule());
        if (existingEmploye != null) {
            redirectAttributes.addFlashAttribute("errorMat", true);
            return "redirect:/ajouter-employe";
        }

        if (!employe.getMotDePasse().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMdp", true);
            return "redirect:/ajouter-employe";
        }else {
            String motDePasseHache = passwordEncoder.encode(employe.getMotDePasse());
            employe.setMotDePasse(motDePasseHache);
            employe.setRole(Role.valueOf(role));
            employeService.saveEmploye(employe);
            return "redirect:/employes";
        }
    }
    @GetMapping("/modifier-employe/{idEmploye}")
    public String afficherFormulaireModificationEmploye(@PathVariable("idEmploye") int idEmploye, Model model) {
        Employe employe = employeService.getEmployeById(idEmploye);
        List<Role> roles = Arrays.asList(Role.values());
        model.addAttribute("employe", employe);
        model.addAttribute("idEmploye", idEmploye);
        model.addAttribute("roles", roles);
        model.addAttribute("pageTitle", "Modifier un employé");
        return "modification_employe";
    }

    @PostMapping("/modifier-employe/{idEmploye}")
    public String modifierEmploye(@PathVariable int idEmploye,
                                  @ModelAttribute Employe employe) {
        Employe ancienEmploye = employeService.getEmployeById(idEmploye);


        ancienEmploye.setNom(employe.getNom());
        ancienEmploye.setPrenom(employe.getPrenom());
        ancienEmploye.setVille(employe.getVille());
        ancienEmploye.setCodePostal(employe.getCodePostal());
        ancienEmploye.setNumeroRue(employe.getNumeroRue());
        ancienEmploye.setLibelleRue(employe.getLibelleRue());
        ancienEmploye.setEmail(employe.getEmail());
        ancienEmploye.setTelephone(employe.getTelephone());
        ancienEmploye.setMatricule(employe.getMatricule());
        ancienEmploye.setRole(employe.getRole());

        employeService.updateEmploye(ancienEmploye);

        return "redirect:/employes";

    }


    @GetMapping("/employes-archives")
    public String listeEmployesArchive(Model model) {
        List<Employe> employes = employeService.getEmployesParArchive(true);
        model.addAttribute("employes", employes);
        model.addAttribute("pageTitle", "Liste des employés");
        return "liste_employe_archives";
    }

    @GetMapping("/confirmation-archivage-employe/{idUtilisateur}")
    public String afficherConfirmationArchivage(Model model, @PathVariable("idUtilisateur") int idUtilisateur) {
        Employe employe = employeService.getEmployeById(idUtilisateur);
        model.addAttribute("employe", employe);
        model.addAttribute("pageTitle", "Archiver employé ?");
        return "confirmation_archivage_employe";
    }

    @PostMapping("/archiver-employe/{idUtilisateur}")
    public String archiverProduit(@PathVariable int idUtilisateur, @ModelAttribute Employe employe, @RequestParam String confirmation) {
        if ("oui".equals(confirmation)) {
            Employe ancienEmploye = employeService.getEmployeById(idUtilisateur);
            ancienEmploye.setArchive(true);
            employeService.updateEmploye(ancienEmploye);
            return "redirect:/employes";
        }
        return "redirect:/employes";
    }

    @GetMapping("/confirmation-desarchivage-employe/{idUtilisateur}")
    public String afficherConfirmationDesarchivage(Model model, @PathVariable("idUtilisateur") int idUtilisateur) {
        Employe employe = employeService.getEmployeById(idUtilisateur);
        model.addAttribute("employe", employe);
        model.addAttribute("pageTitle", "Désarchiver employé ?");
        return "confirmation_desarchivage_employe";
    }

    @PostMapping("/desarchiver-employe/{idUtilisateur}")
    public String desarchiverEmploye(@PathVariable int idUtilisateur, @ModelAttribute Employe employe, @RequestParam String confirmation) {
        if ("oui".equals(confirmation)) {
            Employe ancienEmploye = employeService.getEmployeById(idUtilisateur);
            ancienEmploye.setArchive(false);
            employeService.updateEmploye(ancienEmploye);
            return "redirect:/employes-archives";
        }
        return "redirect:/employes-archives";


    }

}
