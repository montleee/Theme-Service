package com.meowmentor.themeservice.question;

import com.meowmentor.themeservice.exceptions.NoQuestionsFoundException;
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
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SubthemeRepository subthemeRepository;

    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        log.info("Retrieved {} questions", questions.size());
        return questions;
    }

    public Optional<Question> getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            log.info("Question with ID {} found", id);
        } else {
            log.info("Question with ID {} not found", id);
        }
        return question;
    }

    @Transactional
    public void createQuestion(CreateQuestionDto dto) {
        Subtheme subtheme = subthemeRepository.findById(dto.getSubthemeId())
                .orElseThrow(() -> {
                    return new SubthemeNotFoundException(dto.getSubthemeId());
                });

        Question question = new Question();
        question.setQuestion(dto.getQuestion());
        question.setAnswers(dto.getAnswers());
        question.setDifficulty(dto.getDifficulty());
        question.setSubtheme(subtheme);

        questionRepository.save(question);
        log.info("Created question with ID {} under subtheme with ID {}", question.getId(), subtheme.getId());
    }
    public Optional<Question> getRandomQuestion() {

            log.info("Fetching a random question.");
            Optional<Question> question = questionRepository.findRandomQuestion();
            if (question.isEmpty()) {
                log.warn("No questions found in the repository.");
            } else {
                log.info("Random question fetched successfully: {}", question.get().getQuestion());
            }
            return question;

    }

    public Optional<Question> getRandomQuestionBySubtheme(Long subthemeId) {
        log.info("Fetching a random question for subtheme ID: {}", subthemeId);

        Optional<Subtheme> subtheme = subthemeRepository.findById(subthemeId);
        if (subtheme.isEmpty()) {
            log.warn("Subtheme with ID {} not found.", subthemeId);
            throw new SubthemeNotFoundException(subthemeId);
        }

        List<Question> questions = questionRepository.findBySubthemeId(subthemeId);
        if (questions.isEmpty()) {
            log.warn("No questions found for subtheme ID {}.", subthemeId);
            throw new NoQuestionsFoundException(subthemeId);
        }

        Random random = new Random();
        Question randomQuestion = questions.get(random.nextInt(questions.size()));
        log.info("Random question for subtheme ID {}: {}", subthemeId, randomQuestion.getQuestion());

        return Optional.of(randomQuestion);
    }

    @Transactional
    public void updateQuestion(Long id, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> {
                    return new QuestionNotFoundException(id);
                });

        existingQuestion.updateFrom(updatedQuestion);

        questionRepository.save(existingQuestion);
        log.info("Updated question with ID {}", id);
    }

    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id)
                .orElseThrow(() -> {
                    return new QuestionNotFoundException(id);
                });

        questionRepository.delete(question);
        log.info("Deleted question with ID {}", id);
    }


}