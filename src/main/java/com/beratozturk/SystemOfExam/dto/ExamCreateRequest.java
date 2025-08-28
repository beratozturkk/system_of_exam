package com.beratozturk.SystemOfExam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ExamCreateRequest {

    @NotBlank(message = "Title boş olamaz")
    private String title;

    @Size(max = 500, message = "Description en fazla 500 karakter olabilir")
    private String description;

    @Min(value = 1, message = "Süre en az 1 dakika olmalı")
    private int durationInMinutes;

    @NotNull(message = "startDate zorunlu")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm[:ss]")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @NotNull(message = "endDate zorunlu")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm[:ss]")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    private boolean active;

    public ExamCreateRequest(){}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getDurationInMinutes() {
        return durationInMinutes;
    }
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
