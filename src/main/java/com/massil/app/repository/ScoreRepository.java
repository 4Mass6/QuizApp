package com.massil.app.repository;

import com.massil.app.model.Player;
import com.massil.app.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByPlayer(Player player);
    List<Score> findTop10ByOrderByBonnesReponsesDesc();

}
