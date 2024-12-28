package com.forumhub.repository;

import com.forumhub.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByTopicId(Long topicId);
    List<Answer> findByAuthorId(Long authorId);
}
