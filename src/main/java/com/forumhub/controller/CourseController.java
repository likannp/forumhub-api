package com.forumhub.controller;

import com.forumhub.model.Course;
import com.forumhub.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/forumhub/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Endpoint para criar um novo curso.
     *
     * @param course Objeto do curso a ser criado.
     * @return ResponseEntity com status e mensagem apropriados.
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        Map<String, String> response = new HashMap<>();

        if (course.getName() == null || course.getName().trim().isEmpty()) {
            response.put("message", "Nome do curso é obrigatório");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (courseRepository.findByNameIgnoreCase(course.getName()).isPresent()) {
            response.put("message", "Curso já existente");
            response.put("courseName", course.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

}
