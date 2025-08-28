package com.beratozturk.SystemOfExam.Service;

import com.beratozturk.SystemOfExam.Model.Question;
import com.beratozturk.SystemOfExam.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

}
