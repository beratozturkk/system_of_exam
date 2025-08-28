package com.beratozturk.SystemOfExam.Model;

import jakarta.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hangi attempt
    @ManyToOne
    @JoinColumn(name = "attempt_id")
    private ExamAttempt attempt;

    // Hangi soru
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // Öğrencinin cevabı (MCQ için "B", Doğru/yanlış için "TRUE"/"FALSE", kısa cevap için serbest metin)
    @Lob
    private String answerText;

    public Answer() {}
    public Answer(ExamAttempt attempt, Question question, String answerText) {
        this.attempt = attempt;
        this.question = question;
        this.answerText = answerText;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public ExamAttempt getAttempt() {
        return attempt;
    }
    public void setAttempt(ExamAttempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswerText() {
        return answerText;
    }
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
