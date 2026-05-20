package com.massil.app.service;

import com.massil.app.model.Player;
import com.massil.app.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player trouverOuCreerJoueur(String pseudo){
        Optional<Player> existantPlayer = playerRepository.findByPseudo(pseudo);
        if (existantPlayer.isPresent()) {
            return existantPlayer.get();
        }
        Player nouveauPlayer = new Player();
        nouveauPlayer.setPseudo(pseudo);
        nouveauPlayer.setScore(0);
        nouveauPlayer.setPartiesJouees(0);
        return playerRepository.save(nouveauPlayer);
    }

    public Player mettreAJourStats(Player player, int pointsGagnes) {
        player.setScore(player.getScore() + pointsGagnes);
        player.setPartiesJouees(player.getPartiesJouees() + 1);
        return playerRepository.save(player);
    }

    public List<Player> getClassement() {
        return playerRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getScore() - a.getScore())
                .toList();
    }
}
