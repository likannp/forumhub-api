package com.forumhub.controller;

import com.forumhub.model.Topic;
import com.forumhub.model.Course;
import com.forumhub.model.User;
import com.forumhub.repository.TopicRepository;
import com.forumhub.repository.CourseRepository;
import com.forumhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{id}/summary")
    public ResponseEntity<TopicDTO.TopicSummaryDTO> getTopicSummary(@PathVariable Long id) {
        // Buscar o tópico pelo ID
        Optional<Topic> topicOpt = topicRepository.findById(id);

        if (!topicOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Topic topic = topicOpt.get();
        TopicDTO.TopicSummaryDTO summaryDTO = new TopicDTO.TopicSummaryDTO();
        summaryDTO.setTitle(topic.getTitle());
        summaryDTO.setMessage(topic.getMessage());

        return ResponseEntity.ok(summaryDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        Optional<Topic> topicOpt = topicRepository.findById(id);

        if (!topicOpt.isPresent()) {
            return ResponseEntity.status(404).body("Tópico não encontrado.");
        }

        topicRepository.deleteById(id);

        return ResponseEntity.ok("Tópico excluído com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Optional<Topic> topicOpt = topicRepository.findById(id);

        if (!topicOpt.isPresent()) {
            return ResponseEntity.status(404).body("Tópico não encontrado.");
        }

        Topic existingTopic = topicOpt.get();

        // Atualizar título e mensagem, caso fornecidos
        if (updatedTopic.getTitle() != null) {
            existingTopic.setTitle(updatedTopic.getTitle());
        }
        if (updatedTopic.getMessage() != null) {
            existingTopic.setMessage(updatedTopic.getMessage());
        }

        // Atualizar curso, caso fornecido
        if (updatedTopic.getCourse() != null) {
            Optional<Course> courseOpt = courseRepository.findById(updatedTopic.getCourse().getId());
            if (!courseOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Curso especificado não encontrado.");
            }
            existingTopic.setCourse(courseOpt.get());
        }

        // Atualizar status, caso fornecido
        if (updatedTopic.getStatus() != null) {
            existingTopic.setStatus(updatedTopic.getStatus());
        }

        topicRepository.save(existingTopic);

        return ResponseEntity.ok("Tópico atualizado com sucesso.");
    }
    @GetMapping
    public ResponseEntity<List<TopicDTO>> listAllTopics() {
        List<Topic> topics = topicRepository.findAll();

        List<TopicDTO> topicDTOs = topics.stream().map(topic -> {
            TopicDTO dto = new TopicDTO();
            dto.setTitle(topic.getTitle());
            dto.setMessage(topic.getMessage());
            dto.setStatus(topic.getStatus());

            TopicDTO.CourseDTO courseDTO = new TopicDTO.CourseDTO();
            courseDTO.setId(topic.getCourse().getId());
            courseDTO.setName(topic.getCourse().getName());
            courseDTO.setCategory(topic.getCourse().getCategory());
            dto.setCourse(courseDTO);

            TopicDTO.UserDTO userDTO = new TopicDTO.UserDTO();
            userDTO.setId(topic.getAuthor().getId());
            userDTO.setUsername(topic.getAuthor().getUsername());
            userDTO.setEmail(topic.getAuthor().getEmail());
            dto.setAuthor(userDTO);

            return dto;
        }).toList();

        return ResponseEntity.ok(topicDTOs);
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<TopicDTO>> getTopicsByCourse(@PathVariable Long courseId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!courseOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Topic> topics = topicRepository.findByCourseId(courseId);

        List<TopicDTO> topicDTOs = topics.stream().map(topic -> {
            TopicDTO dto = new TopicDTO();
            dto.setTitle(topic.getTitle());
            dto.setMessage(topic.getMessage());
            dto.setStatus(topic.getStatus());

            TopicDTO.CourseDTO courseDTO = new TopicDTO.CourseDTO();
            courseDTO.setId(topic.getCourse().getId());
            courseDTO.setName(topic.getCourse().getName());
            courseDTO.setCategory(topic.getCourse().getCategory());
            dto.setCourse(courseDTO);

            TopicDTO.UserDTO userDTO = new TopicDTO.UserDTO();
            userDTO.setId(topic.getAuthor().getId());
            userDTO.setUsername(topic.getAuthor().getUsername());
            userDTO.setEmail(topic.getAuthor().getEmail());
            dto.setAuthor(userDTO);

            return dto;
        }).toList();

        return ResponseEntity.ok(topicDTOs);
    }

}
