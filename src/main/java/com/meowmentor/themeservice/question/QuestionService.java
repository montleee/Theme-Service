package com.meowmentor.themeservice.question;

import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.exceptions.SubthemeNotFoundException;
import com.meowmentor.themeservice.question.dto.CreateQuestionDto;
import com.meowmentor.themeservice.subtheme.Subtheme;
import com.meowmentor.themeservice.subtheme.SubthemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SubthemeRepository subthemeRepository;


    public ResponseEntity<ApiResponseDto> createQuestion(CreateQuestionDto dto) {
        // Найти Subtheme по ID
        Subtheme subtheme = subthemeRepository.findById(dto.getSubthemeId())
                .orElseThrow(() -> new SubthemeNotFoundException(dto.getSubthemeId()));

        // Создать новый вопрос
        Question question = new Question();
        question.setQuestion(dto.getQuestion());
        question.setAnswers(dto.getAnswers());
        question.setDifficulty(dto.getDifficulty());
        question.setSubtheme(subtheme);

        // Сохранить вопрос
        questionRepository.save(question);

        // Логирование
        System.out.println("Created question with text '" + question.getQuestion() + "' under subtheme: " + subtheme.getTitle());

        // Возвращаем успешный ответ
        ApiResponseDto response = new ApiResponseDto("Question created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }


    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }


}