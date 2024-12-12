package com.forumhub;

import com.forumhub.model.Course;
import com.forumhub.model.Topic;
import com.forumhub.model.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        User usuario = new User();
        usuario.setUsername("Lenin");
        System.out.println(usuario.getUsername());
        Course curso = new Course();
        curso.setName("Java");
        Topic topico = new Topic();
        topico.setAuthorId(usuario.getId());
        topico.setCourseId(curso.getId());
        topico.setTitle("Não consigo criar um projeto.");
        topico.setMessage("Preciso de ajuda para criar um projeto em java.");
        System.out.println(topico);
    }
}
