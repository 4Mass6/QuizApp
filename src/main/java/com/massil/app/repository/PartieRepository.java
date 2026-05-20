package com.massil.app.repository;

import com.massil.app.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartieRepository extends JpaRepository<Partie, Long> {
}