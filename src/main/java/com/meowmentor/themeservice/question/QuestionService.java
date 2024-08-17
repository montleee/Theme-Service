package com.meowmentor.themeservice.question;

import com.meowmentor.themeservice.exceptions.QuestionNotFoundException;
import com.meowmentor.themeservice.exceptions.SubthemeNotFoundException;
import com.meowmentor.themeservice.question.dto.CreateQuestionDto;
import com.meowmentor.themeservice.subtheme.Subtheme;
import com.meowmentor.themeservice.subtheme.SubthemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SubthemeRepository subthemeRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question;
    }

    @Transactional
    public void createQuestion(CreateQuestionDto dto) {

        Subtheme subtheme = subthemeRepository.findById(dto.getSubthemeId())
                .orElseThrow(() -> new SubthemeNotFoundException(dto.getSubthemeId()));


        Question question = new Question();
        question.setQuestion(dto.getQuestion());
        question.setAnswers(dto.getAnswers());
        question.setDifficulty(dto.getDifficulty());
        question.setSubtheme(subtheme);

        questionRepository.save(question);
    }

    @Transactional
    public void updateQuestion(Long id, Question updatedQuestion) {

        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        existingQuestion.updateFrom(updatedQuestion);

       questionRepository.save(existingQuestion);
    }

    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        questionRepository.delete(question);
    }




}