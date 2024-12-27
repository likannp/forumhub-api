package com.forumhub.controller;

import com.forumhub.model.Topic;
import com.forumhub.model.Course;
import com.forumhub.model.User;
import com.forumhub.repository.TopicRepository;
import com.forumhub.repository.CourseRepository;
import com.forumhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/forumhub/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody Topic topic) {
        // Validar e obter o curso
        Optional<Course> courseOpt = courseRepository.findById(topic.getCourse().getId());
        if (!courseOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        topic.setCourse(courseOpt.get());

        // Validar e obter o autor
        Optional<User> userOpt = userRepository.findById(topic.getAuthor().getId());
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        topic.setAuthor(userOpt.get());

        // Salvar o tópico no banco de dados
        Topic savedTopic = topicRepository.save(topic);

        // Criar o DTO de resposta
        TopicDTO responseDTO = new TopicDTO();
        responseDTO.setTitle(savedTopic.getTitle());
        responseDTO.setMessage(savedTopic.getMessage());
        responseDTO.setStatus(savedTopic.getStatus());

        // Definir o curso no DTO
        TopicDTO.CourseDTO courseDTO = new TopicDTO.CourseDTO();
        courseDTO.setId(savedTopic.getCourse().getId());
        courseDTO.setName(savedTopic.getCourse().getName());
        courseDTO.setCategory(savedTopic.getCourse().getCategory());
        responseDTO.setCourse(courseDTO);

        // Definir o autor no DTO
        TopicDTO.UserDTO userDTO = new TopicDTO.UserDTO();
        userDTO.setId(savedTopic.getAuthor().getId());
        userDTO.setUsername(savedTopic.getAuthor().getUsername());
        userDTO.setEmail(savedTopic.getAuthor().getEmail());
        responseDTO.setAuthor(userDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
