package com.beratozturk.SystemOfExam.Repository;

import com.beratozturk.SystemOfExam.Model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByIsActiveTrue();
}

