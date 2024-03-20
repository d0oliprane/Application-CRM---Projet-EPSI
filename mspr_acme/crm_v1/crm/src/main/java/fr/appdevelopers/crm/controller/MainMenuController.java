package fr.appdevelopers.crm.controller;

import fr.appdevelopers.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainMenuController {

    @GetMapping("/")
    public String afficherMenu() {
        return "main_menu";
    }
}
