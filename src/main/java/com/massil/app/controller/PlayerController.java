package com.massil.app.controller;

import com.massil.app.model.Player;
import com.massil.app.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String accueil() {
        return "accueil";
    }

    @PostMapping("/joueur")
    public String creerJoueur(@RequestParam String pseudo, HttpSession session) {
        Player player = playerService.trouverOuCreerJoueur(pseudo);
        session.setAttribute("playerId", player.getId());
        return "redirect:/jeu?playerId=" + player.getId();
    }

    @GetMapping("/classement")
    public String classement(Model model){
        model.addAttribute("joueurs", playerService.getClassement());
        return "classement";
    }

}
