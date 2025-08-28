package com.beratozturk.SystemOfExam.Service;

import com.beratozturk.SystemOfExam.Model.Exam;
import com.beratozturk.SystemOfExam.Model.ExamAttempt;
import com.beratozturk.SystemOfExam.Model.Question;
import com.beratozturk.SystemOfExam.Repository.ExamAttemptRepository;
import com.beratozturk.SystemOfExam.Repository.ExamRepository;
import com.beratozturk.SystemOfExam.Repository.QuestionRepository;
import com.beratozturk.SystemOfExam.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class ExamParticipationService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ExamParticipationService(ExamRepository examRepository, QuestionRepository questionRepository,
                                   ExamAttemptRepository examAttemptRepository, UserRepository userRepository,
                                   JwtService jwtService) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ExamAttempt startExam(Long examId, String token) {
        // Token'dan kullanıcı bilgisini al
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        var user = userRepository.findByUserName(username);
        
        if (user == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        // Sınavı kontrol et
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Sınav bulunamadı"));

        if (!exam.isActive()) {
            throw new RuntimeException("Sınav aktif değil");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(exam.getStartDate()) || now.isAfter(exam.getEndDate())) {
            throw new RuntimeException("Sınav henüz başlamadı veya süresi doldu");
        }

        // Daha önce sınav başlatılmış mı kontrol et
        ExamAttempt existingAttempt = examAttemptRepository.findByUserIdAndExamId(user.getId(), examId);
        if (existingAttempt != null) {
            // Eğer sınav zaten başlatılmışsa mevcut denemeyi döndür
            return existingAttempt;
        }

        // Yeni sınav denemesi oluştur
        ExamAttempt attempt = new ExamAttempt();
        attempt.setUserId(user.getId());
        attempt.setExamId(examId);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus("IN_PROGRESS");

        return examAttemptRepository.save(attempt);
    }

    public List<Map<String, Object>> getExamQuestionsForStudent(Long examId, String token) {
        // Token'dan kullanıcı bilgisini al
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        var user = userRepository.findByUserName(username);
        
        if (user == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        // Sınavı kontrol et
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Sınav bulunamadı"));

        // Sınav denemesi var mı kontrol et
        ExamAttempt attempt = examAttemptRepository.findByUserIdAndExamId(user.getId(), examId);
        if (attempt == null) {
            throw new RuntimeException("Önce sınavı başlatmalısınız");
        }

        // Soruları getir (cevap olmadan)
        List<Question> questions = questionRepository.findByExamId(examId);
        
        return questions.stream().map(q -> {
            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("id", q.getId());
            questionMap.put("questionText", q.getQuestionText());
            questionMap.put("questionType", q.getQuestionType());
            questionMap.put("options", q.getOptions());
            // correctAnswer dahil edilmiyor!
            return questionMap;
        }).collect(Collectors.toList());
    }

    public ExamAttempt submitExam(Long examId, List<String> answers, String token) {
        // Token'dan kullanıcı bilgisini al
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        var user = userRepository.findByUserName(username);
        
        if (user == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        // Sınav denemesini bul
        ExamAttempt attempt = examAttemptRepository.findByUserIdAndExamId(user.getId(), examId);
        if (attempt == null) {
            throw new RuntimeException("Sınav denemesi bulunamadı");
        }

        // Sınavı bitir
        attempt.setEndTime(LocalDateTime.now());
        attempt.setStatus("COMPLETED");
        
        // Burada cevap değerlendirmesi yapılabilir
        // Şimdilik basit bir implementasyon

        return examAttemptRepository.save(attempt);
    }

    public Map<String, Object> getExamResults(Long examId, String token) {
        // Token'dan kullanıcı bilgisini al
        String username = jwtService.extractUsername(token.replace("Bearer ", ""));
        var user = userRepository.findByUserName(username);
        
        if (user == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        // Sınav denemesini bul
        ExamAttempt attempt = examAttemptRepository.findByUserIdAndExamId(user.getId(), examId);
        if (attempt == null) {
            throw new RuntimeException("Sınav denemesi bulunamadı");
        }

        if (!"COMPLETED".equals(attempt.getStatus())) {
            throw new RuntimeException("Sınav henüz tamamlanmadı");
        }

        // Basit sonuç döndür
        Map<String, Object> results = new HashMap<>();
        results.put("examId", examId);
        results.put("status", attempt.getStatus());
        results.put("startTime", attempt.getStartTime());
        results.put("endTime", attempt.getEndTime());
        results.put("duration", java.time.Duration.between(attempt.getStartTime(), attempt.getEndTime()).toMinutes());
        
        return results;
    }

    // Eski metodlar (geriye uyumluluk için)
    public List<Exam> getActiveAndOpenExams() {
        LocalDateTime now = LocalDateTime.now();
        return examRepository.findByIsActiveTrue().stream()
                .filter(exam -> now.isAfter(exam.getStartDate()) && now.isBefore(exam.getEndDate()))
                .collect(Collectors.toList());
    }

    public ExamAttempt startExam(Long examId, Long studentId) {
        // Eski implementasyon
        return null;
    }

    public ExamAttempt submitExam(Long attemptId, Long studentId) {
        // Eski implementasyon
        return null;
    }
}
