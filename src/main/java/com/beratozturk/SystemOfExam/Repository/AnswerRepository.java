package com.beratozturk.SystemOfExam.Repository;

import com.beratozturk.SystemOfExam.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository  extends JpaRepository<Answer, Long> {
    List<Answer> findByAttemptId(Long attemptId);
}
