package com.massil.app.controller;

import com.massil.app.model.*;
import com.massil.app.service.*;
import com.massil.app.repository.PartieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {

    private final QuestionService questionService;
    private final PlayerService playerService;
    private final ScoreService scoreService;
    private final PartieRepository partieRepository;

    public QuizController(QuestionService questionService,
                          PlayerService playerService,
                          ScoreService scoreService,
                          PartieRepository partieRepository) {
        this.questionService = questionService;
        this.playerService = playerService;
        this.scoreService = scoreService;
        this.partieRepository = partieRepository;
    }

    @GetMapping("/jeu")
    public String demarrerJeu(@RequestParam(required = false) Long playerId,
                              HttpSession session) {
        if (playerId != null) {
            session.setAttribute("playerId", playerId);
        } else {
            playerId = (Long) session.getAttribute("playerId");
        }
        if (playerId == null) return "redirect:/";

        List<Question> questions = questionService.getQuestionsMelangees(10);

        Partie partie = new Partie();
        partie.setPlayerId(playerId);
        partie.setIndex(0);
        partie.setBonnesReponses(0);
        partie.setPoints(0);
        partie.setQuestionIds(questions.stream().map(Question::getId).toList());
        partie.setReponsesJoueur(new ArrayList<>());
        partieRepository.save(partie);

        session.setAttribute("partieId", partie.getId());
        return "redirect:/question";
    }

    @GetMapping("/question")
    public String afficherQuestion(HttpSession session, Model model) {
        Long partieId = (Long) session.getAttribute("partieId");
        if (partieId == null) return "redirect:/";

        Partie partie = partieRepository.findById(partieId).orElse(null);
        if (partie == null || partie.getIndex() >= partie.getQuestionIds().size()) {
            return "redirect:/resultat";
        }

        Long questionId = partie.getQuestionIds().get(partie.getIndex());
        Question question = questionService.getTouteslesQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst().orElseThrow();

        model.addAttribute("question", question);
        model.addAttribute("numero", partie.getIndex() + 1);
        model.addAttribute("total", partie.getQuestionIds().size());
        return "question";
    }

    @PostMapping("/repondre")
    public String repondre(@RequestParam String reponse, HttpSession session) {
        Long partieId = (Long) session.getAttribute("partieId");
        if (partieId == null) return "redirect:/";

        Partie partie = partieRepository.findById(partieId).orElseThrow();
        Long questionId = partie.getQuestionIds().get(partie.getIndex());
        Question question = questionService.getTouteslesQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst().orElseThrow();

        List<String> reponses = new ArrayList<>(partie.getReponsesJoueur());
        reponses.add(reponse);
        partie.setReponsesJoueur(reponses);

        if (reponse.equals(question.getBonneReponse())) {
            partie.setBonnesReponses(partie.getBonnesReponses() + 1);
            partie.setPoints(partie.getPoints() + scoreService.calculerPointsQuestion(question.getDifficulte()));
        }

        partie.setIndex(partie.getIndex() + 1);
        partieRepository.save(partie);
        return "redirect:/question";
    }

    @GetMapping("/resultat")
    public String afficherResultat(HttpSession session, Model model) {
        Long partieId = (Long) session.getAttribute("partieId");
        if (partieId == null) return "redirect:/";

        Partie partie = partieRepository.findById(partieId).orElse(null);
        if (partie == null) return "redirect:/";

        Long playerId = partie.getPlayerId();
        Player player = playerService.getClassement().stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst().orElseThrow();

        List<Question> questions = partie.getQuestionIds().stream()
                .map(id -> questionService.getTouteslesQuestions().stream()
                        .filter(q -> q.getId().equals(id))
                        .findFirst().orElseThrow())
                .toList();

        Score score = scoreService.enregistrerScore(player, partie.getBonnesReponses(), questions.size(), partie.getPoints());
        player = playerService.mettreAJourStats(player, partie.getPoints());

        model.addAttribute("score", score);
        model.addAttribute("player", player);
        model.addAttribute("pointsGagnes", partie.getPoints());
        model.addAttribute("questions", questions);
        model.addAttribute("reponsesJoueur", partie.getReponsesJoueur());

        partieRepository.delete(partie);
        session.invalidate();
        return "resultat";
    }
}