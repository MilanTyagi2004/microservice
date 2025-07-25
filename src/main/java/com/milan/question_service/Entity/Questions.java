package com.milan.question_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String question;
    private String category;
    private String correctAnswer;

    @Column(columnDefinition = "JSON")
    private String options;  // You can use List<String> with @Convert if needed

    @Enumerated(EnumType.STRING)
    private Difficulty  difficulty;

    private String questionTitle;

}

