package com.beratozturk.SystemOfExam.Repository;

import com.beratozturk.SystemOfExam.Model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
    ExamAttempt findByUserIdAndExamId(Long userId, Long examId);
}
