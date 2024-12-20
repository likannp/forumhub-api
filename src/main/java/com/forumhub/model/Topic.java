package com.forumhub.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private String status; // "open", "closed"

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "topic")
    private List<Answer> answers;

    // Getters and Setters
}
