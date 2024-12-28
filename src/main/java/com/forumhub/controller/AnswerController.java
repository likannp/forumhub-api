package com.forumhub.controller;

import com.forumhub.model.Answer;
import com.forumhub.model.Topic;
import com.forumhub.model.User;
import com.forumhub.repository.AnswerRepository;
import com.forumhub.repository.TopicRepository;
import com.forumhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forumhub/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/author/{authorId}")
    public ResponseEntity<?> getAnswersByAuthor(@PathVariable Long authorId) {
        Optional<User> userOpt = userRepository.findById(authorId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        List<Answer> answers = answerRepository.findByAuthorId(authorId);
        if (answers.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhuma resposta encontrada para o autor.");
        }

        return ResponseEntity.ok(answers);
    }

    @PostMapping
    public ResponseEntity<?> createAnswer(@RequestBody Answer answer) {
        Optional<Topic> topicOpt = topicRepository.findById(answer.getTopic().getId());
        if (!topicOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Tópico não encontrado.");
        }
        answer.setTopic(topicOpt.get());

        Optional<User> userOpt = userRepository.findById(answer.getAuthor().getId());
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }
        answer.setAuthor(userOpt.get());

        Answer savedAnswer = answerRepository.save(answer);

        return ResponseEntity.ok(savedAnswer);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<Answer>> getAnswersByTopic(@PathVariable Long topicId) {
        Optional<Topic> topicOpt = topicRepository.findById(topicId);
        if (!topicOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Answer> answers = answerRepository.findByTopicId(topicId);
        return ResponseEntity.ok(answers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long id, @RequestBody Answer updatedAnswer) {
        Optional<Answer> answerOpt = answerRepository.findById(id);
        if (!answerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Answer existingAnswer = answerOpt.get();
        if (updatedAnswer.getMessage() != null) {
            existingAnswer.setMessage(updatedAnswer.getMessage());
        }
        if (updatedAnswer.isSolution() != existingAnswer.isSolution()) {
            existingAnswer.setSolution(updatedAnswer.isSolution());
        }

        answerRepository.save(existingAnswer);
        return ResponseEntity.ok(existingAnswer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long id) {
        if (!answerRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Resposta não encontrada.");
        }

        answerRepository.deleteById(id);
        return ResponseEntity.ok("Resposta excluída com sucesso.");
    }
}
