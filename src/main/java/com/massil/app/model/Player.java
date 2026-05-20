package com.massil.app.model;

import jakarta.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private int score;
    private int partiesJouees;

    //Getters
    public Long getId() { return id; }
    public String getPseudo() { return pseudo; }
    public int getScore() { return score; }
    public int getPartiesJouees() { return partiesJouees; }

    //Setters
    public void setId(Long id) { this.id = id; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public void setScore(int score) { this.score = score; }
    public void setPartiesJouees(int partiesJouees) { this.partiesJouees = partiesJouees; }

}
