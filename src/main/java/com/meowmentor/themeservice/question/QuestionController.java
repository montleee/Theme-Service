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
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.getQuestionById(id);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createQuestion(@RequestBody CreateQuestionDto dto) {
        return questionService.createQuestion(dto);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
//        if (!questionService.getQuestionById(id).isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        question.setId(id);
//        Question updatedQuestion = questionService.saveQuestion(question);
//        return ResponseEntity.ok(updatedQuestion);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (!questionService.getQuestionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
