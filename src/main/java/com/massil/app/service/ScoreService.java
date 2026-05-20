package com.massil.app.service;

import com.massil.app.model.Player;
import com.massil.app.model.Score;
import com.massil.app.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Score enregistrerScore(Player player, int bonnesReponses, int totalQuestions, int points) {
        Score score = new Score();
        score.setPlayer(player);
        score.setBonnesReponses(bonnesReponses);
        score.setTotalQuestions(totalQuestions);
        score.setTotalPoints(points);
        score.setDatePartie(LocalDateTime.now());

        return scoreRepository.save(score);
    }

    public List<Score> getScoresParJoueur(Player player) {
        return scoreRepository.findByPlayer(player);
    }

    public List<Score> getTopScores() {
        return scoreRepository.findTop10ByOrderByBonnesReponsesDesc();
    }

    public int calculerPointsQuestion(int difficulte) {
        return switch (difficulte) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> 30;
            default -> 0;
        };
    }

}
