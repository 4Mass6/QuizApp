package com.massil.app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalPoints;
    private int bonnesReponses;
    private int totalQuestions;
    private LocalDateTime datePartie;
    private int points;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    //Getters
    public Long getId() { return id; }
    public int getTotalPoints() { return totalPoints; }
    public int getBonnesReponses() { return bonnesReponses; }
    public int getTotalQuestions() { return totalQuestions; }
    public LocalDateTime getDatePartie() { return datePartie; }
    public Player getPlayer() { return player; }
    public int getPoints() { return points; }

    //Setters
    public void setId(Long id) { this.id = id; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
    public void setBonnesReponses(int bonnesReponses) { this.bonnesReponses = bonnesReponses; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public void setDatePartie(LocalDateTime datePartie) { this.datePartie = datePartie; }
    public void setPlayer(Player player) { this.player = player; }
    public void setPoints(int points) { this.points = points; }

}
