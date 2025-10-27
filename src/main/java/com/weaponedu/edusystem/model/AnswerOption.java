package com.weaponedu.edusystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answer_option")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
