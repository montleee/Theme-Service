package com.meowmentor.themeservice.question;

import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.question.dto.CreateQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.getQuestionById(id);

        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        } else {
            ApiResponseDto response = new ApiResponseDto("Question not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createQuestion(@RequestBody CreateQuestionDto dto) {
         questionService.createQuestion(dto);
         var response = new ApiResponseDto("Question created successfully", HttpStatus.CREATED.value());
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateQuestion(@PathVariable Long id, @RequestBody Question question) {

        questionService.updateQuestion(id,question);
        var response = new ApiResponseDto("Question updated successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteQuestion(@PathVariable Long id) {

        questionService.deleteQuestion(id);
        var response = new ApiResponseDto("Question deleted successfully", HttpStatus.NO_CONTENT.value());
        return ResponseEntity.ok(response);

    }
}
