package com.beratozturk.SystemOfExam.Controller;

import com.beratozturk.SystemOfExam.Model.ExamAttempt;
import com.beratozturk.SystemOfExam.Service.ExamParticipationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-participation")
public class ExamParticipationController {

    private final ExamParticipationService examParticipationService;

    public ExamParticipationController(ExamParticipationService examParticipationService) {
        this.examParticipationService = examParticipationService;
    }

    @PostMapping("/start/{examId}")
    public ResponseEntity<?> startExam(@PathVariable Long examId, @RequestHeader("Authorization") String token) {
        try {
            ExamAttempt attempt = examParticipationService.startExam(examId, token);
            return ResponseEntity.ok(attempt);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exam/{examId}/questions")
    public ResponseEntity<?> getExamQuestions(@PathVariable Long examId, @RequestHeader("Authorization") String token) {
        try {
            // Öğrenci için soruları getir (cevap olmadan)
            return ResponseEntity.ok(examParticipationService.getExamQuestionsForStudent(examId, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/submit/{examId}")
    public ResponseEntity<?> submitExam(@PathVariable Long examId, @RequestBody List<String> answers, 
                                      @RequestHeader("Authorization") String token) {
        try {
            ExamAttempt result = examParticipationService.submitExam(examId, answers, token);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/results/{examId}")
    public ResponseEntity<?> getExamResults(@PathVariable Long examId, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(examParticipationService.getExamResults(examId, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
