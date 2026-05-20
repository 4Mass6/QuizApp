package com.massil.app.repository;

import com.massil.app.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByPseudo(String pseudo);

}
