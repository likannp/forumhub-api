package com.forumhub.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    @OneToMany(mappedBy = "course")
    private List<Topic> topics;

    // Getters and Setters
}
