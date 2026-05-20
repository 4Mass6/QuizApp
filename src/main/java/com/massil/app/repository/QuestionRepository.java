package com.massil.app.repository;

import com.massil.app.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategorie(String categorie);
    List<Question> findByDifficulte(int difficulte);

}
