package com.massil.app.service;

import com.massil.app.model.Question;
import com.massil.app.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getTouteslesQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsMelangees(int nombre) {
        List<Question> questions = questionRepository.findAll();
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(nombre, questions.size()));
    }

    public List<Question> getQuestionsParCategorie(String categorie) {
        return questionRepository.findByCategorie(categorie);
    }

    public Question sauvegarderQuestion(Question question) {
        return questionRepository.save(question);
    }

}
