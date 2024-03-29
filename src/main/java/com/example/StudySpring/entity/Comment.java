package com.example.StudySpring.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "comment")
public class Comment extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer boardId;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
