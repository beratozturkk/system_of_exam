package com.beratozturk.SystemOfExam.Service;

import com.beratozturk.SystemOfExam.Model.Exam;
import com.beratozturk.SystemOfExam.Model.Question;
import com.beratozturk.SystemOfExam.dto.ExamCreateRequest;
import com.beratozturk.SystemOfExam.Repository.ExamRepository;
import com.beratozturk.SystemOfExam.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    public ExamService(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public List<Exam> getActiveExams() {
        return examRepository.findByIsActiveTrue();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    public Exam updateExam(Long id, ExamCreateRequest req) {
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam == null) {
            return null;
        }

        exam.setTitle(req.getTitle());
        exam.setDescription(req.getDescription());
        exam.setDurationInMinutes(req.getDurationInMinutes());
        exam.setStartDate(req.getStartDate());
        exam.setEndDate(req.getEndDate());
        exam.setActive(req.isActive());

        return examRepository.save(exam);
    }

    public boolean deleteExam(Long id) {
        Exam exam = examRepository.findById(id).orElse(null);
        if (exam == null) {
            return false;
        }
        examRepository.delete(exam);
        return true;
    }

    public Question addQuestion(Long examId, Question question) {
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            return null;
        }

        question.setExam(exam);
        return questionRepository.save(question);
    }

    public List<Question> getExamQuestions(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    public Question updateQuestion(Long examId, Long questionId, Question questionData) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            throw new RuntimeException("Soru bulunamadı");
        }

        // Sorunun bu sınavın olup olmadığını kontrol et
        if (!question.getExam().getId().equals(examId)) {
            throw new RuntimeException("Bu soru bu sınavın değil");
        }

        question.setQuestionText(questionData.getQuestionText());
        question.setQuestionType(questionData.getQuestionType());
        question.setOptions(questionData.getOptions());
        question.setCorrectAnswer(questionData.getCorrectAnswer());

        return questionRepository.save(question);
    }

    public boolean deleteQuestion(Long examId, Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return false;
        }

        // Sorunun bu sınavın olup olmadığını kontrol et
        if (!question.getExam().getId().equals(examId)) {
            return false;
        }

        questionRepository.delete(question);
        return true;
    }
}
