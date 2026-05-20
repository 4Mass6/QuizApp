package com.massil.app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private int index;
    private int bonnesReponses;
    private int points;

    @ElementCollection
    private List<Long> questionIds;

    @ElementCollection
    private List<String> reponsesJoueur;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public int getBonnesReponses() { return bonnesReponses; }
    public void setBonnesReponses(int bonnesReponses) { this.bonnesReponses = bonnesReponses; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public List<Long> getQuestionIds() { return questionIds; }
    public void setQuestionIds(List<Long> questionIds) { this.questionIds = questionIds; }

    public List<String> getReponsesJoueur() { return reponsesJoueur; }
    public void setReponsesJoueur(List<String> reponsesJoueur) { this.reponsesJoueur = reponsesJoueur; }
}