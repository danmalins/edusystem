package com.weaponedu.edusystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "article")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(length = 2000)
    private String description;

    private int secretLevel; //(1,2,3)

    private String resourceUrl;
}