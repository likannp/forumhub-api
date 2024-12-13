package com.forumhub.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private Timestamp creationDate;
    private Boolean solution;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Answer() {
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Topic getTopic() {
        return topic;
    }

    public User getAuthor() {
        return author;
    }
}
