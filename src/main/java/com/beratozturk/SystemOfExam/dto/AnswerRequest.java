package com.beratozturk.SystemOfExam.dto;

import jakarta.validation.constraints.NotNull;

public class AnswerRequest {
    @NotNull
    private Long attemptId;

    @NotNull
    private Long questionId;

    private String answerText;


    public Long getAttemptId() {
        return attemptId;
    }
    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public String getAnswerText() {
        return answerText;
    }
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
