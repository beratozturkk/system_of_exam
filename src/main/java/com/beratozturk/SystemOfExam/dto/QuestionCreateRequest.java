package com.beratozturk.SystemOfExam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionCreateRequest {

    @NotNull
    private Long examId;

    @NotBlank
    private String questionText;

    @NotBlank
    private String questionType; // "MULTIPLE_CHOICE", "SHORT_ANSWER", "TRUE_FALSE"


    private String options;      // opsiyonel


    private String correctAnswer;


    public Long getExamId() {
        return examId;
    }
    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getOptions() {
        return options;
    }
    public void setOptions(String options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
