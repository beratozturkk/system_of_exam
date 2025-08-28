package com.beratozturk.SystemOfExam.Controller;

import com.beratozturk.SystemOfExam.Model.Question;
import com.beratozturk.SystemOfExam.Service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @GetMapping("/exam/{examId}")
    public List<Question> getQuestionsByExam(@PathVariable Long examId) {
        return questionService.getQuestionsByExamId(examId);
    }

}
