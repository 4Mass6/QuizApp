package com.massil.app.controller;

import com.massil.app.model.Player;
import com.massil.app.model.Question;
import com.massil.app.model.Score;
import com.massil.app.service.PlayerService;
import com.massil.app.service.QuestionService;
import com.massil.app.service.ScoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {

    private final QuestionService questionService;
    private final PlayerService playerService;
    private final ScoreService scoreService;

    public QuizController(QuestionService questionService, PlayerService playerService, ScoreService scoreService) {
        this.questionService = questionService;
        this.playerService = playerService;
        this.scoreService = scoreService;
    }

    @GetMapping("/jeu")
    public String demarrerJeu(HttpSession session, Model model) {
        Long playerId = (Long) session.getAttribute("playerId");
        Player player = playerService.getClassement().stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow();

        List<Question> questions = questionService.getQuestionsMelangees(10);
        session.setAttribute("questions", questions);
        session.setAttribute("index", 0);
        session.setAttribute("bonnesReponses", 0);
        session.setAttribute("points", 0);
        session.setAttribute("reponsesJoueur", new ArrayList<String>());
        session.setAttribute("playerId", playerId);

        return "redirect:/question";
    }

    @GetMapping("/question")
    public String afficherQuestion(HttpSession session, Model model) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        int index = (int) session.getAttribute("index");

        if(questions == null || index >= questions.size()) {
            return "redirect:/resultat";
        }

        model.addAttribute("question", questions.get(index));
        model.addAttribute("numero", index + 1);
        model.addAttribute("total", questions.size());
        return "question";
    }

    @PostMapping("/repondre")
    public String repondre(@RequestParam String reponse, HttpSession session) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        int index = (int) session.getAttribute("index");
        int bonnesReponses = (int) session.getAttribute("bonnesReponses");
        int points = (int) session.getAttribute("points");
        List<String> reponsesJoueur = (List<String>) session.getAttribute("reponsesJoueur");

        Question question = questions.get(index);
        reponsesJoueur.add(reponse);

        if (reponse.equals(question.getBonneReponse())) {
            bonnesReponses++;
            points += scoreService.calculerPointsQuestion(question.getDifficulte());
            session.setAttribute("bonnesReponses", bonnesReponses);
            session.setAttribute("points", points);
        }

        session.setAttribute("reponsesJoueur", reponsesJoueur);
        session.setAttribute("index", index + 1);
        return "redirect:/question";
    }

    @GetMapping("/resultat")
    public String afficherResultat(HttpSession session, Model model) {
        Integer bonnesReponses = (Integer) session.getAttribute("bonnesReponses");
        Integer points = (Integer) session.getAttribute("points");
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        List<String> reponsesJoueur = (List<String>) session.getAttribute("reponsesJoueur");
        Long playerId = (Long) session.getAttribute("playerId");

        if (bonnesReponses == null || questions == null || playerId == null) {
            return "redirect:/";
        }

        Player player = playerService.getClassement().stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow();

        Score score = scoreService.enregistrerScore(player, bonnesReponses, questions.size(), points);
        player = playerService.mettreAJourStats(player, points);

        model.addAttribute("score", score);
        model.addAttribute("player", player);
        model.addAttribute("pointsGagnes", points);
        model.addAttribute("questions", questions);
        model.addAttribute("reponsesJoueur", reponsesJoueur);

        session.invalidate();
        return "resultat";
    }
}
