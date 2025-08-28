package com.beratozturk.SystemOfExam.Controller;

import com.beratozturk.SystemOfExam.dto.ExamCreateRequest;
import com.beratozturk.SystemOfExam.Model.Exam;
import com.beratozturk.SystemOfExam.Model.Question;
import com.beratozturk.SystemOfExam.Service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ExamCreateRequest req) {
        // DTO -> Entity dönüştür
        Exam exam = new Exam();
        exam.setTitle(req.getTitle());
        exam.setDescription(req.getDescription());
        exam.setDurationInMinutes(req.getDurationInMinutes());
        exam.setStartDate(req.getStartDate());
        exam.setEndDate(req.getEndDate());
        exam.setActive(req.isActive());

        Exam saved = examService.createExam(exam);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/active")
    public List<Exam> getActiveExams() {
        return examService.getActiveExams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        Exam exam = examService.getExamById(id);
        if (exam != null) {
            return ResponseEntity.ok(exam);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(@PathVariable Long id, @Valid @RequestBody ExamCreateRequest req) {
        try {
            Exam updatedExam = examService.updateExam(id, req);
            return ResponseEntity.ok(updatedExam);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        boolean deleted = examService.deleteExam(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{examId}/questions")
    public ResponseEntity<?> addQuestion(@PathVariable Long examId, @RequestBody Question question) {
        Question saved = examService.addQuestion(examId, question);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<Question>> getExamQuestions(@PathVariable Long examId) {
        List<Question> questions = examService.getExamQuestions(examId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{examId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long examId, @PathVariable Long questionId, 
                                          @RequestBody Question question) {
        try {
            Question updatedQuestion = examService.updateQuestion(examId, questionId, question);
            return ResponseEntity.ok(updatedQuestion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{examId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long examId, @PathVariable Long questionId) {
        try {
            boolean deleted = examService.deleteQuestion(examId, questionId);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
