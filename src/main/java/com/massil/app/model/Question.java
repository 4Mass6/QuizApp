package com.massil.app.model;

import jakarta.persistence.*;

import javax.swing.plaf.synth.Region;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enonce;
    private String reponseA;
    private String reponseB;
    private String reponseC;
    private String reponseD;
    private String bonneReponse;
    private String categorie;
    private int difficulte;

    //Getters
    public Long getId() { return id;}
    public int getDifficulte() { return difficulte; }
    public String getCategorie() { return categorie;}
    public String getBonneReponse() { return bonneReponse;}
    public String getReponseD() { return reponseD;}
    public String getReponseC() { return reponseC;}
    public String getReponseB() { return reponseB; }
    public String getEnonce() { return enonce;}
    public String getReponseA() { return reponseA; }

    //Setters
    public void setId(Long id) { this.id = id; }
    public void setEnonce(String enonce) { this.enonce = enonce; }
    public void setReponseA(String reponseA) { this.reponseA = reponseA; }
    public void setReponseB(String reponseB) {this.reponseB = reponseB;}
    public void setReponseC(String reponseC) { this.reponseC = reponseC;}
    public void setReponseD(String reponseD) { this.reponseD = reponseD;}
    public void setBonneReponse(String bonneReponse) { this.bonneReponse = bonneReponse;}
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setDifficulte(int difficulte) { this.difficulte = difficulte; }
}
